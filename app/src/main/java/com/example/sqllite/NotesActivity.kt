package com.example.sqllite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sqllite.databinding.ActivityNotesBinding

class NotesActivity : AppCompatActivity() {
    private lateinit var note : String 
    private lateinit var dbHelper: SqlLiteOpenHelper
    private lateinit var noteList : ArrayList<Note>
    private lateinit var binding : ActivityNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = SqlLiteOpenHelper(this)
        noteList = dbHelper.getItem()
    }
    fun save(view: View) {
        note = binding.note.text.toString()
       if(note.isEmpty())
       {
           Toast.makeText(this,"Note is empty",Toast.LENGTH_LONG).show()
       }
        else {

           val result:Boolean = dbHelper.insertNote(null,note)
           if(result){
               Toast.makeText(this,"Note created",Toast.LENGTH_LONG).show()
               onBackPressed()
           }
           else{
               Toast.makeText(this,"Oops!! Found Error",Toast.LENGTH_LONG).show()
           }
       }
    }
}