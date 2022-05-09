package io.lb.composenoteapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.lb.composenoteapp.R
import io.lb.composenoteapp.components.NoteButton
import io.lb.composenoteapp.components.NoteInputText
import io.lb.composenoteapp.components.NoteRow
import io.lb.composenoteapp.model.Note

@ExperimentalComposeUiApi
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
) {
    val context = LocalContext.current
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            NoteTopAppBar()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(
                modifier = Modifier.padding(8.dp),
                text = title,
                label = "Title",
                onValueChange = {
                    title = it
                }
            )
            NoteInputText(
                modifier = Modifier.padding(8.dp),
                text = description,
                label = "Add note",
                onValueChange = {
                    description = it
                }
            )
            NoteButton(
                text = "Save",
                onClick = {
                    if (title.isEmpty() || description.isEmpty()) {
                        return@NoteButton
                    }

                    onAddNote(Note(title = title, description = description))
                    title = ""
                    description = ""

                    Toast.makeText(context, "Note added", Toast.LENGTH_SHORT).show()
                }
            )

            Divider(modifier = Modifier.padding(8.dp),)

            LazyColumn {
                items(notes) { note ->
                    NoteRow(
                        note = note,
                        onClick = {
                            onRemoveNote(it)
                        }
                    )
                }
           }
        }
    }
}

@Composable
private fun NoteTopAppBar() {
    TopAppBar(
        backgroundColor = Color.DarkGray,
        contentColor = Color.White,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h5,
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Rounded.Notifications,
                contentDescription = "Notifications icon"
            )
        }
    )
}