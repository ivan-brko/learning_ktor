package com.example.routes.routeBuilders

import io.ktor.locations.Location

@Location("{email}")
data class EmailLocation(val email: String)