package com.misw.vinilos.utils

import android.util.Log
import java.text.ParseException
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

fun formatDateFromString(dateString: String): String {
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    val outputFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    try {
        val date = inputFormatter.parse(dateString)
        if (date != null) {
            return outputFormatter.format(date)
        }
    } catch (e: ParseException) {
        Log.d("Parsing date error", e.message+"")
    }

    return dateString
}