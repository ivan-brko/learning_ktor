package com.example.routes.routeBuilders

import com.example.api.user.User
import com.example.api.user.UserApiService
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.route
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

fun Route.setUserRoutes(kodein: Kodein) {
    val userApiService by kodein.instance<UserApiService>()

    route("api/users") {
        authenticate {
            get<Email> { userEmailContainer ->
                when (val user = userApiService.getUserByEmail(userEmailContainer.email)) {
                    is User -> call.respond(user)
                    else -> call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}