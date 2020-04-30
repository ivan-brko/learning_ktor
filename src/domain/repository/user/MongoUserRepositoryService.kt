package com.example.domain.repository.user

import com.example.domain.repository.MongoHandler
import com.example.domain.user.User
import com.example.domain.user.UserWrite
import com.example.domain.user.toUser
import com.mongodb.DuplicateKeyException
import com.mongodb.MongoWriteException
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.litote.kmongo.SetTo
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.set
import java.time.LocalDateTime

class MongoUserRepositoryService(kodein: Kodein) :
    UserRepositoryService {
    private val mongoHandler by kodein.instance<MongoHandler>()

    private suspend fun getUsersCollection(): CoroutineCollection<User> {
        val users = mongoHandler.database.getCollection<User>()
        users.ensureUniqueIndex(User::email) //TODO: this probably shouldn't be called on every request
        return users
    }

    override suspend fun getUserByEmail(email: String): User? {
        val users = getUsersCollection()
        return users.findOne(User::email eq email)
    }

    override suspend fun insertUser(userWrite: UserWrite): User? {
        val users = getUsersCollection()
        val user = userWrite.toUser()

        return try{
            users.insertOne(user)
            user
        } catch (e: MongoWriteException){
            if (e.code == 11000) //user with that email already in DB
                null
            else
                throw e
        }
    }

    override suspend fun deleteUserByEmail(email: String): Boolean {
        val users = getUsersCollection()
        return users.deleteOne(User::email eq email).wasAcknowledged()
    }

    override suspend fun updateUser(userWrite: UserWrite): User? {
        val users = getUsersCollection()

        val updaters = set(
            SetTo(User::updatedAt, LocalDateTime.now()),
            SetTo(User::hashedPassword, userWrite.hashedPassword),
            SetTo(User::firstName, userWrite.firstName),
            SetTo(User::lastName, userWrite.lastName),
            SetTo(User::role, userWrite.role)
        )

        return if (users.updateOne(User::email eq userWrite.email, updaters)
                .wasAcknowledged()
        ) //TODO: check that wasAck acts as expected
            getUserByEmail(userWrite.email)
        else
            null
    }

    override suspend fun getAllUsers(): List<User> =
        getUsersCollection().find().toList()

}