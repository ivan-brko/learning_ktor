package com.example.routes.routeBuilders

import com.auth0.jwt.JWT
import com.example.auth.authentication.AuthenticationConstants.jwtHashAlgorithm
import com.example.auth.authentication.LoggedInUser
import com.example.auth.authentication.PositiveLoginResponse
import com.example.auth.authentication.UserLogin
import com.example.auth.authentication.UserSessionHandler
import com.example.domain.user.User
import com.example.domain.user.UserDomainService
import com.example.utils.AttributeKeysContainer
import com.example.utils.hashPassword
import com.example.utils.serviceTypeConversions.toApi
import com.example.utils.serviceTypeConversions.toDomain
import io.ktor.application.call
import io.ktor.auth.authenticate
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
    val userSessionHandler by kodein.instance<UserSessionHandler>()
    val attributeKeys by kodein.instance<AttributeKeysContainer>()

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

            val session = userSessionHandler.loginUser(loginUser.email)

            //TODO: we did not call withExpiresAt, withIssuedAt or some other claims, check what is necessary here
            val jwt =
                JWT.create().withSubject(loginUser.email).withClaim("session", session.sessionId).sign(jwtHashAlgorithm)
            call.respond(PositiveLoginResponse(jwt))
        }

        authenticate {
            post("logout") { //TODO: think about returned codes from here
                when (val loggedInUser = call.attributes.getOrNull(attributeKeys.loggedInUserAttributeKey)) {
                    is LoggedInUser -> {
                        userSessionHandler.logoutUser(loggedInUser.user.email, loggedInUser.session)
                        call.respond(HttpStatusCode.OK)
                        return@post
                    }
                    else -> {
                        call.respond(HttpStatusCode.NotFound)
                        return@post
                    }
                }
            }
        }
    }
}