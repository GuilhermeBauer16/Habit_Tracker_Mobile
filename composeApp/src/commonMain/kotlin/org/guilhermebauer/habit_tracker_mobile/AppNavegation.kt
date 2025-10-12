package org.guilhermebauer.habit_tracker_mobile

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.guilhermebauer.habit_tracker_mobile.habit.Screen
import org.guilhermebauer.habit_tracker_mobile.habit.presentation.HabitDetailsScreen
import org.guilhermebauer.habit_tracker_mobile.habit.presentation.HabitListScreen
import org.guilhermebauer.habit_tracker_mobile.habit.presentation.HabitViewModel
import org.guilhermebauer.habit_tracker_mobile.habit.presentation.NewHabitScreen

//sealed class Screen(var route: String) {
//
//    object HabitList : Screen("habit_list")
//    object NewHabit : Screen("new_habit")
//
//    object HabitDetails : Screen("habit_details/{habitName}")
//}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val viewModel = HabitViewModel()




    NavHost(navController, startDestination = Screen.HabitList.route) {

        composable("habit_list") {
            HabitListScreen(
                viewModel = viewModel,
                onNavigateToNewHabit = { navController.navigate("new_habit") },
                onHabitClick = { navController.navigate("habit_details") }
            )
        }

        composable(Screen.NewHabit.route) {
            NewHabitScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.HabitDetails.route) {
            HabitDetailsScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }


}



