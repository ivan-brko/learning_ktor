package com.example.utils.serviceTypeConversions

import com.example.utils.hashPassword
import com.example.api.user.User as ApiUser
import com.example.api.user.UserWrite as ApiUserWrite
import com.example.domain.user.User as DomainUser
import com.example.domain.user.UserWrite as DomainUserWrite

fun DomainUser.toApi() =
    ApiUser(email, firstName, lastName, role, createdAt, updatedAt)

fun ApiUserWrite.toDomain() =
    DomainUserWrite(email, firstName, lastName, hashPassword(password), role)
