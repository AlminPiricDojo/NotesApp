package com.example.notesapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// click on key word and press Ctrl+Q to read more about them
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile  // writes to this field are immediately visible to other threads
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){  // protection from concurrent execution on multiple threads
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).fallbackToDestructiveMigration()  // Destroys old database on version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}