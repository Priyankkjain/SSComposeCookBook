package com.jetpack.compose.learning.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.tabarlayout.indicatorSetUp

private val spaceHeight = 16.dp

@Composable
fun WidgetScreen() {
    LazyColumn(contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp)) {
        item { AppBarWidgets() }
        item { ButtonsWidgets() }
        item { CheckBoxWidgets() }
        item { FloatingActionButton() }
        item { ProgressIndicator() }
        item { RadioButtons() }
        item { SnackBarWidgets() }
        item { SliderWidgets() }
        item { SwitchWidgets() }
        item { TabBarWidgets() }
        item { TextWidgets() }
        item { TextFieldWidgets() }
    }
}

@Composable
fun AppBarWidgets() {
    WidgetTitle("App Bars")
    TopAppBar(
        title = { Text("Default Primary-Surface") },
    )
    VerticalSpacer()
    TopAppBar(
        title = { Text("Primary Color Background") },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
        },
        actions = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        }
    )
    VerticalSpacer()
}

@Composable
fun ButtonsWidgets() {
    val buttonPadding = 10.dp
    val textPadding = 5.dp
    WidgetTitle("Buttons")
    Row {
        // Simple Button
        Button(
            onClick = {},
            modifier = Modifier.padding(buttonPadding)
        ) {
            Text(text = "Simple Button", Modifier.padding(textPadding))
        }

        // Button with Border
        Button(
            onClick = {},
            modifier = Modifier.padding(buttonPadding),
            border = BorderStroke(width = 2.dp, MaterialTheme.colors.onSurface),
        ) {
            Text(text = "Button with Border", Modifier.padding(textPadding), fontSize = 12.sp)
        }
    }
    VerticalSpacer()
    Row {
        // Rounded Button
        Button(
            onClick = {},
            modifier = Modifier.padding(buttonPadding),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "Rounded Button", Modifier.padding(textPadding))
        }

        // Disable Button
        Button(
            onClick = {}, enabled = false,
            modifier = Modifier.padding(buttonPadding),
        ) {
            Text(text = "Disable Button", Modifier.padding(textPadding))
        }
    }
    VerticalSpacer()
    Row {
        // Outlined Button
        OutlinedButton(
            onClick = {},
            modifier = Modifier.padding(buttonPadding)
        ) {
            Text("Outlined Button", Modifier.padding(textPadding))
        }

        // Text Button
        TextButton(onClick = {}, modifier = Modifier.padding(buttonPadding)) {
            Text("Text Button", Modifier.padding(textPadding))
        }
    }
    VerticalSpacer()
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Icon Button
        IconButton(
            onClick = {},
            modifier = Modifier.padding(buttonPadding)
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Icon Button",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(60.dp)
            )
        }

        // Icon Button
        Button(
            onClick = {}, modifier = Modifier.padding(buttonPadding),
        ) {
            Icon(
                imageVector = Icons.Filled.PersonAdd,
                contentDescription = "Icon with Text Button",
                modifier = Modifier.padding(all = 5.dp)
            )
            Text(text = "Icon Button with Text")
        }
    }

    VerticalSpacer()
}

@Composable
fun CheckBoxWidgets() {
    WidgetTitle("Check Box")
    var isChecked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .clickable { isChecked = !isChecked },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
            },
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
        )
        Text(
            text = "Great Sample", Modifier.padding(start = 5.dp), fontSize = 16.sp
        )
    }
    VerticalSpacer()
}

@Composable
fun FloatingActionButton() {
    WidgetTitle("Floating Action Buttons")
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            //FAB custom color
            FloatingActionButton(
                onClick = { },
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(Icons.Filled.Add, "")
            }

            //Simple FAB with custom content (similar of Exntended FAB)
            FloatingActionButton(onClick = { },
                backgroundColor = MaterialTheme.colors.primary,
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    ) {
                        Icon(Icons.Filled.Add, "")
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Simple FAB")
                    }
                })

        }
    }
    VerticalSpacer()
}

