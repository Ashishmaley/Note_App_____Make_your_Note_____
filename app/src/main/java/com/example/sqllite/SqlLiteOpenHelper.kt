package com.example.sqllite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlLiteOpenHelper(context:Context) :SQLiteOpenHelper(context,"Notes.db",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Notes(id integer primary key autoincrement,note varchar(300))")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Notes")
    }
    fun insertNote(id : Int? , note : String): Boolean {
        val DB : SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("Note",note)
        val result = DB.insert("Notes",null,contentValues)
        return result.toInt() != -1
    }
    @SuppressLint("Range")
    fun getItem():ArrayList<Note> {
        val arr: ArrayList<Note> = ArrayList()

        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Notes", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val noteText = cursor.getString(cursor.getColumnIndex("note"))

            val note = Note(id, noteText)
            arr.add(note)
        }
        cursor.close()
        db.close()
        return arr
    }

    fun deleteNote(noteId: Int): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM Notes where id = $noteId", null)
        return if(cursor.count>0) {
            var result = db.delete("Notes", "id = $noteId",null )
            db.close()
            result !=-1

        } else
            false
    }
}