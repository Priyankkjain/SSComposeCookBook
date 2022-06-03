package com.jetpack.compose.learning.theme

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Palette
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.gson.Gson
import com.jetpack.compose.learning.list.advancelist.model.Movie
import kotlinx.coroutines.launch

class ThemeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            if (appTheme.value.darkTheme) {
                systemUiController.setSystemBarsColor(
                    appTheme.value.pallet.getMaterialColors(true).surface,
                    false
                )
            } else {
                systemUiController.setSystemBarsColor(
                    appTheme.value.pallet.getMaterialColor(),
                    true
                )
            }
            ComposeCookBookTheme(
                darkTheme = appTheme.value.darkTheme,
                colorPalette = appTheme.value.pallet
            ) {
                ThemeScreen(appTheme)
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ThemeScreen(appTheme: MutableState<AppThemeState>) {
        val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()

        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(16.dp),
            sheetContent = {
                ThemePicker(
                    isDarkModeSelected = appTheme.value.darkTheme,
                    selectedColorPallet = appTheme.value.pallet,
                    onColorPalletChange = {
                        appTheme.value = appTheme.value.copy(pallet = it)
                    },
                    onDarkModeChange = {
                        appTheme.value = appTheme.value.copy(darkTheme = it)
                    },
                )
            },
        ) {
            ThemeBody {
                coroutineScope.launch {
                    if (bottomSheetState.isVisible) bottomSheetState.hide()
                    else bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }
        }
    }

    @Composable
    private fun ThemeBody(onThemeClick: () -> Unit) {
        val navHostController = rememberNavController()
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Theme") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = onThemeClick) {
                        Icon(Icons.Filled.Palette, contentDescription = null)
                    }
                }
            )
        }, bottomBar = {
            BottomNavBar(navHostController)
        }) {
            Navigation(navHostController, Modifier.padding(it))
        }
    }

    @Composable
    fun BottomNavBar(navHostController: NavHostController) {
        val items = listOf(ThemeNavigationScreenType.Widgets, ThemeNavigationScreenType.Demo)
        BottomNavigation {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val parentName = navBackStackEntry?.destination?.parent?.route
            Log.d("Widget Theme", "BottomNavBar: Current Route -> $currentRoute")
            Log.d(
                "Widget Theme",
                "BottomNavBar: Parent Navgraph available -> ${navBackStackEntry?.destination?.parent == null}"
            )
            Log.d("Widget Theme", "BottomNavBar: Parent Route of  Current Route -> $parentName")
            items.forEach { screen ->
                BottomNavigationItem(
                    onClick = {
                        if (currentRoute?.contains(screen.route) == false) {
                            navHostController.popBackStack(
                                navHostController.graph.startDestinationId,
                                false
                            )
                            navHostController.navigate(screen.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = { Icon(screen.icon, "") },
                    label = { Text(text = screen.title) },
                    selected = screen.route == currentRoute,
                    selectedContentColor = MaterialTheme.colors.onPrimary,
                    unselectedContentColor = Color.LightGray
                )
            }
        }
    }

    @Composable
    fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
        NavHost(
            navController,
            startDestination = ThemeNavigationScreenType.Widgets.route,
            modifier = modifier
        ) {
            composable(ThemeNavigationScreenType.Widgets.route) {
                WidgetScreen()
            }
            listingGraph(navController)
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ThemePicker(
        isDarkModeSelected: Boolean,
        selectedColorPallet: ColorPalette,
        onColorPalletChange: (ColorPalette) -> Unit,
        onDarkModeChange: (Boolean) -> Unit
    ) {
        val spaceHeight = 24.dp
        Column(Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier
                    .clickable {
                        onDarkModeChange(!isDarkModeSelected)
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Dark Mode", style = MaterialTheme.typography.h6)
                Spacer(Modifier.requiredWidth(spaceHeight))
                Switch(checked = isDarkModeSelected, onCheckedChange = onDarkModeChange)
            }
            Spacer(Modifier.requiredHeight(spaceHeight))
            Text(text = "Change theme of this screen : ", style = MaterialTheme.typography.h6)
            Spacer(Modifier.requiredHeight(spaceHeight / 2))
            val colorPallet = ColorPalette.values()
            LazyVerticalGrid(cells = GridCells.Fixed(3)) {
                items(
                    colorPallet.size,
                    itemContent = {
                        val isSelected = colorPallet[it] == selectedColorPallet
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                Modifier
                                    .padding(20.dp)
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(colorPallet[it].getMaterialColor())
                                    .clickable {
                                        onColorPalletChange(colorPallet[it])
                                    }
                                    .then(
                                        if (isSelected) {
                                            Modifier.border(
                                                BorderStroke(2.dp, MaterialTheme.colors.onSurface),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                        } else Modifier
                                    )
                            ) {
                                if (isSelected) {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Theme Selected",
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(40.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    },
                )
            }
        }
    }

    private fun NavGraphBuilder.listingGraph(navController: NavController) {
        navigation(
            startDestination = DemoNavigationScreenType.Listing.route,
            route = ThemeNavigationScreenType.Demo.route
        ) {
            composable(DemoNavigationScreenType.Listing.route) { DemoListingScreen(navController) }
            composable(DemoNavigationScreenType.Detail.route.plus("/{movie}")) {
                val movieJson = it.arguments?.getString("movie")
                val movieObject = Gson().fromJson(movieJson, Movie::class.java)
                DemoDetailScreen(movieObject)
            }
        }
    }
}