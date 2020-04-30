package com.example.utils

import com.example.api.user.DevelopmentUserApiService
import com.example.api.user.UserApiService
import com.example.domain.user.DevelopmentUserDomainService
import com.example.domain.user.UserDomainService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun Application.kodeinDISetup(): Kodein =
    Kodein{
        bind<UserDomainService>() with singleton { DevelopmentUserDomainService() }
        bind<UserApiService>() with singleton { DevelopmentUserApiService(kodein) }
    }

fun Application.setupContentNegotiation() =
    install(ContentNegotiation) {
        json()
    }