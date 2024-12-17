package com.rafi.belajarroomdatabase.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rafi.belajarroomdatabase.ContactApp

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            ContactViewModel(
                contactApp().containerApp.repositoryContact
            )
        }
    }
}

fun CreationExtras.contactApp(): ContactApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ContactApp)