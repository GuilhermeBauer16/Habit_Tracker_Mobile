package org.guilhermebauer.habit_tracker_mobile.habit.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.guilhermebauer.habit_tracker_mobile.habit.data.remote.HabitApiService

object KtorClientProvider {

    val httpClient = HttpClient(CIO) {

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        engine {
            requestTimeout = 10_000
            endpoint {
                connectTimeout = 10_000
                socketTimeout = 10_000
            }
        }
    }

    val habitApi = HabitApiService(httpClient)
}