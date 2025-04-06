package com.hyen.smartportfolio_plus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.navigation.compose.rememberNavController
import com.hyen.smartportfolio_plus.navigation.NavGraph
import kotlinx.coroutines.launch
import com.hyen.smartportfolio_plus.components.DrawerContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawerNavigationApp()
        }
    }
}

// 네비게이션
@Composable
fun DrawerNavigationApp() {
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
            scope = scope
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

// 로그인 관련 코드 - 나중에 참고
/*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import navigation.NavGraph
import viewmodel.AuthViewModel
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 전역 예외 핸들러 추가 - 앱이 갑자기 종료될 때 로그 확인 가능
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            Log.e("GlobalException", "Uncaught Exception: ${exception.localizedMessage}", exception)
            exitProcess(2)
        }

        Log.d("MainActivity", "앱 실행 시작 - onCreate 호출됨")

        authViewModel.initGoogleClient(getString(R.string.default_web_client_id))
        Log.d("MainActivity", "Google 클라이언트 초기화 완료")

        // Google 로그인 결과 처리
        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            Log.d("MainActivity", "Google Sign-In 결과 호출됨")
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    Log.d("MainActivity", "Google Sign-In 성공: ${account.displayName}")
                    authViewModel.getAuthManager().handleGoogleSignInResult(
                        account,
                        onSuccess = { userName -> Log.d("MainActivity", "로그인 성공: $userName") },
                        onFailure = { errorMessage ->
                            Log.e("MainActivity", "로그인 실패: $errorMessage")
                        }
                    )
                } else {
                    Log.e("MainActivity", "Google Sign-In 실패: 계정 정보가 null입니다.")
                }
            } catch (e: ApiException) {
                Log.e("MainActivity", "Google Sign-In 오류 발생: ${e.message}", e)
            } catch (e: Exception) {
                Log.e("MainActivity", "예상치 못한 오류 발생: ${e.message}", e)
            }
        }

        setContent {
            Log.d("MainActivity", "Compose UI 설정 시작")
            SmartProtfolioApp()
            Log.d("MainActivity", "Compose UI 설정 완료")
        }
    }

    @Composable
    fun SmartProtfolioApp() {
        val navController = rememberNavController()
        NavGraph(
            navController = navController,
            authViewModel = authViewModel,
            googleSignInLauncher = googleSignInLauncher
        )
    }
}
*/
