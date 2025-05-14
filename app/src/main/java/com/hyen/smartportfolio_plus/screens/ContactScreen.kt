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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hyen.smartportfolio_plus.R
import com.hyen.smartportfolio_plus.components.CommonAppBar
import com.hyen.smartportfolio_plus.data.auth.FirebaseAuthManager
import com.hyen.smartportfolio_plus.data.auth.UserType
import com.hyen.smartportfolio_plus.data.contact.Contact
import com.hyen.smartportfolio_plus.data.contact.ContactDatabase
import com.hyen.smartportfolio_plus.data.firestore.FireStoreContactRepository
import com.hyen.smartportfolio_plus.data.repository.ContactRepository
import com.hyen.smartportfolio_plus.viewmodel.ContactViewModel
import com.hyen.smartportfolio_plus.viewmodel.ContactViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ContactScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    userType: UserType
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

    var showDialog by remember { mutableStateOf(true) }

    // 화면에 처음 들어올 때 한 번만 실행
    LaunchedEffect(Unit) {
        if (userType == UserType.GUEST) {
            showDialog = true
        }
    }

    // GUEST일 경우 로그인 하도록
    if (showDialog && userType == UserType.GUEST) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = { Text("로그인 필요")},
            text = { Text("로그인 후 이용 가능합니다. 로그인하시겠습니까?")},
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = false }
                    }
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.popBackStack()
                }) {
                    Text("취소")
                }
            }
        )
        return
    }

    // MEMBER: 자신이 작성한 것만, ADMIN: 전체
    val contactList by viewModel.localContact.observeAsState(emptyList())
    val displayList = remember(contactList, userType) {
        if (userType == UserType.ADMIN) contactList
        else contactList.filter { it.userId == uid }
    }

    Scaffold(
        topBar = {
            CommonAppBar(
                title = "ContactMe",
                scaffoldState = scaffoldState,
                scope = scope
            )
        },
        floatingActionButton = {
            if (userType == UserType.MEMBER) {
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
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(displayList, key = { it.localId}) { contact ->
                ContactCard(
                    contact = contact,
                    onEdit = {
                        if (contact.userId == uid) {
                            navController.navigate("contactForm/${contact.localId}")
                        }
                    },
                    onDelete = {
                        if (contact.userId == uid) {
                            viewModel.deleteLocal(contact)
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("삭제되었습니다.")
                            }
                        }
                    },
                    userType = userType
                )
                Divider()
            }
        }
    }
}

@Composable
fun ContactCard(
    contact: Contact,
    onEdit: (Contact) -> Unit,
    onDelete: (Contact) -> Unit,
    userType: UserType
) {
    var showDialog by remember {mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = contact.name, style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = contact.message, style = MaterialTheme.typography.body2)
            }
            // MEMBER인 경우에만 수정, 삭제 버튼 노출
            if (userType == UserType.MEMBER) {
                IconButton(onClick = {onEdit(contact)}){
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = {showDialog = true}){
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }

    // 삭제 시, 뜨는 확인창
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {showDialog = false},
            title = {Text("삭제 확인")},
            text = {Text("이 메시지를 삭제하시겠습니까?")},
            confirmButton = {
                TextButton(onClick = {
                    onDelete(contact)
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