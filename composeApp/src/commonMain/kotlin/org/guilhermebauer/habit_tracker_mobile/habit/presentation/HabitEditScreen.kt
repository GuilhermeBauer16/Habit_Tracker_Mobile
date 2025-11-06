package org.guilhermebauer.habit_tracker_mobile.habit.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.guilhermebauer.habit_tracker_mobile.habit.data.FrequencyType
import org.guilhermebauer.habit_tracker_mobile.utils.DatePickerField
import org.guilhermebauer.habit_tracker_mobile.utils.DateSelectionDialog

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

    var isStartDatePickerVisible by remember { mutableStateOf(false) }
    var isEndDatePickerVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val coroutineScope = rememberCoroutineScope()



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Edit Habit",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
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
                    IconButton(onClick = {

                        if (name.isBlank()) {
                            showError = true
                        } else {
                            coroutineScope.launch {
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
                        }
                    }) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)

        ) {


            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
                    .shadow(1.dp, RoundedCornerShape(12.dp))


            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)


                ) {

                    Text(
                        "Habit Details",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )


                }
            }




            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = showError && name.isBlank(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,


                    )
            )

            if (showError && name.isBlank()) {
                Text(
                    "Name cannot be empty",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }


            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,


                    )
            )

            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

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

            Spacer(Modifier.height(24.dp))

            Text(
                "Tap ✓ to save your changes.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )



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
}