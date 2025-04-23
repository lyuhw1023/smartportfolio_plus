package com.hyen.smartportfolio_plus.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hyen.smartportfolio_plus.components.CommonAppBar
import com.hyen.smartportfolio_plus.viewmodel.ProjectViewModel
import kotlinx.coroutines.CoroutineScope
import com.hyen.smartportfolio_plus.data.project.Project

@Composable
fun ProjectCard(
    project: Project,
    onEdit: (Project) -> Unit,
    onDelete: (Project) -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = project.title,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { onEdit(project) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // 프로젝트 이미지
            AsyncImage(
                model = project.imageUrl,
                contentDescription = "Project Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
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
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("삭제 확인") },
            text = { Text("이 프로젝트를 삭제하시겠습니까?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(project)
                    showDialog = false
                }) {
                    Text("삭제", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("취소", color = Color.Black)
                }
            }
        )
    }
}

@Composable
fun ProjectList(
    projects: List<Project>,
    onEdit: (Project) -> Unit,
    onDelete: (Project) -> Unit,
    modifier: Modifier = Modifier
) {
    if (projects.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "등록된 프로젝트가 없습니다.",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            // 선 고정
            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
                    .offset(x = 31.dp)
            ) {
                drawLine(
                    color = Color.Black,
                    start = Offset(x = size.width / 2, y = 0f),
                    end = Offset(x = size.width / 2, y = size.height),
                    strokeWidth = 12f
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
            ) {
                itemsIndexed(projects) { index, project ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(bottom = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .size(25.dp)
                                    .offset(y = 25.dp)
                            ) {
                                // 바깥 원
                                drawCircle(color = Color(0xFF6495ED))
                                // 안쪽 원
                                drawCircle(
                                    color = Color.White,
                                    radius = size.minDimension / 4f
                                )
                            }
                        }
                        // 프로젝트
                        ProjectCard(
                            project = project,
                            onEdit = onEdit,
                            onDelete = onDelete
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    viewModel: ProjectViewModel = viewModel()
) {
    val projectList by viewModel.allProjects.observeAsState(emptyList())

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CommonAppBar(
                title = "Project",
                scaffoldState = scaffoldState,
                scope = scope
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("projectForm") },
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add Project")
                }
            }
        }
    ) { padding ->
        ProjectList(
            projects = projectList,
            onEdit = { project ->
                navController.navigate("projectForm/${project.id}")
            },
            onDelete = { project ->
                viewModel.delete(project)
            },
            modifier = Modifier.padding(padding)
        )
    }
}