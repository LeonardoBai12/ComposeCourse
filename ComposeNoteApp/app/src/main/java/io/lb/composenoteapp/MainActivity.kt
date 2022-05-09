package io.lb.composenoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import io.lb.composenoteapp.screen.NoteScreen
import io.lb.composenoteapp.screen.NoteViewModel
import io.lb.composenoteapp.ui.theme.ComposeNoteAppTheme

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<NoteViewModel>()
            NotesApp(viewModel = viewModel)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun NotesApp(viewModel: NoteViewModel = viewModel()) {
    ComposeNoteAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val notes = viewModel.notes.collectAsState().value

            NoteScreen(
                notes = notes,
                onAddNote = {
                    viewModel.addNote(it)
                },
                onRemoveNote = {
                    viewModel.removeNote(it)
                }
            )
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesApp()
}