package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
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
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Habit Details",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {

                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }


                },
                actions = {
                    IconButton(onClick = { onEdit(habit) }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
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
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    DetailRow("📛 Name", habit.name)
                    DetailRow(
                        "📝 Description",
                        habit.description.ifEmpty { "No description provided." })
                    DetailRow("📅 Start Date", formatDate(habit.startDate))
                    DetailRow("🏁 End Date", habit.endDate?.let { formatDate(it) } ?: "Not defined")
                    DetailRow("🔁 Frequency", habit.frequencyType.name.lowercase())
                }

            }
        }
    }

    if (showDeleteDialog) {

        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Habit") },
            text = { Text("Are you sure you want to delete this habit?") },
            confirmButton = {

                TextButton(onClick = {
                    coroutineScope.launch {
                        viewModel.deleteHabit(habit.id)
                        showDeleteDialog = false
                        onBack()
                    }

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

@Composable
fun DetailRow(label: String, value: String) {

    Column {
        Text(
            label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


