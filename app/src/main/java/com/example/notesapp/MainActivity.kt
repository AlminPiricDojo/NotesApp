package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notesapp.database.DatabaseHandler
import com.example.notesapp.database.NoteModel

class MainActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHandler

    private lateinit var editText: EditText
    private lateinit var submitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHandler(this)

        editText = findViewById(R.id.tvNewNote)
        submitBtn = findViewById(R.id.btSubmit)
        submitBtn.setOnClickListener { postNote() }
    }

    private fun postNote(){
        db.addNote(NoteModel(0, editText.text.toString()))
        editText.text.clear()
        Toast.makeText(this, "Note Added", Toast.LENGTH_LONG).show()
    }
}