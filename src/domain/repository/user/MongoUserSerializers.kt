package com.example.domain.repository.user

import com.example.utils.Role
import com.example.utils.RoleSerializer
import kotlinx.serialization.*
import org.litote.kmongo.serialization.LocalDateSerializer
import org.litote.kmongo.serialization.registerSerializer


object UserMongoSerializers {
    object Roles {
        @Serializer(forClass = Role.ADMIN::class)
        object AdminSerializer : KSerializer<Role.ADMIN> {
            override val descriptor: SerialDescriptor =
                PrimitiveDescriptor("AdminRoleStringSerializer", PrimitiveKind.STRING)

            override fun deserialize(decoder: Decoder): Role.ADMIN =
                Role.ADMIN

            override fun serialize(encoder: Encoder, value: Role.ADMIN) =
                encoder.encodeString(value.name)
        }

        @Serializer(forClass = Role.USER::class)
        object UserSerializer : KSerializer<Role.USER> {
            override val descriptor: SerialDescriptor =
                PrimitiveDescriptor("UserRoleStringSerializer", PrimitiveKind.STRING)

            override fun deserialize(decoder: Decoder): Role.USER =
                Role.USER

            override fun serialize(encoder: Encoder, value: Role.USER) =
                encoder.encodeString(value.name)
        }

        @Serializer(forClass = Role.NONE::class)
        object NoneSerializer : KSerializer<Role.NONE> {
            override val descriptor: SerialDescriptor =
                PrimitiveDescriptor("NoneRoleStringSerializer", PrimitiveKind.STRING)

            override fun deserialize(decoder: Decoder): Role.NONE =
                Role.NONE

            override fun serialize(encoder: Encoder, value: Role.NONE) =
                encoder.encodeString(value.name)
        }
    }
}

fun registerUsersSerializers() {
    registerSerializer(UserMongoSerializers.Roles.AdminSerializer)
    registerSerializer(UserMongoSerializers.Roles.UserSerializer)
    registerSerializer(UserMongoSerializers.Roles.NoneSerializer)
    registerSerializer(RoleSerializer)
    registerSerializer(LocalDateSerializer)
}