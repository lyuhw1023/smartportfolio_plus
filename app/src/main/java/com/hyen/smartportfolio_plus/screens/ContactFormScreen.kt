package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hyen.smartportfolio_plus.components.CommonAppBar
import com.hyen.smartportfolio_plus.data.contact.Contact
import com.hyen.smartportfolio_plus.viewmodel.ContactViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun ContactFormScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    contactId: Int?, // null이면 등록, 값이 있으면 수정
    viewModel: ContactViewModel = viewModel()
) {
    val contactList by viewModel.allContacts.observeAsState(emptyList())
    val original = contactList.find { it.id == contactId }

    var name by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val isEditMode = original != null

    LaunchedEffect(original) {
        if (original != null) {
            name = original.name
            message = original.message
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CommonAppBar(
                title = if (isEditMode) "Edit Message" else "Add Message",
                scaffoldState = scaffoldState,
                scope = scope
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isEditMode) {
                        val updated = original!!.copy(name = name, message = message)
                        viewModel.update(updated)
                    } else {
                        val newContact = Contact(
                            name = name,
                            message = message,
                            userId = "tempUser"
                        )
                        viewModel.insert(newContact)
                    }
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