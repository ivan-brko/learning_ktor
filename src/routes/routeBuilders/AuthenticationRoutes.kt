package com.example.routes.routeBuilders

import com.auth0.jwt.JWT
import com.example.auth.AuthenticationConstants.jwtHashAlgorithm
import com.example.auth.PositiveLoginResponse
import com.example.auth.UserLogin
import com.example.domain.user.User
import com.example.domain.user.UserDomainService
import com.example.utils.hashPassword
import com.example.utils.serviceTypeConversions.toApi
import com.example.utils.serviceTypeConversions.toDomain
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import com.example.api.user.UserWrite as ApiUserWrite

fun Route.setAuthenticationRoutes(kodein: Kodein) {
    val userDomainService by kodein.instance<UserDomainService>()

    route("auth") {
        put("register") {
            val user = call.receive<ApiUserWrite>()
            when (val retrievedUser = userDomainService.insertUser(user.toDomain())) {
                is User -> call.respond(retrievedUser.toApi())
                else -> call.respond(HttpStatusCode.Conflict)
            }
        }

        post("login") {
            val loginUser = call.receive<UserLogin>()
            val retrievedUser = userDomainService.getUserByEmail(loginUser.email)
            if (retrievedUser == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }

            if (retrievedUser.hashedPassword != hashPassword(loginUser.password)) {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }

            val jwt = JWT.create().withSubject(loginUser.email).sign(jwtHashAlgorithm)
            call.respond(PositiveLoginResponse(jwt))
        }
    }
}