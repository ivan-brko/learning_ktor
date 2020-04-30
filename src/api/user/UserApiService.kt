package com.example.api.user

interface UserApiService{
    fun insertUser(userWrite: UserWrite): User?
    fun getUserByEmail(email: String): User?
}