package io.lb.composenoteapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.lb.composenoteapp.data_source.NoteDao
import io.lb.composenoteapp.db.NoteDatabase
import io.lb.composenoteapp.repository.NoteRepository
import io.lb.composenoteapp.repository.NoteRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun providesNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDAO()
    }

    @Singleton
    @Provides
    fun providesNoteDataSource(dao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(dao)
    }
}