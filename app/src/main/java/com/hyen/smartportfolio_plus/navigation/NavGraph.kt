package com.hyen.smartportfolio_plus.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hyen.smartportfolio_plus.screens.AboutScreen
import com.hyen.smartportfolio_plus.screens.ContactFormScreen
import com.hyen.smartportfolio_plus.screens.ContactScreen
import com.hyen.smartportfolio_plus.screens.HomeScreen
import com.hyen.smartportfolio_plus.screens.MoreScreen
import com.hyen.smartportfolio_plus.screens.ProjectFormScreen
import com.hyen.smartportfolio_plus.screens.ProjectScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                scaffoldState = scaffoldState,
                scope = scope,
                paddingValues = paddingValues
            )
        }
        composable("about") {
            AboutScreen(
                scaffoldState = scaffoldState,
                scope = scope
            )
        }
        composable("project") {
            ProjectScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope
            )
        }
        composable("projectForm") {
            ProjectFormScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope,
                projectId = null
            )
        }
        composable("projectForm/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            ProjectFormScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope,
                projectId = id
            )
        }
        composable("more") {
            MoreScreen(
                scaffoldState = scaffoldState,
                scope = scope
            )
        }
        composable("contact") {
            ContactScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope
            )
        }
        composable("contactForm") {
            ContactFormScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope,
                contactId = null
            )
        }
        composable("contactForm/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            ContactFormScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope,
                contactId = id
            )
        }
    }
}
