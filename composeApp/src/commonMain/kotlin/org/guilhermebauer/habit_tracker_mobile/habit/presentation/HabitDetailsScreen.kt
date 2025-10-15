package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun HabitDetailsScreen(
    viewModel: HabitViewModel,
    onBack: () -> Unit,
    onEdit: (Habit) -> Unit
) {

    val habit = viewModel.selectedHabit ?: return

    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habit Details") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {

                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }

                    IconButton(onClick = {
                        showDeleteDialog = true
                    }) {
                        Icon(
                            Icons.Default.Delete, contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                        onBack
                    }

                },
                actions = {
                    IconButton(onClick = { onEdit(habit) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
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

    if (showDeleteDialog) {

        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Habit") },
            text = { Text("Are you sure you want to delete this habit?") },
            confirmButton = {

                TextButton(onClick = {

                    viewModel.deleteHabit(habit)
                    showDeleteDialog = false
                    onBack()


                }
                ) {
                    Text(
                        "Delete", color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }

            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        )
    }
}


