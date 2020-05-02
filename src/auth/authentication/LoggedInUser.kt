package com.example.auth.authentication

import com.example.domain.user.User

data class LoggedInUser(
    val user: User,
    val session: Session
)