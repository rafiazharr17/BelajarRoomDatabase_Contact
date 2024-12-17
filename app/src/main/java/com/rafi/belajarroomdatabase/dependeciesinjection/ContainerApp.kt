package com.rafi.belajarroomdatabase.dependeciesinjection

import android.content.Context
import com.rafi.belajarroomdatabase.data.database.ContactDatabase
import com.rafi.belajarroomdatabase.repository.LocalRepositoryContact
import com.rafi.belajarroomdatabase.repository.RepositoryContact

interface InterfaceContainerApp{
    val repositoryContact: RepositoryContact
}

class ContainerApp (private val context: Context) : InterfaceContainerApp {
    override val repositoryContact: RepositoryContact by lazy {
        LocalRepositoryContact(ContactDatabase.getDatabase(context).contactDao())
    }
}