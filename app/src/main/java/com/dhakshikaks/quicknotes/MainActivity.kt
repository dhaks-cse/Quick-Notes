package com.dhakshikaks.quicknotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dhakshikaks.quicknotes.ui.theme.QuickNotesTheme

// -------------------- ACTIVITY --------------------

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickNotesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    QuickNotesScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// -------------------- VIEWMODEL (BUSINESS LOGIC) --------------------

class NotesViewModel : ViewModel() {

    var noteText by mutableStateOf("")
        private set

    var notes = mutableStateListOf<String>()
        private set

    fun onNoteTextChange(text: String) {
        noteText = text
    }

    fun addNote() {
        if (noteText.isNotBlank()) {
            notes.add(noteText)
            noteText = ""
        }
    }

    fun deleteNote(note: String) {
        notes.remove(note)
    }
}

// -------------------- UI SCREEN --------------------

@Composable
fun QuickNotesScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = viewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Quick Notes 📝",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.noteText,
            onValueChange = viewModel::onNoteTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter a note") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = viewModel::addNote,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Note")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(viewModel.notes) { note ->
                NoteItem(
                    note = note,
                    onDelete = { viewModel.deleteNote(note) }
                )
            }
        }
    }
}

// -------------------- NOTE ITEM UI --------------------

@Composable
fun NoteItem(
    note: String,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onDelete() }
    ) {
        Text(
            text = note,
            modifier = Modifier.padding(12.dp)
        )
    }
}