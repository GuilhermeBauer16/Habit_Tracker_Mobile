package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.datetime.LocalDate
import org.guilhermebauer.habit_tracker_mobile.habit.data.FrequencyType
import org.guilhermebauer.habit_tracker_mobile.habit.data.Habit

class HabitViewModel : ViewModel() {

    val habits = listOf(
        Habit(
            "Morning Run",
            "Run 3km every morning",
            LocalDate(2025, 1, 1),
            LocalDate(2025, 1, 1),
            FrequencyType.DAILY
        ),
        Habit(
            "Meditation",
            "10 minutes mindfulness",
            LocalDate(2025, 1, 1),
            null,
            FrequencyType.DAILY
        ),
        Habit(
            "Read",
            "Read 20 pages daily",
            LocalDate(2025, 1, 1),
            null,
            FrequencyType.DAILY
        )
    )

    var selectedHabit: Habit? by mutableStateOf(null)

}