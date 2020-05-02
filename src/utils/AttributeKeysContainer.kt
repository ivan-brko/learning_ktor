package com.example.utils


import com.example.auth.authentication.LoggedInUser
import io.ktor.util.AttributeKey

object AttributeKeysContainer {
    val loggedInUserAttributeKey = AttributeKey<LoggedInUser>("LoggedInUser")
}