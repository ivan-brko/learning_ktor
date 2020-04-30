package com.example.domain.user

import com.example.domain.repository.user.UserRepositoryService
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class DevelopmentUserDomainService(kodein: Kodein) : UserDomainService {
    private val userRepository by kodein.instance<UserRepositoryService>()

    override suspend fun getUserByEmail(email: String): User? =
        userRepository.getUserByEmail(email)

    override suspend fun insertUser(user: UserWrite): User? =
        userRepository.insertUser(user)

    override suspend fun deleteUserByEmail(email: String): Boolean =
        userRepository.deleteUserByEmail(email)

    override suspend fun updateUser(userWrite: UserWrite): User? =
        userRepository.updateUser(userWrite)

    override suspend fun getAllUsers(): List<User> =
        userRepository.getAllUsers()

}