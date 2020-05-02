package com.example.auth.authorization

import com.example.auth.authentication.LoggedInUser
import com.example.utils.AttributeKeysContainer
import io.ktor.application.Application
import io.ktor.application.install
import org.kodein.di.Kodein
import org.kodein.di.generic.instance


fun Application.setupAuthorization(kodein: Kodein) {
    val attributeKeys by kodein.instance<AttributeKeysContainer>()

    install(RoleAuthorization) {
        validate { role ->
            when (val loggedInUser = attributes.getOrNull(attributeKeys.loggedInUserAttributeKey)) {
                is LoggedInUser -> {
                    loggedInUser.user.role.level >= role.level
                }
                else -> false
            }
        }
    }
}