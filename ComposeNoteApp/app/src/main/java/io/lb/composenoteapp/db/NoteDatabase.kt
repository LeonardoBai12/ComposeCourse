package io.lb.composenoteapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.lb.composenoteapp.data_source.NoteDao
import io.lb.composenoteapp.model.Note
import io.lb.composenoteapp.util.DateConverter
import io.lb.composenoteapp.util.UUIDConverter

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDAO(): NoteDao
}