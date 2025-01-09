package com.example.outiz.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDate(pattern: String = "dd/MM/yyyy"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

fun Date.formatDateTime(pattern: String = "dd/MM/yyyy HH:mm"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}