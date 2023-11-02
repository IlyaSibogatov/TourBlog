package com.example.tourblog.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tourblog.ui.theme.DarkOnPrimary
import com.example.tourblog.utils.Constants.CONTENT_DESCRIPTION

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainEntryPoint() {
    val navController = rememberNavController()

    val bottomBarItems = listOf(
        BottomNavBar.Main,
        BottomNavBar.Map,
        BottomNavBar.Chats,
        BottomNavBar.Reservation,
        BottomNavBar.Profile
    )

    val newBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = newBackStackEntry?.destination

    val isBottomBarItem =
        navController.currentBackStackEntryAsState().value?.destination?.route in bottomBarItems.map { it.route }

    val selectedBottomItem = remember { mutableStateOf(BottomNavBar.Main.route) }

    if (bottomBarItems.find { it.route == currentDestination?.route } != null)
        selectedBottomItem.value = currentDestination?.route!!

    Scaffold(
        topBar = {
            if (isBottomBarItem) {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = bottomBarItems.find {
                                    it.route == currentDestination?.route
                                }.let {
                                    it?.title?.let {
                                        stringResource(id = it)
                                    } ?: ""
                                },
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 24.sp,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background,
                tonalElevation = 0.dp
            ) {
                bottomBarItems.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painterResource(id = screen.icon),
                                contentDescription = CONTENT_DESCRIPTION,
                                modifier = Modifier.alpha(
                                    if (selectedBottomItem.value == screen.route) 1f else 0.3f
                                ),
                            )
                        },
                        label = {
                            Text(
                                modifier = Modifier.alpha(
                                    if (selectedBottomItem.value == screen.route) 1f else 0.3f
                                ),
                                text = stringResource(id = screen.title),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        selected = selectedBottomItem.value == screen.route,
                        onClick = {
                            if (navController.currentDestination?.route != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                                selectedBottomItem.value = screen.route
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DarkOnPrimary,
                            selectedTextColor = DarkOnPrimary,
                            indicatorColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            NavGraph(
                navController = navController
            )
        }
    }
}