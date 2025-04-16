package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.ScaffoldState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.EmojiEvents


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
            backgroundColor = Color.White
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
    val awards = mapOf(
        "2024" to listOf(
            "한림 오픈소스 SW 해커톤 우수상",
            "SW Week GitHub 이력서 콘테스트 해커톤 은상",
            "씨애랑 SW 전시회 인기상",
            "SW 캡스톤 디자인 - 팀 내 2등 수상"
        ),
        "2023" to listOf(
            "한림모여코딩 프로그램 우수활동 팀 선정"
        ),
        "2022" to listOf(
            "정보과학대학 서공제 아이디어 부문 장려상"
        )
    )
    val participations = mapOf(
        "2024" to listOf(
            "프라이머 제2회 GenAI 해커톤 참여",
            "정보과학대학 서공제 생성형 AI 활용 : 마스코트 만들기 부문 본선 진출",
            "정보과학대학 서공제 완성작 부문 본선 진출"
        )
    )
    val mentorings = mapOf(
        "2024" to listOf(
            "2학기 SW전공 멘토링(창의코딩 - 모두의 웹) 진행"
        ),
        "2022" to listOf(
            "1학기 SW교과목 멘토링(컴퓨팅사고 AI기초) 진행",
            "1학기 상생러닝디딤돌 멘토링 진행",
            "1학기 SW전공 멘토링(파이썬) 진행",
            "2학기 SW전공 멘토링(창의코딩웹) 진행"
        )
    )
    val educations = mapOf(
        "2021" to listOf(
            "codeit 대학생 코딩 캠프 7기 수료",
            "1학기 전공 멘토링(C언어)",
            "2학기 전공 멘토링(파이썬)",
        ),
        "2020" to listOf(
            "1학기 신입생 몰입형 SW코딩캠프",
            "2학기 인공지능 교육 특강 수료",
            "2학기 전공 멘토링(자바2)"
        )
    )
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 수상 섹션
        item { SectionHeader("\uD83C\uDFC6 수상") }
        awards.forEach{ (year, items) ->
            item {
                Text(
                    text = year,
                    style = MaterialTheme.typography.body2.copy(color = Color.Gray)
                )
            }
            items(items) { item ->
                MoreCard("- $item")
            }
        }
        // 해커톤 및 대회 섹션
        item { SectionHeader("\uD83D\uDCA1 해커톤 & 대회 참여") }
        participations.forEach{ (year, items) ->
            item {
                Text(
                    text = year,
                    style = MaterialTheme.typography.body2.copy(color = Color.Gray),
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }
            items(items) { item ->
                MoreCard("- $item")
            }
        }
        // 멘토링 및 동아리 섹션
        item { SectionHeader("\uD83D\uDC65 멘토링") }
        mentorings.forEach{ (year, items) ->
            item {
                Text(
                    text = year,
                    style = MaterialTheme.typography.body2.copy(color = Color.Gray),
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }
            items(items) { item ->
                MoreCard("- $item")
            }
        }
        // 캠프 및 수료 섹션
        item { SectionHeader("\uD83D\uDCDA 캠프 & 수료") }
        educations.forEach{ (year, items) ->
            item {
                Text(
                    text = year,
                    style = MaterialTheme.typography.body2.copy(color = Color.Gray),
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }
            items(items) { item ->
                MoreCard("- $item")
            }
        }
    }
}

@Composable
fun CareerList() {
    val study = mapOf(
        "2024" to listOf(
            "교내 정보과학대학 학술동아리 씨애랑 라떼팀",
        ),
        "2022" to listOf(
            "정보과학대학 학술동아리 노네임 회장",
        ),
        "2021" to listOf(
            "창업동아리 'TAG' 활동",
            "창업동아리 '트라움' 활동",
        ),
        "2020" to listOf(
            "소프트웨어융합대학 학술동아리 노네임 활동"
        )
    )
    val work = mapOf(
        "2024" to listOf(
            "제 3대 정보과학대학 학생회 'Ready' 사무국 국장",
            "중앙선거관리위원회 운영국"
        ),
        "2022" to listOf(
            "제 1대 정보과학대학 학생회 'A:BLE' 기획국 국장",
            "한림대학교 대동제 '그,_림' 축제준비위원회 밤부스팀"
        ),
        "2021" to listOf(
            "제 3대 소프트웨어융합대학 학생회 'WUSM' 체육국 부장"
        ),
        "2020" to listOf(
            "제 2대 소프트웨어융합대학 학생회 'STEP' 체육국 부원"
        )
    )
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 학술 관련
        item { SectionHeader("\uD83D\uDCDA 학술 동아리") }
        study.forEach{ (year, items) ->
            item {
                Text(
                    text = year,
                    style = MaterialTheme.typography.body2.copy(color = Color.Gray)
                )
            }
            items(items) { item ->
                MoreCard("- $item")
            }
        }
        // 해커톤 및 대회 섹션
        item { SectionHeader("\uD83D\uDCE3 학생회 & 행사 운영") }
        work.forEach{ (year, items) ->
            item {
                Text(
                    text = year,
                    style = MaterialTheme.typography.body2.copy(color = Color.Gray),
                )
            }
            items(items) { item ->
                MoreCard("- $item")
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(top = 40.dp)
    )
}

@Composable
fun MoreCard(text: String){
    Card(
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}

/*

@Composable
fun CareerList() {
    val careers = listOf(
        "\uD83D\uDCA1 2024년도 교내 정보과학대학 학술동아리 씨애랑 라떼팀",
        "\uD83D\uDCE2 2024년도 제 3대 정보과학대학 학생회 'Ready' 사무국 국장",
        "\uD83D\uDCE2 2024년도 중앙선거관리위원회 운영국",
        "\uD83D\uDCA1 2022년도 정보과학대학 학술동아리 노네임 회장",
        "\uD83D\uDCE2 2022년도 제 1대 정보과학대학 학생회 'A:BLE' 기획국 국장",
        "\uD83D\uDCE2 2022년도 한림대학교 대동제 '그,_림' 축제준비위원회 밤부스팀",
        "\uD83D\uDCA1 2021년도 창업동아리 'TAG' 활동",
        "\uD83D\uDCA1 2021년도 창업동아리 '트라움' 활동",
        "\uD83D\uDCE2 2021년도 제 3대 소프트웨어융합대학 학생회 'WUSM' 체육국 부장",
        "\uD83D\uDCA1 2020년도 교내 소프트웨어융합대학 학술동아리 노네임 활동",
        "\uD83D\uDCE2 2020년도 제 2대 소프트웨어융합대학 학생회 'STEP' 체육국 부원"
    )
    LazyColumn(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(careers) { career ->
            Card(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = career,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
*/
