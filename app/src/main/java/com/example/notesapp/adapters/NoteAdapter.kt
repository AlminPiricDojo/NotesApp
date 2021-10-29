package com.example.notesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.ListFragment
import com.example.notesapp.R
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.NoteRowBinding

class NoteAdapter(private val listFragment: ListFragment): RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {
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
                /**
                 * We will use Shared Preferences here to pass the NoteId from our NoteAdapter to the Update Fragment
                 * There is a much cleaner way of doing this, but we will leave that up to you
                 * Hint: look into 'navArgs'
                 * Another option is 'Shared ViewModel'
                 */
                with(listFragment.sharedPreferences.edit()) {
                    putString("NoteId", note.id)
                    apply()
                }
                listFragment.findNavController().navigate(R.id.action_list_to_update)
            }
            ibDeleteNote.setOnClickListener {
                listFragment.listViewModel.deleteNote(note.id)
            }
        }
    }

    override fun getItemCount() = notes.size

    fun update(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }
}
