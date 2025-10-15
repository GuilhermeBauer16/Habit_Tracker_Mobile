package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.guilhermebauer.habit_tracker_mobile.habit.data.FrequencyType
import org.guilhermebauer.habit_tracker_mobile.habit.data.Habit
import org.guilhermebauer.habit_tracker_mobile.utils.DatePickerField
import org.guilhermebauer.habit_tracker_mobile.utils.DateSelectionDialog
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun NewHabitScreen(
    onSaveHabit: (Habit) -> Unit = {},
    onBack: () -> Unit = {}
) {

    var habitName by remember { mutableStateOf("") }
    var habitDescription by remember { mutableStateOf("") }
    var startDate by remember {
        mutableStateOf(
            Clock.System.now()
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
                .date
        )
    }
    var isStartDatePickerVisible by remember { mutableStateOf(false) }
    var isEndDatePickerVisible by remember { mutableStateOf(false) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var frequencyType by remember { mutableStateOf(FrequencyType.DAILY) }
    var isExpanded by remember { mutableStateOf(false) }
    val frequencyOptions = FrequencyType.entries.toList()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Habit") },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                })

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                "🧩 Habit Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = habitName,
                onValueChange = { habitName = it },
                label = { Text("Habit Name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = habitDescription,
                onValueChange = { habitDescription = it },
                label = { Text("Description") },
                placeholder = { Text("What is your goal?") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
            )

            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

            Text(
                "📅 Schedule",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )


            DatePickerField(
                label = "Start Date",
                date = startDate,
                onDateClicked = { isStartDatePickerVisible = true },
                onDateCleared = {},
                isDateOptional = false
            )


            DatePickerField(
                label = "End date (Optional)",
                date = endDate,
                onDateClicked = { isEndDatePickerVisible = true },
                onDateCleared = { endDate = null },
                isDateOptional = true
            )

//            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = frequencyType.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Frequency") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
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

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    if (habitName.isNotBlank()) {
                        val newHabit = Habit(
                            name = habitName,
                            description = habitDescription,
                            startDate = startDate,
                            endDate = endDate,
                            frequencyType = frequencyType
                        )
                        onSaveHabit(newHabit)
                        onBack()

                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .height(56.dp),
                shape= MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = habitName.isNotBlank(),
            ) {
                Text("Save habit", style = MaterialTheme.typography.titleMedium)
            }
        }

        if (isStartDatePickerVisible) {
            DateSelectionDialog(
                currentDate = startDate,
                onDateSelected = { newDate -> startDate = newDate },
                onDismiss = { isStartDatePickerVisible = false }
            )
        }

        if (isEndDatePickerVisible) {
            DateSelectionDialog(
                currentDate = endDate,
                onDateSelected = { newDate -> endDate = newDate },
                onDismiss = { isEndDatePickerVisible = false }
            )
        }


    }


}




