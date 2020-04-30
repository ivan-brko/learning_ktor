package com.example.api.user

import com.example.utils.Role
import com.example.utils.customSerializations.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: Role,
    @Serializable(with = LocalDateTimeSerializer::class) val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class) val updatedAt: LocalDateTime
)