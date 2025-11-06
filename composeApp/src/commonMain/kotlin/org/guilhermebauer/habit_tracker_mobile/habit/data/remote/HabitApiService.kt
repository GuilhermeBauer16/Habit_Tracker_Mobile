package org.guilhermebauer.habit_tracker_mobile.habit.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import org.guilhermebauer.habit_tracker_mobile.habit.data.Habit

class HabitApiService(private val client: HttpClient) {

    private val baseUrl = "http://10.0.2.2:8080/habit"



    suspend fun getAllHabits(): List<Habit> {
        val response: PageResponse<Habit> = client.get(baseUrl).body()
        return response.content
    }

    suspend fun getHabitById(id: String): Habit {
        return client.get("$baseUrl/$id").body()


    }

    suspend fun createHabit(habit: Habit): Habit = client.post(baseUrl) {
        contentType(ContentType.Application.Json)
        setBody(habit)
    }.body()

    suspend fun updateHabit(habit: Habit): Habit = client.put("$baseUrl/updateHabit/${habit.id}") {
        contentType(ContentType.Application.Json)
        setBody(habit)
    }.body()

    suspend fun deleteHabit(id: String) {
        client.delete("$baseUrl/$id")

    }
}

@Serializable
data class PageResponse<T>(
    val content: List<T>,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int
)

