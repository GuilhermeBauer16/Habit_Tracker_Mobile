package org.guilhermebauer.habit_tracker_mobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform