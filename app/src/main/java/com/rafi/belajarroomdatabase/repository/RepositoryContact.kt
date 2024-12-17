package com.rafi.belajarroomdatabase.repository

import com.rafi.belajarroomdatabase.data.entity.Contact
import kotlinx.coroutines.flow.Flow

interface RepositoryContact {
    suspend fun insertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun updateContact(contact: Contact)

    fun getAllContact(): Flow<List<Contact>>

    fun getContact(id: Int): Flow<Contact>
}