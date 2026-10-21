package br.com.fiap.prazocerto.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.prazocerto.BuildConfig
import br.com.fiap.prazocerto.model.Activity
import com.google.firebase.Firebase
import com.google.firebase.database.database

@Composable
fun ActivityDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    activity: Activity = Activity(),
    operation: String
) {

    // definindo o estado dos campos
    var title by remember { mutableStateOf(activity.title) }
    var discipline by remember { mutableStateOf(activity.discipline) }
    var deliverDate: String by remember { mutableStateOf(activity.deliverDate.toString()) }
    var wasDelivered by remember { mutableStateOf(activity.wasDelivered) }

    val databaseUrl = BuildConfig.DB_CONNECTION_URL

    // Criando o AlertDialog
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = operation)
        },
        text = {
            Column(){
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = {
                        Text(text = "Título da atividade")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = discipline,
                    onValueChange = {
                        discipline = it
                    },
                    label = {
                        Text(text = "Disciplina")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = deliverDate.toString(),
                    onValueChange = {
                        deliverDate = it
                    },
                    label = {
                        Text(text = "Data da entrega")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = wasDelivered,
                        onCheckedChange = {
                            wasDelivered = it
                        }
                    )
                    Text(text = "Entregue")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newActivity = Activity(
                        title = title,
                        discipline = discipline,
                        deliverDate = deliverDate,
                        wasDelivered = wasDelivered
                    )

                    // criando uma instância do Firebase
                    val database = Firebase.database(
                        databaseUrl
                    )

                    // referenciando o nó "activities", afinal estamos tratando de um BD NoSQL
                    // (similar ao MongoDB)
                    val activitiesRef = database.getReference("activities").push()

                    // gerando um ID para a activity
                    newActivity.id = activitiesRef.push().key.toString()

                    // convertendo e gravando a activity no Realtime Database
                    activitiesRef.setValue(newActivity.toJson())

                    onConfirm()
                }
            ) {
                Text(text = "Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Cancelar")
            }
        }
    )
}