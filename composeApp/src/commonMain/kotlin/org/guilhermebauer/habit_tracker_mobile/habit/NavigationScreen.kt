package org.guilhermebauer.habit_tracker_mobile.habit

sealed class Screen(val route: String){

    object HabitList : Screen("habit_list")
    object NewHabit : Screen("new_habit")
}