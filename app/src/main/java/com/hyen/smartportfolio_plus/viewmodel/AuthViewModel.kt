package com.hyen.smartportfolio_plus.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.hyen.smartportfolio_plus.data.auth.FirebaseAuthManager
import com.hyen.smartportfolio_plus.R
import com.hyen.smartportfolio_plus.data.auth.UserType

// 로그인 상태 & 결과 관리
class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val clientId = getApplication<Application>()
        .getString(R.string.default_web_client_id)
    private val authManager = FirebaseAuthManager(app.applicationContext, clientId)

    // firebase에서 받은 UID
    val userIdLiveData = MutableLiveData<String?>().apply {
        value = authManager.getCurrentUserId()
    }

    val errorLiveData = MutableLiveData<String?>()

    val userTypeLiveData = MediatorLiveData<UserType>().apply {
        value = computeType(userIdLiveData.value)
        addSource(userIdLiveData) { uid ->
            value = computeType(uid)
        }
    }

    // UID & UserType 매핑
    private fun computeType(uid: String?): UserType {
        return when {
            uid == null -> UserType.GUEST
            uid == "0ThckGgo2xaw6HENVlJHoIokTCx1" -> UserType.ADMIN
            else -> UserType.MEMBER
        }
    }

    fun getSignInIntent() = authManager.getSignInIntent()

    // 로그인 결과 처리
    fun handleSignIn(account: com.google.android.gms.auth.api.signin.GoogleSignInAccount?) {
        authManager.handleSignInResult( account,
            onSuccess = { uid: String -> userIdLiveData.postValue(uid) },
            onFailure = { msg: String -> errorLiveData.postValue(msg) }
        )
    }

    // 로그아웃
    fun signOut(onComplete: () -> Unit) {
        authManager.signOut {
            userIdLiveData.postValue(null)
            onComplete()
        }
    }

    // 현재 로그인 상태 확인
    fun isLoggedIn(): Boolean = authManager.isLoggedIn()
}
