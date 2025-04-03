package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hyen.smartportfolio_plus.components.CommonAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun AddContactMessageScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    var name by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CommonAppBar(
                title = "Add Message",
                scaffoldState = scaffoldState,
                scope = scope
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // 저장 후 뒤로 이동
                    navController.popBackStack()
                },
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save")
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
                value = name,
                onValueChange = {name = it},
                label = { Text("이름 or 기업명") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = message,
                onValueChange = {message = it},
                label = { Text("내용") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
    }
}