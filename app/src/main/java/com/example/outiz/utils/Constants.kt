package com.example.outiz.utils

object Constants {
    const val DATABASE_NAME = "outiz_database"
    const val PREF_TECHNICIAN_PROFILE = "technician_profile_created"

    object ReportTypes {
        const val DRAFT = "draft"
        const val IN_PROGRESS = "in_progress"
        const val COMPLETED = "completed"
    }

    object TimeEntryTypes {
        const val INSTALLATION = "installation"
        const val MAINTENANCE = "maintenance"
        const val REPAIR = "repair"
    }
}