package com.example.domain.user

import com.example.domain.repository.UserRepositoryService
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class DevelopmentUserDomainService(kodein: Kodein) : UserDomainService {
    val userRepository by kodein.instance<UserRepositoryService>()

    override fun getUserByEmail(email: String): User? =
        userRepository.getUserByEmail(email)

    override fun insertUser(user: UserWrite): User? =
        userRepository.insertUser(user)

}