@Composable
fun ProgressIndicator() {
    WidgetTitle("Progress Indicator")
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LinearProgressIndicator()
        CircularProgressIndicator()
        CircularProgressIndicator(color = MaterialTheme.colors.onSurface)
    }
    VerticalSpacer()
}

@Composable
fun RadioButtons() {
    var customUnSelectedColorRadioButton by remember { mutableStateOf(false) }

    WidgetTitle("Radio Buttons")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        RadioButton(
            selected = customUnSelectedColorRadioButton,
            onClick = {
                customUnSelectedColorRadioButton = !customUnSelectedColorRadioButton
            },
            colors = RadioButtonDefaults.colors(
                unselectedColor = MaterialTheme.colors.primary,
                selectedColor = MaterialTheme.colors.primary
            ),
            modifier = Modifier.padding(end = 5.dp)
        )
        Text(text = "Custom unselected color Radio button",
            Modifier.clickable {
                customUnSelectedColorRadioButton = !customUnSelectedColorRadioButton
            })
    }

    VerticalSpacer()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SliderWidgets() {
    var basicSliderPosition by remember { mutableStateOf(0f) }
    var sliderPosition by remember { mutableStateOf(0f..100f) }

    WidgetTitle("Slider")
    Column {
        Text(text = "Slider value =  $basicSliderPosition", fontSize = 16.sp)
        Slider(value = basicSliderPosition, onValueChange = { basicSliderPosition = it })
        VerticalSpacer()
        Text(
            text = "From range  ${sliderPosition.start}  to  ${sliderPosition.endInclusive}",
            fontSize = 16.sp
        )
        RangeSlider(
            steps = 5,
            values = sliderPosition,
            onValueChange = { it -> sliderPosition = it },
            valueRange = 0f..100f,
            onValueChangeFinished = {
                // some business logic update with the state you hold
            },
        )
    }
    VerticalSpacer()
}

@Composable
fun SnackBarWidgets() {
    WidgetTitle("SnackBar")
    Column {
        Snackbar {
            Text(text = "Basic snackbar")
        }
        VerticalSpacer()
        Snackbar(
            action = {
                TextButton(onClick = {}) {
                    Text(text = "Click Me")
                }
            },
        ) {
            Text(text = "Snackbar with action button")
        }
        VerticalSpacer()
        Snackbar(
            backgroundColor = MaterialTheme.colors.primary,
            action = {
                TextButton(onClick = {}) {
                    Text(text = "ClickMe")
                }
            }) {
            Text(text = "Custom color background", color = MaterialTheme.colors.onPrimary)
        }
    }
    VerticalSpacer()
}

@Composable
fun SwitchWidgets() {
    var simpleSwitchState by remember { mutableStateOf(false) }
    var customEnableColorSwitchState by remember { mutableStateOf(true) }
    WidgetTitle("Switch")
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simple Switch.", modifier = Modifier.padding(end = 5.dp))
            Switch(
                checked = simpleSwitchState,
                onCheckedChange = { simpleSwitchState = it }
            )
        }
        // Switch with selected thumb color
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Custom selected thumb color.", modifier = Modifier.padding(end = 5.dp))
            Switch(
                checked = customEnableColorSwitchState,
                onCheckedChange = { customEnableColorSwitchState = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.primary,
                    checkedTrackColor = MaterialTheme.colors.primaryVariant,
                ),
            )
        }
    }
    VerticalSpacer()
}

