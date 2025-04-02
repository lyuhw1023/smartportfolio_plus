package com.hyen.smartportfolio_plus.screens

import androidx.compose.material.ScaffoldState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hyen.smartportfolio_plus.components.CommonAppBar
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items

@Composable
fun MoreScreen(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
) {
    val tabTitles = listOf("ACTIVITIES", "CAREER")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize())
    {
        CommonAppBar(
            title = "More",
            scaffoldState = scaffoldState,
            scope = scope
        )

        // 탭 UI
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colors.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(title) }
                )
            }
        }

        // 탭 페이지 전환
        HorizontalPager(
            count = tabTitles.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> ActivityList()
                1 -> CareerList()
            }
        }
    }
}

@Composable
fun ActivityList() {
    val activities = listOf(
        "2020년도 1학기 신입생 몰입형 SW코딩캠프",
        "2020년도 2학기 인공지능 교육 특강 수료",
        "2020년도 2학기 전공 멘토링(자바2)",
        "2021년도 1학기 전공 멘토링(C언어)",
        "codeit 대학생 코딩 캠프 7기 수료",
        "2022년도 정보과학대학 학술동아리 노네임 회장",
        "2024년도 SW Week GitHub 이력서 콘테스트 해커톤 은상",
        "2024년도 씨애랑 SW 전시회 인기상"
    )
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(activities) { activity ->
            Text(
                text = "- $activity",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}


@Composable
fun CareerList() {
    val careers = listOf(
        "2020년도 제 2대 소프트웨어융합대학 학생회 'STEP' 체육국 부원",
        "2021년도 창업동아리 'TAG' 활동",
        "2022년도 제 1대 정보과학대학 학생회 'A:BLE' 기획국 국장",
        "2022년도 정보과학대학 학술동아리 노네임 회장",
        "2024년도 중앙선거관리위원회 운영국"
    )
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(careers) { career ->
            Text(
                text = "- $career",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}
