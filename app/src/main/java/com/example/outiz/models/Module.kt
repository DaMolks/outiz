package com.example.outiz.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Module(
    @StringRes val id: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val iconRes: Int
)