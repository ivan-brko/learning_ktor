package com.example.auth.authentication

import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(
    val email: String,
    val password: String
)

@Serializable
data class PositiveLoginResponse(
    val token: String
)