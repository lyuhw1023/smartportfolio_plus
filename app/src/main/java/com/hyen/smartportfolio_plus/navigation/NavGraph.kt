package com.hyen.smartportfolio_plus.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hyen.smartportfolio_plus.data.auth.UserType
import com.hyen.smartportfolio_plus.screens.AboutScreen
import com.hyen.smartportfolio_plus.screens.ContactFormScreen
import com.hyen.smartportfolio_plus.screens.ContactScreen
import com.hyen.smartportfolio_plus.screens.HomeScreen
import com.hyen.smartportfolio_plus.screens.LoadingScreen
import com.hyen.smartportfolio_plus.screens.LoginScreen
import com.hyen.smartportfolio_plus.screens.MoreScreen
import com.hyen.smartportfolio_plus.screens.ProjectFormScreen
import com.hyen.smartportfolio_plus.screens.ProjectScreen
import com.hyen.smartportfolio_plus.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    authViewModel: AuthViewModel,
    googleSignInLauncher: ActivityResultLauncher<Intent>
) {
    val userType by authViewModel.userTypeLiveData.observeAsState(UserType.GUEST)

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            val userId by authViewModel.userIdLiveData.observeAsState()
            var loading by remember { mutableStateOf(false) }

            // userId 있으면 home으로 (로그인 성공)
            LaunchedEffect(userId) {
                if (userId != null && !loading) {
                    loading = true
                }
            }
            if (loading) {
                LoadingScreen()
                LaunchedEffect(Unit) {
                    delay(1000L)
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            } else {
                LoginScreen(
                    // 비회원, 홈으로 이동
                    onGuest = {
                        loading = true
                    },
                    // 로그인 버튼 > Google 로그인
                    onLogin = {
                        googleSignInLauncher.launch(authViewModel.getSignInIntent())
                    }
                )
            }
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
                scope = scope,
                userType = userType,
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
                scope = scope,
                userType = userType
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
