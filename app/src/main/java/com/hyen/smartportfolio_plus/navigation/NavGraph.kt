package com.hyen.smartportfolio_plus.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hyen.smartportfolio_plus.screens.AboutScreen
import com.hyen.smartportfolio_plus.screens.ContactFormScreen
import com.hyen.smartportfolio_plus.screens.ContactScreen
import com.hyen.smartportfolio_plus.screens.HomeScreen
import com.hyen.smartportfolio_plus.screens.LoginScreen
import com.hyen.smartportfolio_plus.screens.MoreScreen
import com.hyen.smartportfolio_plus.screens.ProjectFormScreen
import com.hyen.smartportfolio_plus.screens.ProjectScreen
import com.hyen.smartportfolio_plus.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    authViewModel: AuthViewModel,
    googleSignInLauncher: ActivityResultLauncher<Intent>
) {
    val startRoute = if (authViewModel.isLoggedIn()) "home" else "login"
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable("login") {
            val userId by authViewModel.userIdLiveData.observeAsState()
            LaunchedEffect(userId) {
                if (userId != null) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
            LoginScreen(
                onClickSignIn = {
                    googleSignInLauncher.launch(authViewModel.getSignInIntent())
                }
            )
        }
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
