package com.misw.vinilos.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateForDisplay(millis: Long): String {
    if (millis == 0L) return "No Date Selected"
    val formatter = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun convertDateFormatForBackend(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'00:00:00-00:00", Locale.getDefault())
    return formatter.format(Date(millis))
}