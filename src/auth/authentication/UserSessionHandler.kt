package com.example.auth.authentication

interface UserSessionHandler {
    fun validateUserSession(email: String, session: Session): Boolean
    fun loginUser(email: String): Session
    fun logoutUser(email: String, session: Session): Boolean?
}