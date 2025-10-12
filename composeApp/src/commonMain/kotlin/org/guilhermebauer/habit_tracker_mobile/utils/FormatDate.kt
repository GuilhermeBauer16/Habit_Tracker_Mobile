package org.guilhermebauer.habit_tracker_mobile.utils

import kotlinx.datetime.LocalDate

fun formatDate(date: LocalDate?): String{
    if(date == null){
        return ""
    }
    val day = date.dayOfMonth.toString().padStart(2, '0')
    val month = date.monthNumber.toString().padStart(2, '0')
    val year = date.year.toString()

    return "$day/$month/$year"


}