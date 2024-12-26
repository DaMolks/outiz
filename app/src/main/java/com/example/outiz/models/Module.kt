package com.example.outiz.models

import androidx.annotation.DrawableRes

data class Module(
    val id: String,
    val title: String,
    val description: String,
    @DrawableRes val iconRes: Int,
    val isEnabled: Boolean = true
)