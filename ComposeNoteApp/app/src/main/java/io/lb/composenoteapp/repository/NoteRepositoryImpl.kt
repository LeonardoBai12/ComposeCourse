package io.lb.composenoteapp.repository

import io.lb.composenoteapp.data_source.NoteDao
import io.lb.composenoteapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class NoteRepositoryImpl(
    private val dao: NoteDao
): NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes().flowOn(Dispatchers.IO)
            .conflate()
    }

    override suspend fun loadNoteById(id: String): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun addNote(note: Note) {
        return dao.insert(note)
    }

    override suspend fun removeNote(note: Note) {
        return dao.delete(note)
    }

    override suspend fun updateNote(note: Note) {
        return dao.update(note)
    }

    override suspend fun removeAll() {
        return dao.deleteAll()
    }
}