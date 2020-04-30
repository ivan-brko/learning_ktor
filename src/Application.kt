package com.example

import com.example.auth.setupAuthentication
import com.example.routes.setupRouting
import com.example.utils.kodeinDISetup
import com.example.utils.setupContentNegotiation
import io.ktor.application.*
import io.ktor.locations.Locations

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val kodein = kodeinDISetup()
    setupContentNegotiation()
    setupAuthentication(kodein)
    install(Locations)
    setupRouting(kodein)
}

