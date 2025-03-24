package com.hyen.smartportfolio_plus

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hyen.smartportfolio_plus.ui.theme.SmartportfolioPlusTheme
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Firebase 인증 인스턴스 초기화
        auth = FirebaseAuth.getInstance()

        // Google 로그인 옵션 설정
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Google 로그인 클라이언트 생성
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 앱 시작 시 로그인 상태 확인
        val currentUsesr = auth.currentUser
        if (currentUsesr != null) {
            println("이미 로그인된 사용자: ${currentUsesr.displayName}")
            setContent {
                SmartportfolioPlusTheme {
                    Surface {
                        UserInfoScreen(
                            userName = currentUsesr.displayName ?: "Unknown",
                            userEmail = currentUsesr.email ?: "Unknown"
                        )
                    }
                }
            }
        } else {
            println("로그인되지 않음")
            setContent{
                SmartportfolioPlusTheme {
                    Surface {
                        GoogleLoginButton()
                    }
                }
            }
        }
    }

    // 로그인 버튼 클릭 시 호출되는 함수
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // 로그인 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.result
            if (account != null) {
                firebaseAuthWithGoogle(account)
            }
        }
    }

    // firebase 인증 및 연결
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 로그인 성공
                    val user = auth.currentUser
                    println("로그인 성공: ${user?.displayName}")

                    // 로그인 성공 시 사용자 정보 화면에 표시
                    setContent {
                        SmartportfolioPlusTheme {
                            Surface {
                                UserInfoScreen(
                                    userName = user?.displayName ?: "Unknown",
                                    userEmail = user?.email ?: "Unknown"
                                )
                            }
                        }
                    }
                } else {
                    // 로그인 실패
                    println("로그인 실패: ${task.exception?.message}")
                }
            }
    }

    // 로그아웃
    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this) {
                auth.signOut()
                println("로그아웃 완료")

                // 로그아웃 후 로그인 화면으로 전환
                setContent {
                    SmartportfolioPlusTheme {
                        Surface {
                            GoogleLoginButton()
                        }
                    }
                }
            }
    }

    @Composable
    fun GoogleLoginButton() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { signIn() },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Google 로그인")
            }
        }
    }

    // 사용자 정보
    @Composable
    fun UserInfoScreen(userName: String, userEmail: String){
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = "환영합니다, $userName 님", style = MaterialTheme.typography.headlineMedium)
            Text(text = "이메일: $userEmail", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {signOut()},
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "로그아웃")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        SmartportfolioPlusTheme {
            GoogleLoginButton()
        }
    }
}