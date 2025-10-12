package org.guilhermebauer.habit_tracker_mobile
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.guilhermebauer.habit_tracker_mobile.habit.presentation.HabitListScreen
import org.guilhermebauer.habit_tracker_mobile.habit.presentation.NewHabitScreen

sealed class Screen(var route: String) {

    object HabitList : Screen("habit_list")
    object NewHabit : Screen("new_habit")
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.HabitList.route) {

        composable(Screen.HabitList.route) {
            HabitListScreen(
                onNavigateToNewHabit = { navController.navigate(Screen.NewHabit.route) }
            )
        }

        composable(Screen.NewHabit.route) {
            NewHabitScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }


    }
}


