package com.example.sqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqllite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var dbHelper: SqlLiteOpenHelper
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notesRecyclerView = binding.recycleView
        dbHelper = SqlLiteOpenHelper(this)
        notesRecyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns
        noteAdapter = NoteAdapter(ArrayList(),dbHelper,this)
        notesRecyclerView.adapter = noteAdapter
        updateNotes()
    }

    fun createNode(view: View) {
        val intent = Intent(this,NotesActivity::class.java)
        startActivity(intent)
    }
    private fun updateNotes() {
        val notes = dbHelper.getItem()
        noteAdapter = NoteAdapter(notes,dbHelper,this)
        notesRecyclerView.adapter = noteAdapter
    }
    override fun onResume() {
        super.onResume()
        updateNotes() // Make sure this function is called when the activity is resumed
    }
}