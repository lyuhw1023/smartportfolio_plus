package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hyen.smartportfolio_plus.components.CommonAppBar
import kotlinx.coroutines.CoroutineScope

data class ContactMessage(val name: String, val message: String)

@Composable
fun ContactScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
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
                onClick = { navController.navigate("addContactMessage") },
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
            items(
                items = listOf(
                    ContactMessage("A", "hello"),
                    ContactMessage("B", "hello hi"),
                    ContactMessage("C", "hello nice"),
                ),
                key = { it.name }
            ) { msg ->
                ContactMessageItem(msg)
                Divider()
            }
        }
    }
}

@Composable
fun ContactMessageItem(msg: ContactMessage) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = msg.name, style = MaterialTheme.typography.subtitle1)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = msg.message, style = MaterialTheme.typography.body2)
    }
}