package com.example.auth.authentication

import java.util.*

data class Session(val sessionId: String)

object InMemoryUserSessionHandler : UserSessionHandler {
    //we map email to all of its active sessions
    private val activeSessions = mutableMapOf<String, MutableSet<Session>>()

    override fun validateUserSession(email: String, session: Session) =
        when (val userActiveSessions = activeSessions[email]) {
            is MutableSet<Session> -> userActiveSessions.contains(session)
            else -> false
        }

    override fun loginUser(email: String): Session {
        return when (val userActiveSessions = activeSessions[email]) {
            is MutableSet<Session> -> {
                val session = Session(UUID.randomUUID().toString())
                userActiveSessions.add(session)
                session
            }
            else -> {
                val session = Session(UUID.randomUUID().toString())
                val sessionsForUser = mutableSetOf(session)
                activeSessions[email] = sessionsForUser
                session
            }
        }
    }

    override fun logoutUser(email: String, session: Session) =
        activeSessions[email]?.run {
            when (val result = remove(session)) {
                is Boolean -> result
                else -> false
            }
        }
}