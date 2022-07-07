package com.jetpack.compose.learning.demosamples

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.demosamples.instagramdemo.InstagramSplashActivity
import com.jetpack.compose.learning.model.Component
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class SampleUIActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Sample UI") },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                }) {
                    LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
                        items(getComponents(), key = {
                            it.className.name
                        }) {
                            ButtonComponent(it)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ButtonComponent(component: Component) {
        val context = LocalContext.current
        Button(
            onClick = {
                startActivity(Intent(context, component.className))
            }, modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = component.componentName,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }

    private fun getComponents(): List<Component> = listOf(
        Component("Instagram App", InstagramSplashActivity::class.java),
    )
}