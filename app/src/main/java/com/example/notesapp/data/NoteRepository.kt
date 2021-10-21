package com.example.notesapp.data

class NoteRepository(private val noteDao: NoteDao) {

    val getNotes: List<Note> = noteDao.getNotes()

    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

}