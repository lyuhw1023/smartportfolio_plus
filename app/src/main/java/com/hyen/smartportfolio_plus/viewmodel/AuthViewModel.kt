package com.hyen.smartportfolio_plus.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hyen.smartportfolio_plus.data.auth.FirebaseAuthManager
import com.hyen.smartportfolio_plus.R

// 로그인 상태 & 결과 관리
class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val clientId = getApplication<Application>()
        .getString(R.string.default_web_client_id)
    private val authManager = FirebaseAuthManager(app.applicationContext, clientId)

    val userIdLiveData = MutableLiveData<String?>().apply {
        value = authManager.getCurrentUserId()
    }

    val errorLiveData = MutableLiveData<String?>()

    fun getSignInIntent() = authManager.getSignInIntent()

    // 로그인 결과 처리
    fun handleSignIn(account: com.google.android.gms.auth.api.signin.GoogleSignInAccount?) {
        authManager.handleSignInResult( account,
            onSuccess = { uid: String -> userIdLiveData.postValue(uid) },
            onFailure = { msg: String -> errorLiveData.postValue(msg) }
        )
    }

    // 로그아웃
    fun signOut() {
        authManager.signOut() {
            userIdLiveData.postValue(null)
        }
    }

    // 현재 로그인 상태 확인
    fun isLoggedIn(): Boolean = authManager.isLoggedIn()
}
