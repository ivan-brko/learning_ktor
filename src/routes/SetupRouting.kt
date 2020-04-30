package com.example.routes

import com.example.routes.routeBuilders.setAuthenticationRoutes
import com.example.routes.routeBuilders.setUserRoutes
import io.ktor.application.Application
import io.ktor.routing.routing
import org.kodein.di.Kodein

fun Application.setupRouting(kodein: Kodein){
    routing {
        setAuthenticationRoutes(kodein)
        setUserRoutes(kodein)
    }
}