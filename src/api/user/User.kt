@file:UseSerializers(LocalDateTimeSerializer::class)

package com.example.api.user

import com.example.utils.Role
import com.example.utils.customSerializations.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDateTime


@Serializable
data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: Role,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)