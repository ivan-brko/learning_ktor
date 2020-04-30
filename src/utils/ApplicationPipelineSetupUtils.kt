package com.example.utils

import com.example.api.user.DevelopmentUserApiService
import com.example.api.user.UserApiService
import com.example.domain.repository.InMemoryUserRepositoryService
import com.example.domain.repository.UserRepositoryService
import com.example.domain.user.DevelopmentUserDomainService
import com.example.domain.user.UserDomainService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json
import kotlinx.serialization.serializer
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun Application.setupKodeinDI(): Kodein =
    Kodein{
        bind<UserDomainService>() with singleton { DevelopmentUserDomainService(kodein) }
        bind<UserApiService>() with singleton { DevelopmentUserApiService(kodein) }
        bind<UserRepositoryService>() with singleton { InMemoryUserRepositoryService() }
    }

fun Application.setupContentNegotiation() =
    install(ContentNegotiation) {
        json()
    }