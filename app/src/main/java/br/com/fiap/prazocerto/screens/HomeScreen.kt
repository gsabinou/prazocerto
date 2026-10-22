package br.com.fiap.prazocerto.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.prazocerto.ui.theme.PrazoCertoTheme
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.prazocerto.BuildConfig
import br.com.fiap.prazocerto.model.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    // Obter uma instancia do Firebase
    val loggedUser = FirebaseAuth.getInstance()

    // Necessário para deslogar da conta do Google
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.DEFAULT_WEB_CLIENT_ID)
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    var  showDialog by remember { mutableStateOf(false) }

    var showDialogExclude by remember { mutableStateOf(false) }

    var activityIdToExclude by remember { mutableStateOf("") }

    var showDialogEdit by remember { mutableStateOf(false) }

    var editActivity by remember {
        mutableStateOf(Activity())
    }

    // variável que carregará a lista de atividades obtidas no firebase
    val activities = remember { mutableStateListOf<Activity>() }


    // criando uma isntância do Realtime Database
    val database = Firebase.database(
        BuildConfig.DB_CONNECTION_URL
    )

    // obtendo uma ref do nó "activities" e carregando os seus children
    // Buscamos os dados do Firebase
    // e preenchemos a lista de atividades
    database.getReference("activities").get()
        .addOnSuccessListener { dataSnapshot ->
            activities.clear()
            for (child in dataSnapshot.children) {
                val atividade = child.getValue(Activity::class.java)
                activities.add(atividade!!)
            }
        }
        .addOnFailureListener {
            println("Erro ao buscar dados: ${it.message}")
        }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Prazo Certo",
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            Text(
                                text = "Olá, ${loggedUser.currentUser?.displayName}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 14.sp
                            )
                        }
                        IconButton(
                            onClick = {
                                val authenticate = FirebaseAuth.getInstance()
                                authenticate.signOut()

                                // logout do Google
                                googleSignInClient.signOut()
                                    .addOnCompleteListener {
                                        navController.navigate("login")
                                    }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Efetuar Logout",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults
                    .topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                shape = RoundedCornerShape(50.dp),
                modifier = modifier
                    .width(100.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(
                text = "Atividades",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            // Exibindo a lista de atividades
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 48.dp)
            ) {
                items(activities) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = it.discipline,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = it.deliverDate,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = {
                                editActivity = it
                                showDialogEdit = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = {
                                activityIdToExclude = it.id
                                showDialogExclude = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Apagar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }

    if (showDialogExclude){
        AlertDialog(
            onDismissRequest = { showDialogExclude = false },
            title = {
                Text(
                    text = "Excluir atividade"
                )
            },
            text = {
                Text(
                    text = "Confirma a exclusão da atividade selecionada?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                    showDialogExclude = false
                    database.getReference("activities")
                        .child(activityIdToExclude)
                        .removeValue()
                }) {
                    Text(text = "Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                    showDialogExclude = false
                }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }


    if (showDialog) {
        ActivityDialog(
            onDismiss = { showDialog = false},
            onConfirm = { showDialog = false},
            operation = "Adicionar"
        )
    }

    // Exibe o AlertDialog AtividadeDialog para edição
    if (showDialogEdit) {
        ActivityDialog(
            onDismiss = { showDialogEdit = false },
            onConfirm = { showDialogEdit = false },
            operation = "Editar",
            activity = editActivity
        )
    }


}

@Preview
@Composable
private fun HomeScreenPreview() {
    PrazoCertoTheme() {
        HomeScreen(navController = rememberNavController())
    }
}