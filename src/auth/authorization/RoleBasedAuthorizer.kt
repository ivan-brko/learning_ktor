package com.example.auth.authorization

import com.example.utils.Role
import io.ktor.application.ApplicationCall

class RoleBasedAuthorizer {
    internal var authorizationFunction: suspend ApplicationCall.(Role) -> Boolean = { false }

    fun validate(body: suspend ApplicationCall.(Role) -> Boolean) {
        authorizationFunction = body
    }
}