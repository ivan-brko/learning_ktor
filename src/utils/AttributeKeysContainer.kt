package com.example.utils

import com.example.domain.user.User
import io.ktor.util.AttributeKey

object AttributeKeysContainer {
    val userAttributeKey = AttributeKey<User>("User")
}