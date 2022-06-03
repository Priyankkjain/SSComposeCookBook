package com.jetpack.compose.learning.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ThemeNavigationScreenType(
    val icon: ImageVector,
    var route: String,
    val title: String
) {
    object Widgets : ThemeNavigationScreenType(Icons.Filled.Widgets, "widgets", "Widgets")
    object Demo : ThemeNavigationScreenType(Icons.Filled.Dashboard, "demo", "Demo")
}

sealed class DemoNavigationScreenType(var route: String, val title: String) {
    object Listing : DemoNavigationScreenType("listing", "Listing")
    object Detail : DemoNavigationScreenType("detail", "Detail")
}