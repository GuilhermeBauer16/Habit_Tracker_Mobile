package org.guilhermebauer.habit_tracker_mobile.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

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
            value = formatDate(date),
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