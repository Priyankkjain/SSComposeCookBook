package com.jetpack.compose.learning.constraintlayout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.model.Component
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class ConstraintLayoutActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                ConstraintLayoutComponents()
            }
        }
    }

    @Preview
    @Composable
    fun ConstraintLayoutComponents() {
        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxHeight()
        ) {
            TopAppBar(
                title = { Text("Constraint Layout") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            LazyColumn(contentPadding = PaddingValues(20.dp)) {
                items(getComponents(), key = {
                    it.className.name
                }) {
                    ConstraintLayoutItems(it)
                    Spacer(Modifier.requiredHeight(24.dp))
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterialApi::class)
    fun ConstraintLayoutItems(component: Component) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            elevation = 5.dp,
            onClick = {
                startActivity(Intent(this, component.className))
            },
            indication = rememberRipple()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(component.componentName, Modifier.weight(1f), fontSize = 16.sp)
                Icon(Icons.Default.KeyboardArrowRight, "", Modifier.size(24.dp))
            }
        }
    }

    private fun getComponents(): List<Component> = listOf(
        Component("Barrier", BarrierActivity::class.java),
        Component("Guideline", GuidelineActivity::class.java),
        Component("Chain", ChainActivity::class.java)
    )
}