package com.example.api.user

interface UserApiService{
    fun insertUser(userWrite: UserWrite): User?
    fun getUserByEmail(email: String): User?
    fun getAllUsers(): List<User>
    fun deleteUserByEmail(email: String): Boolean
    fun updateUser(userWrite: UserWrite): User?
}