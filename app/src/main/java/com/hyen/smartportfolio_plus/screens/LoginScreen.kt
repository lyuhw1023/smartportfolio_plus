
package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen (
    onClickSignIn: () -> Unit
    ) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClickSignIn,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Goggle 로그인")
        }
    }
}
