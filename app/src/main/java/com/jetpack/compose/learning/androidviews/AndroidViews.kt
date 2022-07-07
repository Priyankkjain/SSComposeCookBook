package com.jetpack.compose.learning.androidviews

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class AndroidViews : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                AndroidViewSample()
            }
        }
    }

    @Preview
    @Composable
    fun AndroidViewSample() {
        Column(Modifier.background(MaterialTheme.colors.background)) {
            TopAppBar(
                title = { Text(text = "XML Views in Compose", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            XmlContent()
        }
    }

    @Composable
    fun XmlContent() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Text(
                text = "Below View is inflated from Xml in AndroidView Component",
                modifier = Modifier.padding(15.dp)
            )

            AndroidView(
                factory = {
                    View.inflate(it, R.layout.compose, null)
                },
                update = {
                    val textView: AppCompatTextView = it.findViewById(R.id.txtAppCompatTextView)
                    val button: AppCompatButton = it.findViewById(R.id.btnAppCompatButton)

                    button.setOnClickListener { view ->
                        Toast.makeText(view.context, "AppCompat Button Clicked", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier.fillMaxWidth(0.85f)
            )
        }
    }
}

