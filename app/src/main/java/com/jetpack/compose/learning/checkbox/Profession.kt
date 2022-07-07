package com.jetpack.compose.learning.checkbox

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf

@Stable
data class Profession(var text: String, var value: MutableState<Boolean> = mutableStateOf(false))