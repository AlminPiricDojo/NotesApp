package com.example.notesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.MainActivity
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.NoteRowBinding

class NoteAdapter(private val activity: MainActivity): RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {
    private var notes = emptyList<Note>()

    class ItemViewHolder(val binding: NoteRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ItemViewHolder {
        return ItemViewHolder(
            NoteRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notes[position]

        holder.binding.apply {
            tvNote.text = note.noteText
            ibEditNote.setOnClickListener {
                activity.raiseDialog(note.id)
            }
            ibDeleteNote.setOnClickListener {
                activity.mainViewModel.deleteNote(note.id)
            }
        }
    }

    override fun getItemCount() = notes.size

    fun update(notes: List<Note>){
        println("UPDATING DATA")
        this.notes = notes
        notifyDataSetChanged()
    }
}
