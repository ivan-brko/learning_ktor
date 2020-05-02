package com.example

import com.example.auth.authentication.setupAuthentication
import com.example.auth.authorization.setupAuthorization
import com.example.routes.setupRouting
import com.example.utils.setupContentNegotiation
import com.example.utils.setupCors
import com.example.utils.setupKodeinDI
import io.ktor.application.Application

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val kodein = setupKodeinDI()
    setupContentNegotiation()
    setupCors()
    setupAuthentication(kodein)
    setupAuthorization(kodein)
    setupRouting(kodein)
}

