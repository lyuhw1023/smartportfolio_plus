package com.hyen.smartportfolio_plus.data.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseAuthManager(context: Context, clientId: String) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()

        // 로그인 클라이언트 생성
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    // 로그인 인텐트 반환
    fun getSignInIntent(): Intent = googleSignInClient.signInIntent

    // 로그인 결과 처리 및 firebase 인증
    fun handleSignInResult(
        account: GoogleSignInAccount?,
        onSuccess: (userId: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        if (account == null) {
            onFailure("Google 로그인 계정 정보가 없습니다.")
            return
        }

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    auth.currentUser?.uid?.let(onSuccess)
                        ?: onFailure("사용자 정보를 가져올 수 없습니다.")
                } else {
                    onFailure(task.exception?.message ?: "인증 오류가 발생했습니다.")
                }
            }
    }

    // 로그아웃
    fun signOut(onComplete: () -> Unit) {
        googleSignInClient.signOut().addOnCompleteListener {
            auth.signOut()
            onComplete()
        }
    }

    // 로그인 상태 확인
    fun isLoggedIn(): Boolean = auth.currentUser != null
}
