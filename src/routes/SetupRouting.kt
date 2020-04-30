package com.example.routes

import com.example.routes.routeBuilders.setAuthenticationRoutes
import com.example.routes.routeBuilders.setUserRoutes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.locations.Locations
import io.ktor.routing.routing
import org.kodein.di.Kodein

fun Application.setupRouting(kodein: Kodein){
    install(Locations)

    routing {
        setAuthenticationRoutes(kodein)
        setUserRoutes(kodein)
    }
}