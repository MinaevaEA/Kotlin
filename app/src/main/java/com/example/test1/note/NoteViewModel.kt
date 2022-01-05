package com.example.test1.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import com.example.test1.database.isEmpty
import kotlinx.coroutines.launch

class NoteViewModel(private val database: NoteDatabase) : ViewModel() {
    val noteData = MutableLiveData<NoteData>()
    val noteShare = MutableLiveData<NoteData>()
    val noteEmpty = MutableLiveData<Unit>()
    val saveSuccess = MutableLiveData<NoteData>()

    fun initData(noteData: NoteData) {
        this.noteData.postValue(noteData)
    }

    fun share() {
        noteData.value?.let {
            if (it.isEmpty()) {
                this.noteEmpty.postValue(Unit)
            } else {
                this.noteShare.postValue(it)
            }
        }
    }

    suspend fun save() {

        noteData.value?.also {
            if (it.isEmpty()) {
                this.noteEmpty.postValue(Unit)
            } else {
                if (it.id > 0) {
                    database.noteDao().update(it)
                } else {
                    database.noteDao().insert(it).also { newNoteId ->
                        updateId(newNoteId)
                    }
                }
                this.saveSuccess.postValue(it)
            }
        }
    }

    fun updateTitle(title: String) {
        noteData.value?.also {
            noteData.value = it.copy(title = title).apply {
                viewModelScope.launch {
                    database.noteDao().update(it)
                }
            }
        }
    }

    fun updateText(text: String) {
        noteData.value?.also {
            noteData.value = it.copy(text = text).apply {
                viewModelScope.launch {
                    database.noteDao().update(it)
                }
            }
        }
    }

    private fun updateId(id: Long) {
        noteData.value?.also {
            noteData.value = it.copy(id = id).apply {
                viewModelScope.launch {
                    database.noteDao().update(it)

                }
            }
        }
    }
}


