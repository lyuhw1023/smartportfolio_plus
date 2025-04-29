package com.hyen.smartportfolio_plus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.hyen.smartportfolio_plus.navigation.NavGraph
import kotlinx.coroutines.launch
import com.hyen.smartportfolio_plus.components.DrawerContent
import com.hyen.smartportfolio_plus.screens.LoginScreen
import com.hyen.smartportfolio_plus.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(AuthViewModel::class.java)

        // 로그인 결과 리스너 등록
        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // 취소된 경우 무시
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult

            // 결과 Intent에서 계정 가져오기
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                // 성공 시 ViewModel 로 전달
                authViewModel.handleSignIn(account)
            } catch (e: ApiException) {
                Log.e("SignIn", "Google sign-in failed code=${e.statusCode}", e)
            }
        }

        setContent {
            DrawerNavigationApp(
                authViewModel = authViewModel,
                googleSignInLauncher = googleSignInLauncher
            )
        }
    }
}

// 네비게이션
@Composable
fun DrawerNavigationApp(
    authViewModel: AuthViewModel,
    googleSignInLauncher: ActivityResultLauncher<Intent>
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(navController = navController, scaffoldState = scaffoldState)
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            paddingValues = paddingValues,
            scaffoldState = scaffoldState,
            scope = scope,
            authViewModel = authViewModel,
            googleSignInLauncher = googleSignInLauncher
        )
    }
}

// 네비게이션 메뉴
@Composable
fun DrawerContent(navController: androidx.navigation.NavController, scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()

    Column {
        Text(
            text = "About",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )
        Divider(color = Color.Gray)

        DrawerMenuItem(title = "Home", route = "home", navController = navController, scope = scope, scaffoldState = scaffoldState)
        DrawerMenuItem(title = "About", route = "about", navController = navController, scope = scope, scaffoldState = scaffoldState)
        DrawerMenuItem(title = "Project", route = "project", navController = navController, scope = scope, scaffoldState = scaffoldState)
        DrawerMenuItem(title = "Contact", route = "contact", navController = navController, scope = scope, scaffoldState = scaffoldState)
        DrawerMenuItem(title = "More", route = "more", navController = navController, scope = scope, scaffoldState = scaffoldState)
    }
}

// 개별 드로어 메뉴 항목
@Composable
fun DrawerMenuItem(
    title: String,
    route: String,
    navController: androidx.navigation.NavController,
    scope: kotlinx.coroutines.CoroutineScope,
    scaffoldState: ScaffoldState
) {
    Text(
        text = title,
        modifier = Modifier
            // 선택 시 이동
            .clickable {
                navController.navigate(route)
                scope.launch { scaffoldState.drawerState.close() }
            }
            .padding(16.dp)
    )
}
