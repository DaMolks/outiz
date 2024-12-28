package com.example.outiz.utils

object Constants {
    const val DATABASE_NAME = "outiz_database"
    const val PREFERENCES_NAME = "outiz_preferences"

    // Types de tâches
    object TaskTypes {
        const val INSTALLATION = "installation"
        const val MAINTENANCE = "maintenance"
        const val REPAIR = "repair"
    }

    // Status des rapports
    object ReportStatus {
        const val DRAFT = "draft"
        const val IN_PROGRESS = "in_progress"
        const val COMPLETED = "completed"
    }
}