package org.guilhermebauer.habit_tracker_mobile

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "HabitTrackerMobile",
    ) {
        App()
    }
}