package com.example.domain.repository

import com.example.domain.repository.user.registerUsersSerializers
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class MongoHandler() {
    init {
        registerAdditionalSerializers()
    }

    val mongoClient by lazy {
        KMongo.createClient("mongodb://root:root123@localhost:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&ssl=false").coroutine
    }

    val database by lazy {
        mongoClient.getDatabase("learn-ktor")
    }

    private fun registerAdditionalSerializers() {
        registerUsersSerializers()
    }
}