
package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.hyen.smartportfolio_plus.R
import com.hyen.smartportfolio_plus.ui.theme.primary
import com.hyen.smartportfolio_plus.ui.theme.primaryClick

@Composable
fun LoginScreen (
    onGuest: () -> Unit,
    onLogin: () -> Unit
    ) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )
        Text(
            "Smart Portfolio",
            style = MaterialTheme.typography.headlineLarge
            )
        Spacer(Modifier.height(40.dp))
        Button(
            onClick = onGuest,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                contentColor = Color.White,
                disabledContainerColor = primaryClick,
                disabledContentColor = Color.White
            )
        ) {
            Text(text = "둘러보기")
        }
        Spacer(Modifier.height(10.dp))
        Button(
            onClick = onLogin,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                contentColor = Color.White,
                disabledContainerColor = primaryClick,
                disabledContentColor = Color.White
            )
        ) {
            Text(text = "Google 로그인")
        }
    }
}
