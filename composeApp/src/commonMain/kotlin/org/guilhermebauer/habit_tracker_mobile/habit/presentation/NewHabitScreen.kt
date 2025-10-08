package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.guilhermebauer.habit_tracker_mobile.habit.data.FrequencyType
import org.guilhermebauer.habit_tracker_mobile.habit.data.Habit
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun NewHabitScreen(onSaveHabit: (Habit) -> Unit = {}) {

    var habitName by remember { mutableStateOf("") }
    var habitDescription by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(LocalDate(2025, 1, 1)) }
    var isStartDatePickerVisible by remember { mutableStateOf(false) }
    var isEndDatePickerVisible by remember { mutableStateOf(false) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var frequencyType by remember { mutableStateOf(FrequencyType.DAILY) }
    var isExpanded by remember { mutableStateOf(false) }
    val frequencyOptions = FrequencyType.entries.toList()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = null)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Create New Habit") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = habitName,
                onValueChange = { habitName = it },
                label = { Text("Habit Name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
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
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))



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

            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(Modifier.weight(1f))

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

                        habitName = ""
                        habitDescription = ""


                        startDate = LocalDate(2025, 1, 1)
                        endDate = null

                        frequencyType = FrequencyType.DAILY
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = habitName.isNotBlank()
            ) {
                Text("Save habit")
            }
        }

        if(isStartDatePickerVisible){
            DateSelectionDialog(
                currentDate = startDate,
                onDateSelected = {newDate -> startDate = newDate},
                onDismiss = {isStartDatePickerVisible = false}
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

@Composable
fun DatePickerField(
    label: String,
    date: LocalDate?,
    onDateClicked: () -> Unit,
    onDateCleared: () -> Unit,
    isDateOptional: Boolean,
    modifier: Modifier = Modifier
) {
    val clearButtonExclusionTarget = if (isDateOptional && date != null) 48.dp else 0.dp
    val calendarIconTouchTarget = 48.dp

    val exclusionWidth = if (isDateOptional && date != null) {

        clearButtonExclusionTarget + calendarIconTouchTarget
    } else {
        calendarIconTouchTarget
    }

    Box(modifier = modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = date?.toString() ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = modifier.fillMaxWidth()
        )



        Spacer(
            modifier.matchParentSize()
                .padding(end = exclusionWidth)
                .clickable(onClick = onDateClicked)
        )

        IconButton(
            onClick = onDateClicked,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = if (isDateOptional && date != null) (-48).dp else 0.dp)
        ) {

            Text("📅")

        }

        if (isDateOptional && date != null) {
            IconButton(
                onClick = onDateCleared,
                modifier = Modifier.align(Alignment.CenterEnd)

            ) {
                Text("❌")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectionDialog(
    currentDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {

    val initialDateMillis =
        currentDate?.atStartOfDayIn(TimeZone.currentSystemDefault())?.toEpochMilliseconds()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedMillis = datePickerState.selectedDateMillis
                if (selectedMillis != null) {

                    val newDate = Instant.fromEpochMilliseconds(selectedMillis)
                        .toLocalDateTime(TimeZone.UTC).date
                    onDateSelected(newDate)
                }
                onDismiss()
            }) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}




