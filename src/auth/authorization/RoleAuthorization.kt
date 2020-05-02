package com.example.auth.authorization

import com.example.utils.Role
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.util.AttributeKey
import io.ktor.util.pipeline.PipelinePhase

class RoleAuthorization internal constructor(val configuration: Configuration) {
    constructor(provider: RoleBasedAuthorizer) : this(Configuration(provider))

    fun interceptPipeline(pipeline: ApplicationCallPipeline, role: Role) {
        pipeline.insertPhaseBefore(ApplicationCallPipeline.Call, authorizationPhase)
        pipeline.intercept(authorizationPhase) {
            val call = call
            if (configuration.provider.authorizationFunction(call, role)) {
                return@intercept
            } else {
                call.respond(HttpStatusCode.Forbidden)
                finish() //no further features get called
            }
        }
    }

    companion object Feature : ApplicationFeature<ApplicationCallPipeline, RoleBasedAuthorizer, RoleAuthorization> {
        private val authorizationPhase = PipelinePhase("authorization") // this will be used in pipeline with install()
        override val key: AttributeKey<RoleAuthorization> = AttributeKey("RoleAuthorization")

        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: RoleBasedAuthorizer.() -> Unit
        ): RoleAuthorization {
            val configuration = RoleBasedAuthorizer().apply { configure() }
            return RoleAuthorization(configuration)
        }
    }
}

internal class Configuration(val provider: RoleBasedAuthorizer) {
    fun copy() = Configuration(provider)
}