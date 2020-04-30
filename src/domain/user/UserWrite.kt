package com.example.domain.user

import com.example.utils.Role
import kotlinx.serialization.Serializable

@Serializable
data class UserWrite(
    val email: String,
    val firstName: String,
    val lastName: String,
    val hashedPassword: String,
    val role: Role
)