@Composable
fun TabBarWidgets() {
    WidgetTitle("Tab Bar")
    val tabData = listOf(
        "HOME" to Icons.Filled.Home,
        "FAVORITE" to Icons.Filled.Favorite,
        "DOWNLOAD" to Icons.Filled.GetApp,
        "PICTURES" to Icons.Filled.AddAPhoto,
        "MUSIC" to Icons.Filled.Headset,
        "USER" to Icons.Filled.Person,
        "SETTING" to Icons.Filled.Settings,
    )
    var simpleTabIndex by remember { mutableStateOf(0) }
    var tabIndex by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = simpleTabIndex) {
        tabData.take(3).forEachIndexed { index, model ->
            Tab(selected = simpleTabIndex == index, onClick = {
                simpleTabIndex = index
            }, text = {
                Text(text = model.first)
            })
        }
    }
    VerticalSpacer()
    ScrollableTabRow(selectedTabIndex = tabIndex,
        divider = {
            TabRowDefaults.Divider(
                thickness = 5.dp,
                color = MaterialTheme.colors.primaryVariant
            )
        },
        indicator = {
            TabRowDefaults.Indicator(
                height = 5.dp,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.indicatorSetUp(it[tabIndex])
            )
        }
    ) {
        tabData.forEachIndexed { index, pair ->
            Tab(selected = tabIndex == index, onClick = {
                tabIndex = index
            }, text = {
                Text(text = pair.first)
            }, icon = {
                Icon(imageVector = pair.second, contentDescription = null)
            })
        }
    }
    VerticalSpacer()
}

@Composable
fun TextWidgets() {
    val fontSize = 18.sp
    WidgetTitle("Text")
    Text(
        text = "The Default color is onPrimary",
        Modifier.padding(bottom = 10.dp),
        fontSize = 15.sp
    )
    Text(
        "Text color is Primary ",
        Modifier.padding(bottom = 10.dp),
        color = MaterialTheme.colors.primary,
        fontSize = fontSize
    )
    Text(
        "Text color from style",
        modifier = Modifier
            .padding(bottom = 10.dp),
        fontSize = fontSize,
        style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary)
    )
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                append("Different")
            }
            append(" Color")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary,
                )
            ) {
                append(" Same ")
            }
            append(" TEXT")
        },
        modifier = Modifier.padding(bottom = 10.dp), fontSize = fontSize,
    )
    Text(
        "This text field has background",
        modifier = Modifier
            .padding(bottom = 10.dp)
            .background(MaterialTheme.colors.primary),
        fontSize = fontSize,
    )
    VerticalSpacer()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldWidgets() {
    var simpleText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val (simple, email, password) = remember { FocusRequester.createRefs() }

    WidgetTitle("Text Field")
    TextField(
        value = simpleText,
        onValueChange = { simpleText = it },
        label = { Text("Simple TextField") }, // It will be shown on the top when focussed
        placeholder = { Text("Placeholder text") }, // It will be shown as hint when nothing is typed
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .focusRequester(simple)
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
    VerticalSpacer()
    OutlinedTextField(
        value = emailText,
        onValueChange = { emailText = it },
        label = { Text("Enter email") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "",
                tint = if (MaterialTheme.colors.isLight) LocalContentColor.current.copy(alpha = LocalContentAlpha.current) else MaterialTheme.colors.onSurface
            )
        },
        modifier = Modifier
            .focusRequester(email)
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
    VerticalSpacer()
    OutlinedTextField(
        value = passwordText,
        onValueChange = { passwordText = it },
        placeholder = { Text("Outlined password TextField") },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(disabledBorderColor = MaterialTheme.colors.onSurface),
        trailingIcon = {
            val image = if (passwordVisibility) Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            IconButton({ passwordVisibility = !passwordVisibility }) {
                Icon(
                    imageVector = image,
                    "",
                    tint = if (MaterialTheme.colors.isLight) LocalContentColor.current.copy(alpha = LocalContentAlpha.current) else MaterialTheme.colors.onSurface
                )
            }
        },
        modifier = Modifier
            .focusRequester(password)
            .fillMaxWidth(),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
    VerticalSpacer()
}

@Composable
private fun VerticalSpacer() {
    Spacer(Modifier.requiredHeight(spaceHeight))
}

@Composable
private fun WidgetTitle(title: String) {
    Text(title, style = MaterialTheme.typography.h6)
    Spacer(Modifier.requiredHeight(spaceHeight / 2))
}