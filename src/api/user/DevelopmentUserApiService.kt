package com.example.api.user

import com.example.domain.user.UserDomainService
import com.example.utils.serviceTypeConversions.toApi
import com.example.utils.serviceTypeConversions.toDomain
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class DevelopmentUserApiService(kodein: Kodein): UserApiService{
    private val userDomainService by kodein.instance<UserDomainService>()

    override fun insertUser(userWrite: UserWrite): User? =
        userDomainService.insertUser(userWrite.toDomain())?.let { it.toApi() }

    override fun getUserByEmail(email: String): User? =
        userDomainService.getUserByEmail(email)?.toApi()

    override fun getAllUsers(): List<User> =
        userDomainService.getAllUsers().map { it.toApi() }

    override fun deleteUserByEmail(email: String): Boolean =
        userDomainService.deleteUserByEmail(email)

    override fun updateUser(userWrite: UserWrite): User? =
        userDomainService.updateUser(userWrite.toDomain())?.let{it.toApi()}

}