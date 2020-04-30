package com.example.utils.serviceTypeConversions

import com.example.utils.hashPassword
import com.example.domain.user.User as DomainUser
import com.example.api.user.User as ApiUser
import com.example.domain.user.UserWrite as DomainUserWrite
import com.example.api.user.UserWrite as ApiUserWrite

fun DomainUser.toApi() =
    ApiUser(this.email, this.firstName, this.lastName)

fun ApiUserWrite.toDomain() =
    DomainUserWrite(this.email, this.firstName, this.lastName, hashPassword(this.password))
