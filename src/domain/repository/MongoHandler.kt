package com.example.domain.repository

import com.example.domain.repository.user.registerUsersSerializers
import io.ktor.config.ApplicationConfig
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class MongoHandler(kodein: Kodein) {
    val config by kodein.instance<ApplicationConfig>()

    init {
        registerAdditionalSerializers()
    }

    val mongoClient by lazy {
        //we could use propertyOrNull here to handle missing config graciously, this will throw if
        //connection string is not set
        val connectionString = config.property("ktor.mongo.connectionString").getString()
        KMongo.createClient(connectionString).coroutine
    }

    val database by lazy {
        val databaseName = config.property("ktor.mongo.databaseName").getString()
        mongoClient.getDatabase(databaseName)
    }

    private fun registerAdditionalSerializers() {
        registerUsersSerializers()
    }
}