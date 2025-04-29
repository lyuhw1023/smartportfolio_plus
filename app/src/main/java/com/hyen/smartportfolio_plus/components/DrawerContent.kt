package com.hyen.smartportfolio_plus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hyen.smartportfolio_plus.R
import com.hyen.smartportfolio_plus.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    navController: NavController,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()
    val authViewModel: AuthViewModel = viewModel()

    val userId by authViewModel.userIdLiveData.observeAsState()

    val userType = when {
        userId == null -> "비회원"
        userId == "0ThckGgo2xaw6HENVlJHoIokTCx1" -> "관리자"
        else -> "회원"
    }

    Column(modifier = Modifier.background(Color.White)) {

        // Drawer 헤더
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF696969))
        ){
            Text(
                text = userType,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 25.sp,
                    color = Color.White
                ),
                modifier = Modifier
                    .padding(start = 18.dp, bottom = 18.dp)
                    .align(Alignment.BottomStart)
            )
        }

        // Index 섹션
        DrawerSection(title = "Index")
        DrawerMenuItem("Home", R.drawable.navi_home, "home", navController, scope, scaffoldState)
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(16.dp))

        // Portfolio 메뉴 섹션
        DrawerSection(title = "Portfolio")
        DrawerMenuItem("About", R.drawable.navi_about, "about", navController, scope, scaffoldState)
        DrawerMenuItem("Project", R.drawable.navi_project, "project", navController, scope, scaffoldState)
        DrawerMenuItem("More", R.drawable.navi_more, "more", navController, scope, scaffoldState)
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(16.dp))

        // Others 메뉴 섹션
        DrawerSection(title = "Others")
        DrawerMenuItem("Contact", R.drawable.navi_contact, "contact", navController, scope, scaffoldState)
        DrawerMenuItem("Logout", R.drawable.logout, "", navController, scope, scaffoldState,
            overrideOnClick = {
                authViewModel.signOut {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                    scope.launch { scaffoldState.drawerState.close() }
                }
            }
        )

    }
}


// Drawer 메뉴 제목 스타일
@Composable
fun DrawerSection(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.subtitle1.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.Gray
        ),
        modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp)
    )
}

// Drawer 개별 메뉴 항목
@Composable
fun DrawerMenuItem(
    title: String,
    icon: Int,
    route: String,
    navController: androidx.navigation.NavController,
    scope: kotlinx.coroutines.CoroutineScope,
    scaffoldState: ScaffoldState,
    overrideOnClick: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                if (overrideOnClick != null) {
                    overrideOnClick()
                } else {
                    navController.navigate(route)
                    scope.launch { scaffoldState.drawerState.close() }
                }
            }
            .padding(12.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.body1.copy(
                fontSize = 15.sp,
            ),
            color = Color.Black
        )
    }
}