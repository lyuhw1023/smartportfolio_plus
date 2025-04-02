package com.hyen.smartportfolio_plus.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.hyen.smartportfolio_plus.R
import com.hyen.smartportfolio_plus.components.CommonAppBar
import kotlinx.coroutines.CoroutineScope

data class Project(
    val title: String,
    val description: String,
    val imageRes: Int,
    val detailLink: String
)

@Composable
fun ProjectCard(project: Project) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFDCDCDC))
                .padding(16.dp)
        ) {
            // 프로젝트 제목
            Text(
                text = project.title,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // 프로젝트 이미지
            Image(
                painter = painterResource(id = project.imageRes),
                contentDescription = "Project Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            // 프로젝트 설명
            Text(
                text = project.description,
                style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            // 프로젝트 상세보기 링크
            ClickableText(
                text = androidx.compose.ui.text.AnnotatedString("프로젝트 보러가기 >>"),
                style = TextStyle(
                    fontSize = 12.sp,
                    textAlign = TextAlign.End
                ),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(project.detailLink))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun ProjectList(
    projects: List<Project>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(projects) { project ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .height(IntrinsicSize.Min)
            ) {
                // 타임라인
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    // 선
                    Canvas(modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                        .align(Alignment.Center)
                    ) {
                        drawLine(
                            color = Color.Black,
                            start = Offset(x = size.width / 2, y = 0f),
                            end = Offset(x= size.width / 2, y = size.height),
                            strokeWidth = 15f
                        )
                    }

                    // 원
                    Canvas(
                        modifier = Modifier
                            .size(25.dp)
                            .offset(y = 25.dp)
                            .align(Alignment.TopCenter)
                    ) {
                        // 바깥 원
                        drawCircle(color = Color(0xFF6495ED))
                        // 안쪽 원
                        drawCircle(
                            color = Color.White,
                            radius = size.minDimension / 4f)
                    }

                }

                // 프로젝트 카드
                ProjectCard(project = project)
            }
        }
    }
}

@Composable
fun ProjectScreen(
    navController: androidx.navigation.NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    val projects = listOf(
        Project(
            title = "SmartPortfolio",
            description = "kotlin을 사용해 개발한 포트폴리오 관리 어플입니다. 자신의 프로젝트와 개발 역량을 정리하고 손쉽게 공유할 수 있도록 합니다.",
            imageRes = R.drawable.project_smartportfolio,
            detailLink = "https://github.com/lyuhw1023/smart_portfolio"
        ),
        Project(
            title = "Tomorrow",
            description = "사용자가 직접 작가가 되어 창작하고 출판할 수 있도록 지원하는 AI 기반 창작 중심 SNS 플랫폼입니다.",
            imageRes = R.drawable.project_tomorrow,
            detailLink = "https://github.com/lyuhw1023/To.Morrow"
        ),
        Project(
            title = "EyeForYou",
            description = "시각장애인의 쇼핑을 더 편리하고 즐겁게 만들어주는 쇼핑 동반자 AI 카메라 서비스 어플입니다.",
            imageRes = R.drawable.project_eyeforyou,
            detailLink = "https://github.com/lyuhw1023/eyeforyou_plus"
        ),
        Project(
            title = "SilverLife",
            description = "부모님의 실버타운 생활, 식단, 프로그램, 공지사항 등을 확인할 수 있는 실버타운 관리 플랫폼입니다.",
            imageRes = R.drawable.project_silverlife,
            detailLink = "https://github.com/lyuhw1023/silver_life"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonAppBar(
            title = "Project",
            scaffoldState = scaffoldState,
            scope = scope
        )

        ProjectList(projects = projects)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()

            ProjectScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope
            )
        }
    }
}