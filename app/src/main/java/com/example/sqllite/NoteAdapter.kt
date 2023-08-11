package com.example.sqllite

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val notes: ArrayList<Note>,private val dbHelper: SqlLiteOpenHelper,private val con: Context) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val cardColors = arrayOf("#D69898", "#7EBDB4", "#F3A953", "#9D7ECA", "#64A874")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
        val colorIndex = position % cardColors.size
        val cardColor = Color.parseColor(cardColors[colorIndex])
        holder.cardView.setCardBackgroundColor(cardColor)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val note = notes[position]

            // Inflate the note_display.xml layout
            val dialogView = LayoutInflater.from(context).inflate(R.layout.note_display, null)
            val noteTextView: TextView = dialogView.findViewById(R.id.textView)
            noteTextView.text = note.noteText

            // Create and show a custom AlertDialog with the inflated layout
            val alertDialogBuilder = AlertDialog.Builder(context)
                .setView(dialogView)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        holder.itemView.setOnLongClickListener {
            val context = holder.itemView.context
            val note = notes[position]

            AlertDialog.Builder(context)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete") { _, _ ->
                    // Call a function to delete the note
                    val deleted : Boolean=dbHelper.deleteNote(note.id)
                    if(deleted){
                        notes.remove(note)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, notes.size)
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
            false
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        private val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)

        fun bind(note: Note) {
            noteTextView.text = note.noteText
        }

    }
}