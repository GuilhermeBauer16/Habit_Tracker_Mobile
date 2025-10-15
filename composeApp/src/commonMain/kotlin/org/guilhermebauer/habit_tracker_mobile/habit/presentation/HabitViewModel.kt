package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlinx.datetime.LocalDate
import org.guilhermebauer.habit_tracker_mobile.habit.data.FrequencyType
import org.guilhermebauer.habit_tracker_mobile.habit.data.Habit
class HabitViewModel : ViewModel() {

    var habits by mutableStateOf(
        mutableListOf(
            Habit("Morning Run", "Run 3km every morning", LocalDate(2025, 1, 1), null, FrequencyType.DAILY),
            Habit("Meditation", "10 minutes mindfulness", LocalDate(2025, 1, 2), null, FrequencyType.DAILY),
            Habit("Read", "Read 20 pages", LocalDate(2025, 1, 3), null, FrequencyType.DAILY)
        ))




        var selectedHabit by mutableStateOf<Habit?>(null)

    fun updateHabit(updated: Habit) {
        habits = habits.map {
            if (it.name == selectedHabit?.name) updated else it
        }
            .toMutableList()

        selectedHabit = updated

    }


    fun deleteHabit(habit: Habit){
        habits = habits.filter { it.name != habit.name }.toMutableList()

        selectedHabit = null
    }

}