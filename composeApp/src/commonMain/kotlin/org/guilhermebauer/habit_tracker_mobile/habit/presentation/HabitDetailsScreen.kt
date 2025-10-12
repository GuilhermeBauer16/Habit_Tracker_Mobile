package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.guilhermebauer.habit_tracker_mobile.habit.data.Habit
import org.guilhermebauer.habit_tracker_mobile.utils.formatDate
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HabitDetailsScreen(viewModel: HabitViewModel, onBack: () -> Unit) {

    val habit = viewModel.selectedHabit ?: return
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habit Details") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {

                    IconButton(onClick = onBack){
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("📝 Description", fontWeight = FontWeight.Bold)
            Text(habit.description.ifEmpty { "No description provided." })

            Spacer(Modifier.height(12.dp))

            Text("📅 Start Date: ${formatDate(habit.startDate)}")

            Spacer(Modifier.height(12.dp))

            if (habit.endDate != null) {

                Text("🏁 End Date: ${formatDate(habit.endDate)}")
            } else {

                Text("🏁 End Date: Not defined")
            }

            Spacer(Modifier.height(12.dp))

            Text("🔁 Frequency: ${habit.frequencyType.name.lowercase()}")
        }
    }
}
