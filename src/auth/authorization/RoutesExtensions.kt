package com.example.auth.authorization

import com.example.utils.Role
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.feature
import io.ktor.routing.*

fun Route.minimalRoleAllowed(role: Role, build: Route.() -> Unit): Route {
    val authorisedRoute = createChild(AuthorisedRouteSelector())

    //TODO: check the whole pipeline thing, why this exact pipeline needs to be sent
    application.feature(RoleAuthorization).interceptPipeline(authorisedRoute, role = role)

    authorisedRoute.build()
    return authorisedRoute
}

class AuthorisedRouteSelector : RouteSelector(RouteSelectorEvaluation.qualityConstant) {
    override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation =
        RouteSelectorEvaluation.Constant
}
