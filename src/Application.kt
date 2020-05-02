package com.example

import com.example.auth.authentication.setupAuthentication
import com.example.auth.authorization.setupAuthorization
import com.example.routes.setupRouting
import com.example.utils.setupContentNegotiation
import com.example.utils.setupCors
import com.example.utils.setupKodeinDI
import io.ktor.application.Application
import org.kodein.di.Kodein

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val kodein = setupKodeinDI()
    moduleWithInjectedDependencies(kodein, testing)
}

//as suggested in ktor documentation, we separate the function in two modules, where
//one simply creates DI object and passes it to the other
//this way, we can test easily by calling second module with DI setup for tests
fun Application.moduleWithInjectedDependencies(kodein: Kodein, testing: Boolean) {
    setupContentNegotiation()
    setupCors()
    setupAuthentication(kodein)
    setupAuthorization(kodein)
    setupRouting(kodein)
}

