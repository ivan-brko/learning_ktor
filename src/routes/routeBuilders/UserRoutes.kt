package com.example.routes.routeBuilders

import com.example.api.user.User
import com.example.api.user.UserApiService
import com.example.api.user.UserWrite
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.delete
import io.ktor.locations.get
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

fun Route.setUserRoutes(kodein: Kodein) {
    val userApiService by kodein.instance<UserApiService>()

    route("api/users") {
        authenticate {
            get<EmailLocation> { userEmailContainer ->
                when (val user = userApiService.getUserByEmail(userEmailContainer.email)) {
                    is User -> call.respond(user)
                    else -> call.respond(HttpStatusCode.NotFound)
                }
            }

            get {
                call.respond(userApiService.getAllUsers())
            }

            post {
                val writeUser = call.receive<UserWrite>()
                when (val user = userApiService.insertUser(writeUser)) {
                    is User -> call.respond(user)
                    else -> call.respond(HttpStatusCode.Conflict)
                }
            }

            put {
                val writeUser = call.receive<UserWrite>()
                when (val user = userApiService.updateUser(writeUser)) {
                    is User -> call.respond(user)
                    else -> call.respond(HttpStatusCode.Conflict)
                }
            }

            delete<EmailLocation> { emailContainer ->
                if (userApiService.deleteUserByEmail(emailContainer.email))
                    call.respond(HttpStatusCode.NoContent)
                else
                    call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}