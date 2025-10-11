package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview

fun HabitListScreen(onNavigateToNewHabit: () -> Unit){

    Scaffold(

        topBar = { TopAppBar(title = { Text("Your Habits") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToNewHabit) { // <-- Chama a navegação
                Text("+")
            }
        }
    ){ paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            Text("Yours habit list will appear here!")
        }

    }
}