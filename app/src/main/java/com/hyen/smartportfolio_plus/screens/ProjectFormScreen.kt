package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hyen.smartportfolio_plus.R
import com.hyen.smartportfolio_plus.components.CommonAppBar
import com.hyen.smartportfolio_plus.data.auth.FirebaseAuthManager
import com.hyen.smartportfolio_plus.data.firestore.FireStoreProjectRepository
import com.hyen.smartportfolio_plus.data.project.Project
import com.hyen.smartportfolio_plus.data.project.ProjectDatabase
import com.hyen.smartportfolio_plus.data.repository.ProjectRepository
import com.hyen.smartportfolio_plus.ui.theme.primary
import com.hyen.smartportfolio_plus.viewmodel.ProjectViewModel
import com.hyen.smartportfolio_plus.viewmodel.ProjectViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProjectFormScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    projectId: Int?, // null이면 등록, 값이 있으면 수정
) {
    val context = LocalContext.current

    // 현재 UID
    val authManager = FirebaseAuthManager(
        context,
        context.getString(R.string.default_web_client_id)
    ).getCurrentUserId().orEmpty()

    val viewModel: ProjectViewModel = viewModel(
        factory = ProjectViewModelFactory(
            roomRepo = ProjectRepository(ProjectDatabase.getDatabase(context).projectDao()),
            cloudRepo = FireStoreProjectRepository()
        )
    )


    val projectList by viewModel.localProject.observeAsState(emptyList())
    val original = projectList.find { it.localId == projectId }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    val isEdit = original != null

    LaunchedEffect(original) {
        if (original != null) {
            title = original.title
            description = original.description
            link = original.detailLink
            imageUrl = original.imageUrl
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CommonAppBar(
                title = if (isEdit) "Edit Project" else "Add Project",
                scaffoldState = scaffoldState,
                scope = scope,
                showBackButton = true,
                onBackClick = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (title.isBlank() || description.isBlank()) return@FloatingActionButton
                    if (isEdit) {
                        // 수정
                        val updated = original!!.copy(
                            title = title,
                            description = description,
                            detailLink = link,
                            imageUrl = imageUrl
                        )
                        viewModel.updateLocal(updated)
                    } else {
                        // 신규 등록
                        val newProject = Project(
                            userId = authManager,
                            title = title,
                            description = description,
                            detailLink = link,
                            imageUrl = imageUrl
                        )
                        viewModel.insertLocal(newProject)
                    }
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("등록되었습니다")
                    }
                    // 저장 후 뒤로 이동
                    navController.popBackStack()
                },
                backgroundColor = primary,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Save",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "등록하기",
                        fontSize = 17.sp,
                        color = Color.White
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {title = it},
                label = { Text("제목") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = description,
                onValueChange = {description = it},
                label = { Text("설명") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = link,
                onValueChange = {link = it},
                label = { Text("프로젝트 링크") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = imageUrl,
                onValueChange = {imageUrl = it},
                label = { Text("이미지 URL") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}