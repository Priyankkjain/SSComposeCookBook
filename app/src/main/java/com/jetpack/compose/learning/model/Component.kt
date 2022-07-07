package com.jetpack.compose.learning.model

import androidx.compose.runtime.Stable

@Stable
data class Component(
    val componentName: String,
    val className: Class<*>
)