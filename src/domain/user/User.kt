@file:ContextualSerialization(LocalDateTime::class)
package com.example.domain.user

import com.example.utils.Role
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val hashedPassword: String,
    val role: Role,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)