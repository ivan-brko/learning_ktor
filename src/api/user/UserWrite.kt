package com.example.api.user

import com.example.utils.Role
import kotlinx.serialization.Serializable

@Serializable
data class UserWrite(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: Role
)