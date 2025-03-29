package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyen.smartportfolio_plus.R
import com.hyen.smartportfolio_plus.components.CommonAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    paddingValues: PaddingValues
) {
    Scaffold(
        topBar = {
            CommonAppBar(
                title = "SmartPortfolio",
                scaffoldState = scaffoldState,
                scope = scope
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                MenuCard(
                    icon = R.drawable.about,
                    title = "앱 소개",
                    description = "- 2022년도 (주)더존비즈온 & 한림대학교 모바일 개발자 과정 프로젝트 본선 진출작입니다. \n\n- kotlin 기반 포트폴리오 앱으로 화면 간 이동, 데이터 전달, 리스트 구성, 웹 이동 등의 기능이 있습니다.\n\n- 기존 프로젝트를 리팩토링함으로써 더욱 업그레이드 된 SmartPortfolio를 확인하실 수 있습니다."
                )
            }
            item {
                MenuCard(
                    icon = R.drawable.project,
                    title = "기능 소개",
                    description = "- About: 개인 소개 및 스킬 정보.\n\n- Project: 프로젝트 목록과 세부 내용.\n\n- Contact: 연락 정보 및 ~ 제공.\n\n- More: 기타 활동(대회,동아리 등)"
                )
            }
            item {
                MenuCard(
                    icon = R.drawable.more,
                    title = "버전 정보 & 사용 기술",
                    description = "- androidstudio, jdk,버전들\n\n- 사용 기술: Kotlin, Jetpack Compose, Firebase, MVVM 패턴 적용"
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "앱 아이콘",
                    modifier = Modifier.size(40.dp)
                )

                Text(
                    text = "SmartPortfolio 앱을 사용해 주셔서 감사합니다.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0))
                        .padding(16.dp)
                ){
                    Column {
                        Text(
                            text = "개발자 정보",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "이름: 유혜원",style = TextStyle(fontSize = 11.sp, color = Color.DarkGray))
                        Text(text = "이메일: lyuhw11023@gmail.com",style = TextStyle(fontSize = 11.sp, color = Color.DarkGray))
                        Text(text = "깃허브: https://github.com/lyuhw1023",style = TextStyle(fontSize = 11.sp, color = Color.DarkGray))
                    }
                }
            }
        }
    }
}

@Composable
fun MenuCard(icon: Int, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        elevation = 6.dp,
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ){
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "$title Icon",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6495ED)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = description,
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        scaffoldState = rememberScaffoldState(),
        scope = kotlinx.coroutines.MainScope(),
        paddingValues = PaddingValues(0.dp)
    )
}
