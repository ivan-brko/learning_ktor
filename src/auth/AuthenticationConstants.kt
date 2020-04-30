package com.example.auth

import com.auth0.jwt.algorithms.Algorithm

object AuthenticationConstants {
    const val algorithmSecret = "KD3HihJOpRTnSJOyJCla"

    //https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/WWW-Authenticate
    const val realm = "NutritionApplication"

    val jwtHashAlgorithm: Algorithm = Algorithm.HMAC256(AuthenticationConstants.algorithmSecret)
}