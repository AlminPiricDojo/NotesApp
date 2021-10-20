package com.example.notesapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION){

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MyDB"
        private const val TABLE_NOTES = "NotesTable"

        private const val KEY_ID = "_id"
        private const val KEY_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NOTES($KEY_ID INTEGER PRIMARY KEY, $KEY_NOTE TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        onCreate(db)
    }

    fun addNote(note: NoteModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NOTE, note.noteText)

        val success = db.insert(TABLE_NOTES, null, contentValues)

        db.close()
        return success
    }

    fun viewNotes(): ArrayList<NoteModel>{
        val noteList: ArrayList<NoteModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NOTES"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var noteText: String

        if(cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                noteText = cursor.getString(cursor.getColumnIndex(KEY_NOTE))

                val note = NoteModel(id = id, noteText = noteText)
                noteList.add(note)
            } while (cursor.moveToNext())
        }

        return noteList
    }

    fun updateNote(note: NoteModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NOTE, note.noteText)

        val success = db.update(TABLE_NOTES, contentValues, "$KEY_ID = ${note.id}", null)

        db.close()
        return success
    }

    fun deleteNote(note: NoteModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, note.id)
        val success = db.delete(TABLE_NOTES, "$KEY_ID = ${note.id}", null)
        db.close()
        return success
//        success > 0 means it worked
    }
}