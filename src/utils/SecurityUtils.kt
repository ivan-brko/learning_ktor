package com.example.utils

import com.auth0.jwt.algorithms.Algorithm
import com.example.auth.AuthenticationConstants
import io.ktor.util.hex
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private val hashKey = hex("40fa3dd752fe829080aa5834c03376d183f90cd6")
private val hmacKey = SecretKeySpec(hashKey, "HmacSHA1")

fun hashPassword(password: String): String {
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(hmacKey)
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}