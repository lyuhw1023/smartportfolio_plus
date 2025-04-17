package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyen.smartportfolio_plus.R
import com.hyen.smartportfolio_plus.components.CommonAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun AboutScreen(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    Scaffold(
        topBar = {
            CommonAppBar(
                title = "About",
                scaffoldState = scaffoldState,
                scope = scope
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header 영역
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile), // 프로필 사진
                    contentDescription = "프로필 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(150.dp)
                        .padding(end = 16.dp)
                )
                Column {
                    Text("유혜원 | Mobile Developer", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("완성도를 높이는 개발자, 유혜원입니다.")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 2. Contact
            Text("Contact", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFB8C00))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Email : lyuhw11023@gmail.com")
            Text("Phone : 010-2786-0570")
            Text("GitHub : https://github.com/lyuhw1023")

            Spacer(modifier = Modifier.height(40.dp))

            // 3. Introduce
            Text("Introduce", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFB8C00))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Flutter 및 Kotlin 기반의 모바일 애플리케이션을 개발하며 UI/UX 최적화, 데이터 흐름 설계, API 연동 등의 경험을 쌓아왔습니다.\n\n" +
                        "REST API 및 Fast API와의 연동을 통해 데이터를 효과적으로 처리하고, AI 모델 연동 및 비동기 처리를 최적화하며 유지보수성을 고려한 코드 작성에 집중했습니다.\n\n" +
                        "Git을 활용한 코드 협업 경험이 있으며, 팀원들과 코드 리뷰 및 PR을 주고받으며 협업 능력을 키워왔습니다.\n\n" +
                        "끊임없는 학습과 개선을 통해 더 높은 완성도를 갖춘 Mobile 개발자로 성장하겠습니다.",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 4. Skills
            Text("Skills", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFB8C00))
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text("• Frontend: Flutter, Kotlin")
                Text("• API & Backend: REST API, FastAPI")
                Text("• Database: Firebase, SQLite, MySQL")
                Text("• State Management: GetX, Provider, MVVM 아키텍처 적용 경험")
                Text("• Version Control: Git, GitHub (Pull Request, Issue Tracking 경험)")
                Text("• Design: Figma 기반 UI 분석 및 구현")
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 5. Education
            Text("Education", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFB8C00))
            Spacer(modifier = Modifier.height(8.dp))
            Text("• 2020.03 ~ 2025.02  한림대학교 정보과학대학 빅데이터전공")

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
