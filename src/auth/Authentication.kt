package com.example.auth

import com.auth0.jwt.JWT
import com.example.auth.AuthenticationConstants.jwtHashAlgorithm
import com.example.domain.user.UserDomainService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

private fun makeJwtVerifier() =
    JWT.require(jwtHashAlgorithm).build()

fun Application.setupAuthentication(kodein: Kodein) {
    val userDomainService by kodein.instance<UserDomainService>()

    install(Authentication) {
        jwt {
            realm = AuthenticationConstants.realm
            verifier(makeJwtVerifier())
            validate { credential ->
                val username = credential.payload.subject
                val retrievedUser = userDomainService.getUserByEmail(username)
                retrievedUser?.let {
                    JWTPrincipal(credential.payload)
                }
            }
        }
    }
}
