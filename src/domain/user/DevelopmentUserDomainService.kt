package com.example.domain.user

class DevelopmentUserDomainService : UserDomainService {
    var users = mutableListOf<User>()

    override fun getUserByEmail(email: String): User? =
        users.find { it.email == email }

    override fun insertUser(user: UserWrite): User? {
        users.add(user.toUser())
        return user.toUser()
    }

}