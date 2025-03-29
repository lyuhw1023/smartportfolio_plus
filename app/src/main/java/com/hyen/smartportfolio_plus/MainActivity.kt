package com.hyen.smartportfolio_plus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hyen.smartportfolio_plus.screens.HomeScreen
import com.hyen.smartportfolio_plus.ui.theme.SmartportfolioPlusTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawerNavigationApp()
        }
    }
}

@Composable
fun DrawerNavigationApp() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent()
        }
    ) { paddingValues ->
        HomeScreen(
            onLogoutClick = {},
            onMenuClick = { scope.launch { scaffoldState.drawerState.open() } },
            paddingValues = paddingValues
        )
    }
}

@Composable
fun DrawerContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "About", modifier = Modifier.padding(8.dp))
        Text(text = "Project", modifier = Modifier.padding(8.dp))
        Text(text = "", modifier = Modifier.padding(8.dp))
        Text(text = "About", modifier = Modifier.padding(8.dp))
    }
}


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
