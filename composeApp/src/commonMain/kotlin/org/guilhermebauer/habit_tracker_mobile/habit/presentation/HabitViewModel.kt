package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.guilhermebauer.habit_tracker_mobile.habit.data.Habit
import org.guilhermebauer.habit_tracker_mobile.habit.data.KtorClientProvider

class HabitViewModel : ViewModel() {

    private val api = KtorClientProvider.habitApi

    var habits by mutableStateOf<List<Habit>>(emptyList())
        private set
    var selectedHabit by mutableStateOf<Habit?>(null)


    fun loadHabits() {

        viewModelScope.launch {

            try {
                habits = api.getAllHabits()
                println("loaded: ${habits.size}")
            } catch (e: Exception) {
                println("Error loading habits: ${e.message}")

            }

        }
    }

    suspend fun addHabit(habit: Habit) {

        try {
            api.createHabit(habit)
            loadHabits()
        } catch (e: Exception) {
            println("Error adding habit: ${e.message}")

        }

    }


    suspend fun updateHabit(updatedHabit: Habit) {


        try {
            api.updateHabit(updatedHabit)
            loadHabits()
            selectedHabit = updatedHabit
        } catch (e: Exception) {
            println("Error updating habit: ${e.message}")
        }


    }


    suspend fun deleteHabit(id: String) {


        try {
            api.deleteHabit(id)
            loadHabits()
        } catch (e: Exception) {
            println("Error deleting habit: ${e.message}")
        }


    }

}