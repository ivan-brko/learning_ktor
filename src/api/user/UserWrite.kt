package com.example.api.user

import kotlinx.serialization.Serializable

@Serializable
data class UserWrite(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)