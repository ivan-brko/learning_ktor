package com.example.utils

import kotlinx.serialization.*

@Serializable(with = RoleSerializer::class)
sealed class Role(val level: Int, val name: String) {

    object ADMIN : Role(100, "Admin")

    object USER : Role(10, "User")

    object NONE : Role(0, "None")

}

fun String.toRole(): Role = when (this) {
    Role.ADMIN.name -> Role.ADMIN
    Role.USER.name -> Role.USER
    else -> Role.NONE
}

@Serializer(forClass = Role::class)
object RoleSerializer : KSerializer<Role> {
    override val descriptor: SerialDescriptor = PrimitiveDescriptor("RoleStringSerializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Role =
        decoder.decodeString().toRole()

    override fun serialize(encoder: Encoder, value: Role) =
        encoder.encodeString(value.name)
}

