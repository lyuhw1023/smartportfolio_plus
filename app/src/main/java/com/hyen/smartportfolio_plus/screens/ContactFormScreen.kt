package com.hyen.smartportfolio_plus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hyen.smartportfolio_plus.R
import com.hyen.smartportfolio_plus.components.CommonAppBar
import com.hyen.smartportfolio_plus.data.auth.FirebaseAuthManager
import com.hyen.smartportfolio_plus.data.contact.Contact
import com.hyen.smartportfolio_plus.data.contact.ContactDatabase
import com.hyen.smartportfolio_plus.data.firestore.FireStoreContactRepository
import com.hyen.smartportfolio_plus.data.repository.ContactRepository
import com.hyen.smartportfolio_plus.viewmodel.ContactViewModel
import com.hyen.smartportfolio_plus.viewmodel.ContactViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ContactFormScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    contactId: Int?, // null이면 등록, 값이 있으면 수정
) {

    val context = LocalContext.current

    // 현재 UID
    val uid = FirebaseAuthManager(
        context,
        context.getString(R.string.default_web_client_id)
    ).getCurrentUserId().orEmpty()

    val viewModel: ContactViewModel = viewModel(
        factory = ContactViewModelFactory(
            roomRepo = ContactRepository(ContactDatabase.getDatabase(context).contactDao()),
            cloudRepo = FireStoreContactRepository()
        )
    )

    val contactList by viewModel.localContact.observeAsState(emptyList())
    val original = contactList.find { it.localId == contactId }

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
        topBar = {
            CommonAppBar(
                title = if (isEditMode) "Message 수정하기" else "Message 남기기",
                scaffoldState = scaffoldState,
                scope = scope
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isEditMode) {
                        val updated = original!!.copy(name = name, message = message)
                        viewModel.updateLocal(updated)
                    } else {
                        val newContact = Contact(
                            name = name,
                            message = message,
                            userId = uid
                        )
                        viewModel.insertLocal(newContact)
                    }
                    navController.popBackStack()

                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = if (isEditMode) "수정되었습니다." else "등록되었습니다.",
                            duration = SnackbarDuration.Short
                        )
                    }
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