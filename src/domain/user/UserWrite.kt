package com.example.domain.user

data class UserWrite(
    val email: String,
    val firstName: String,
    val lastName: String,
    val hashedPassword: String
)

fun UserWrite.toUser() =
    User(email, firstName, lastName, hashedPassword)