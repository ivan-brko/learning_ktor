package com.example.utils

import com.example.api.user.DevelopmentUserApiService
import com.example.api.user.UserApiService
import com.example.domain.repository.MongoHandler
import com.example.domain.repository.user.MongoUserRepositoryService
import com.example.domain.repository.user.UserRepositoryService
import com.example.domain.user.DevelopmentUserDomainService
import com.example.domain.user.UserDomainService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun Application.setupKodeinDI(): Kodein =
    Kodein{
        bind<UserDomainService>() with singleton { DevelopmentUserDomainService(kodein) }
        bind<UserApiService>() with singleton { DevelopmentUserApiService(kodein) }
        bind<UserRepositoryService>() with singleton {
            MongoUserRepositoryService(
                kodein
            )
        }
        bind<MongoHandler>() with singleton { MongoHandler() }
    }

fun Application.setupContentNegotiation() =
    install(ContentNegotiation) {
        json()
    }