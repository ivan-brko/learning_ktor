package com.example.auth.authentication

import com.auth0.jwt.JWT
import com.example.auth.authentication.AuthenticationConstants.jwtHashAlgorithm
import com.example.domain.user.UserDomainService
import com.example.utils.AttributeKeysContainer
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTCredential
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

private fun makeJwtVerifier() =
    JWT.require(jwtHashAlgorithm).build()

private fun JWTCredential.getSessionClaim(): String? =
    payload.getClaim("session")?.let { it.asString() }


fun Application.setupAuthentication(kodein: Kodein) {
    val userDomainService by kodein.instance<UserDomainService>()
    val attributeKeys by kodein.instance<AttributeKeysContainer>()
    val userSessionHandler by kodein.instance<UserSessionHandler>()

    //this code will be much nicer when we introduce Arrow and Optionals
    install(Authentication) {
        jwt {
            realm = AuthenticationConstants.realm
            verifier(makeJwtVerifier())
            validate { credential ->
                credential.getSessionClaim()?.let { session ->
                    val username = credential.payload.subject
                    if (userSessionHandler.validateUserSession(username, Session(session))) {
                        val retrievedUser = userDomainService.getUserByEmail(username)
                        retrievedUser?.let { user ->
                            attributes.put(attributeKeys.loggedInUserAttributeKey, LoggedInUser(user, Session(session)))
                            JWTPrincipal(credential.payload)
                        }
                    } else null
                }
            }
        }
    }
}
