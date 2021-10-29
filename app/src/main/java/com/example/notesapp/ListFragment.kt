package com.example.notesapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.adapters.NoteAdapter
import com.example.notesapp.data.Note
import kotlinx.coroutines.*

class ListFragment : Fragment() {
    private lateinit var rvNotes: RecyclerView
    private lateinit var rvAdapter: NoteAdapter
    private lateinit var editText: EditText
    private lateinit var submitBtn: Button

    lateinit var listViewModel: ListViewModel
    private lateinit var notes: List<Note>

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // We use 'requireActivity()' to access MainActivity here
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        notes = arrayListOf()

        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.getNotes().observe(viewLifecycleOwner, {
                notes -> rvAdapter.update(notes)
        })

        editText = view.findViewById(R.id.tvNewNote)
        submitBtn = view.findViewById(R.id.btSubmit)
        submitBtn.setOnClickListener {
            listViewModel.addNote(Note("", editText.text.toString()))
            editText.text.clear()
            editText.clearFocus()
        }

        rvNotes = view.findViewById(R.id.rvNotes)
        rvAdapter = NoteAdapter(this)
        rvNotes.adapter = rvAdapter
        rvNotes.layoutManager = LinearLayoutManager(requireContext())

        listViewModel.getData()

        return view
    }

    override fun onResume() {
        super.onResume()
        // We call the 'getData' function from our ViewModel after a one second delay because Firestore takes some time
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            listViewModel.getData()
        }
    }
}