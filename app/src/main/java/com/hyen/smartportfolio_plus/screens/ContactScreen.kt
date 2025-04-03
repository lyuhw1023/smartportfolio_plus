package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hyen.smartportfolio_plus.components.CommonAppBar
import com.hyen.smartportfolio_plus.data.contact.Contact
import com.hyen.smartportfolio_plus.viewmodel.ContactViewModel
import kotlinx.coroutines.CoroutineScope

//data class ContactMessage(val name: String, val message: String)

@Composable
fun ContactScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    viewModel: ContactViewModel = viewModel()
) {
    // DB에서 불러온 메시지 목록
    val contactList by viewModel.allContacts.observeAsState(emptyList())

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CommonAppBar(
                title = "ContactMe",
                scaffoldState = scaffoldState,
                scope = scope
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("contactForm") },
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add Message")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(contactList, key = { it.id}) { msg ->
                ContactMessageItem(
                    msg = msg,
                    onEdit = {
                        navController.navigate("contactForm/${msg.id}")
                    },
                    onDelete = {
                        // 삭제기능
                    }
                )
                Divider()
            }
        }
    }
}

@Composable
fun ContactMessageItem(
    msg: Contact,
    onEdit: (Contact) -> Unit,
    onDelete: (Contact) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = msg.name, style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = msg.message, style = MaterialTheme.typography.body2)
            }
            IconButton(onClick = {onEdit(msg)}){
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = {onDelete(msg)}){
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }

        }
    }
}