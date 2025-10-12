package org.guilhermebauer.habit_tracker_mobile.habit.data


import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
enum class FrequencyType{

    DAILY,
    WEEKLY,
    MONTHLY
}
@Serializable

data class Habit(
    val name: String ,
    val description: String ,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val frequencyType: FrequencyType
)