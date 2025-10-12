package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import org.guilhermebauer.habit_tracker_mobile.habit.data.FrequencyType
import org.guilhermebauer.habit_tracker_mobile.habit.data.Habit
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HabitListScreen(onNavigateToNewHabit: () -> Unit) {

    val habits = remember {
        listOf(
            Habit("Morning Run", "Run 3km every morning", LocalDate(2025, 1, 1), null, frequencyType = FrequencyType.DAILY),
            Habit("Meditation", "10 minutes mindfulness", LocalDate(2025, 1, 1), null, frequencyType = FrequencyType.DAILY),
            Habit("Read", "Read 20 pages daily", LocalDate(2025, 1, 1), null, frequencyType = FrequencyType.DAILY)
        )
    }

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("Your Habits") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,

                )

            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToNewHabit,
                containerColor = MaterialTheme.colorScheme.primary
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Habit",
                    tint = Color.White
                )
            }


        }
    ) { paddingValues ->

        if (habits.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center

            ) {

                Text(
                    text = "No habits yet. \nTap the + button to create one!",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = Color.Gray
                )


            }


        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                items(habits) { habit ->

                    HabitCard(habit = habit)
                    Spacer(modifier = Modifier.height(12.dp))
                }



            }

        }


    }
}


@Composable
fun HabitCard(habit: Habit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 90.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )

    ) {

        Column(modifier = Modifier.padding(16.dp)){

            Text(
                text = habit.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = habit.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray

            )

            Text(
                text = "Start Date: ${formatDate(habit.startDate)}" ,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray

            )

            Text(
                text = "Frequency: ${habit.frequencyType.name.lowercase()}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray

            )


        }
    }


}

fun formatDate( date: LocalDate): String{

    val day = date.dayOfMonth.toString().padStart(2, '0')
    val month = date.monthNumber.toString().padStart(2, '0')
    val year = date.year.toString()

    return "$day/$month/$year"


}