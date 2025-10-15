package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.guilhermebauer.habit_tracker_mobile.habit.data.FrequencyType
import org.guilhermebauer.habit_tracker_mobile.utils.formatDate

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HabitEditScreen(viewModel: HabitViewModel, onBack: () -> Unit) {

    val habit = viewModel.selectedHabit ?: return

    var name by remember { mutableStateOf(habit.name) }
    var description by remember { mutableStateOf(habit.description) }
    var startDate by remember { mutableStateOf(habit.startDate) }
    var endDate by remember { mutableStateOf(habit.endDate) }
    var frequencyType by remember { mutableStateOf(habit.frequencyType) }
    var isExpanded by remember { mutableStateOf(false) }
    val frequencyOptions = FrequencyType.entries.toList()
    var showError by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Habit") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {

                        if (name.isBlank()) {
                            showError = true
                        } else {
                            viewModel.updateHabit(
                                habit.copy(
                                    name = name.trim(),
                                    description = description.trim(),
                                    frequencyType = frequencyType,
                                    startDate = startDate,
                                    endDate = endDate
                                )
                            )
                            onBack()
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {


            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = showError && name.isBlank()

            )

            if (showError && name.isBlank()) {
                Text("Name cannot be empty", color = MaterialTheme.colorScheme.error)
            }


            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            Text(
                "Start Date: ${formatDate(startDate)}",
                fontWeight = FontWeight.SemiBold
            )

            Text(
                "End Date: ${endDate?.let { formatDate(it) } ?: "Not defined"}",
                fontWeight = FontWeight.SemiBold
            )

            ExposedDropdownMenuBox(

                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {

                OutlinedTextField(
                    value = frequencyType.name.lowercase().replaceFirstChar { it.uppercase() },
                    onValueChange = {},
                    label = { Text("Frequency") },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    frequencyOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.name) },
                            onClick = {
                                frequencyType = selectionOption
                                isExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                "Changes will saved when you tap the ✓ icon.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )


        }
    }
}