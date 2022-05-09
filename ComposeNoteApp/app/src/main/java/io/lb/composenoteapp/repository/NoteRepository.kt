package io.lb.composenoteapp.repository

import io.lb.composenoteapp.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun loadNoteById(id: String): Note?
    suspend fun addNote(note: Note)
    suspend fun removeNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun removeAll()
}