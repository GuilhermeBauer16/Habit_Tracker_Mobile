package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.guilhermebauer.habit_tracker_mobile.habit.data.FrequencyType
import org.guilhermebauer.habit_tracker_mobile.habit.data.Habit
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
@OptIn(ExperimentalMaterial3Api::class)
fun NewHabitScreen(onSaveHabit: (Habit) -> Unit) {

    var habitName by remember { mutableStateOf("") }
    var habitDescription by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(LocalDate(2025, 1, 1)) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var frequencyType by remember { mutableStateOf(FrequencyType.DAILY) }
    var isExpanded by remember { mutableStateOf(false) }
    var frequencyOptions = FrequencyType.entries.toList()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Create New Habit") })

        }) {

            paddingValues ->
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
                placeholder = { Text("E.g., Drink Water") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = habitDescription,
                onValueChange = { habitDescription = it },
                label = { Text("Description") },
                placeholder = { Text("What is your goal?") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)


            )

            Text("Start Date: ${startDate.toString()}")

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = {isExpanded = it},
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ){

                OutlinedTextField(
                    value = frequencyType.name,
                    onValueChange = {},
                    readOnly = true,
                    label = {Text("Frequency")},
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) })

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = {isExpanded = false}
                ){
                    frequencyOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = {Text(selectionOption.name)},
                            onClick = {frequencyType = selectionOption
                                isExpanded = false},
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
                    }


                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = habitName.isNotBlank()

            ) {

                Text("Save habit")
            }

        }

    }


}