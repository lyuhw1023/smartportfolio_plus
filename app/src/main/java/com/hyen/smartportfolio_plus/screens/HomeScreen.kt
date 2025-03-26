package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.hyen.smartportfolio_plus.components.CommonAppBar

@Composable
fun HomeScreen(
    onHomeClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonAppBar(
                title = "SmartPortfolio",
                onHomeClick = onHomeClick,
                onLogoutClick = onLogoutClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            MenuCard(icon = R.drawable.about, title = "About", onClick = {
                // About 화면으로 이동
            })
            MenuCard(icon = R.drawable.project, title = "Project", onClick = {
                // Project 화면으로 이동
            })
            MenuCard(icon = R.drawable.contact, title = "Contact", onClick = {
                // Contact 화면으로 이동
            })
            MenuCard(icon = R.drawable.more, title = "More", onClick = {
                // More 화면으로 이동
            })
        }
    }
}

@Composable
fun MenuCard(icon: Int, title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 6.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "$title Icon",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onHomeClick = {},
        onLogoutClick = {}
    )
}
