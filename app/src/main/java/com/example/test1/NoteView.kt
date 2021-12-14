package com.example.test1

import com.example.test1.database.NoteData


interface NoteView {

    fun onNoteEmpty()
    fun onSaveSuccess()
    fun shareNote(noteData: NoteData)
    fun showNote(noteData: NoteData?)
}