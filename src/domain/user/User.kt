package com.example.domain.user

data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val hashedPassword: String
)