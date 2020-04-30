package com.example

import com.example.auth.setupAuthentication
import com.example.routes.setupRouting
import com.example.utils.setupKodeinDI
import com.example.utils.setupContentNegotiation
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val kodein = setupKodeinDI()
    setupContentNegotiation()
    setupAuthentication(kodein)
    setupRouting(kodein)
}
