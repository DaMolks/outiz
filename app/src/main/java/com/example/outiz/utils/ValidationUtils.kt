package com.example.outiz.utils

object ValidationUtils {
    fun isValidDescription(text: String?): Boolean = !text.isNullOrBlank()

    fun isValidDuration(duration: String?): Boolean {
        return try {
            duration?.toInt()?.let { it > 0 } ?: false
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun isValidSiteCode(code: String?): Boolean = !code.isNullOrBlank() && code.length <= 10

    fun isValidTechnicianId(id: String?): Boolean = !id.isNullOrBlank() && id.length == 8
}