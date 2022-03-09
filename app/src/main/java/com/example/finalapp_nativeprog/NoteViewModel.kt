package com.example.finalapp_nativeprog

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class NoteViewModel: ViewModel() {
    var notes = mutableStateOf(mutableListOf<Note>())
    // var list = mutableStateListOf<Note>()

    fun addNote(note: Note) {
        var newNotes = notes.value.toMutableList()
        newNotes.add(note)
        notes.value = newNotes
    }
}