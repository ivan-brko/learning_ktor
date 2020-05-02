package com.example.utils

import com.example.api.user.DevelopmentUserApiService
import com.example.api.user.UserApiService
import com.example.auth.authorization.RoleAuthorization
import com.example.domain.repository.MongoHandler
import com.example.domain.repository.user.MongoUserRepositoryService
import com.example.domain.repository.user.UserRepositoryService
import com.example.domain.user.DevelopmentUserDomainService
import com.example.domain.user.User
import com.example.domain.user.UserDomainService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.config.ApplicationConfig
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun Application.setupKodeinDI(): Kodein =
    Kodein {
        bind<UserDomainService>() with singleton { DevelopmentUserDomainService(kodein) }
        bind<UserApiService>() with singleton { DevelopmentUserApiService(kodein) }
        bind<UserRepositoryService>() with singleton {
            MongoUserRepositoryService(
                kodein
            )
        }
        bind<MongoHandler>() with singleton { MongoHandler(kodein) }
        bind<ApplicationConfig>() with singleton { environment.config }
        bind<AttributeKeysContainer>() with singleton { AttributeKeysContainer }
    }

fun Application.setupContentNegotiation() =
    install(ContentNegotiation) {
        json()
    }

fun Application.setupCors() =
    install(CORS) {
        anyHost()
        //TODO: read about proper way of setting up CORS (not in ktor but general, how permissive should we be)
    }

fun Application.setupAuthorization(kodein: Kodein) {
    val attributeKeys by kodein.instance<AttributeKeysContainer>()

    install(RoleAuthorization) {
        validate { role ->
            when (val user = attributes.getOrNull(attributeKeys.userAttributeKey)) {
                is User -> {
                    user.role.level >= role.level
                }
                else -> false
            }
        }
    }
}