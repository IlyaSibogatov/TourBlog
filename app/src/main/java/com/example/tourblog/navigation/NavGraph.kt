package com.example.tourblog.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tourblog.screens.chats.ChatsScreen
import com.example.tourblog.screens.details.BlogDetailsScreen
import com.example.tourblog.screens.main.MainScreen
import com.example.tourblog.screens.maps.MapsScreen
import com.example.tourblog.screens.profile.ProfileScreen
import com.example.tourblog.screens.reservation.ReservationScreen
import com.example.tourblog.utils.Constants.BLOG_ID
import com.example.tourblog.utils.Constants.BLOG_NAME
import com.example.tourblog.utils.Constants.DETAILS_ROUTE

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavBar.Main.route
    ) {
        composable(route = BottomNavBar.Main.route) {
            MainScreen(navController)
        }
        composable(route = BottomNavBar.Map.route) {
            MapsScreen()
        }
        composable(route = BottomNavBar.Chats.route) {
            ChatsScreen()
        }
        composable(route = BottomNavBar.Reservation.route) {
            ReservationScreen()
        }
        composable(route = BottomNavBar.Profile.route) {
            ProfileScreen()
        }
        composable(
            route = "${DETAILS_ROUTE}/{${BLOG_ID}}",
            arguments = listOf(
                navArgument(BLOG_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            BlogDetailsScreen(
                navController,
                id = backStackEntry.arguments?.getString(BLOG_ID)
            )
        }
    }
}