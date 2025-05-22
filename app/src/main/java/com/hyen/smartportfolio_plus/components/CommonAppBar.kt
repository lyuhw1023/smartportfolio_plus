package com.hyen.smartportfolio_plus.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyen.smartportfolio_plus.R
import com.hyen.smartportfolio_plus.ui.theme.primary
import com.hyen.smartportfolio_plus.ui.theme.primaryClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CommonAppBar(
    title: String,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
) {
    Surface(
        color = primary,
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            IconButton(
                onClick = {
                    if (showBackButton) {
                        onBackClick?.invoke()
                    } else {
                        scope.launch { scaffoldState.drawerState.open() }
                    }
                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = if (showBackButton) {
                        Icons.Default.ArrowBack
                    } else {
                        Icons.Filled.Menu
                    },
                    contentDescription = if (showBackButton) "Back" else "Menu",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommonAppBarPreview() {
    CommonAppBar(
        title = "SmartPortfolio",
        scaffoldState = rememberScaffoldState(),
        scope = kotlinx.coroutines.MainScope()
    )
}