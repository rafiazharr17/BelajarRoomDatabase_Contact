package com.rafi.belajarroomdatabase.repository

import com.rafi.belajarroomdatabase.data.dao.ContactDao
import com.rafi.belajarroomdatabase.data.entity.Contact
import kotlinx.coroutines.flow.Flow

class LocalRepositoryContact(
    private val contactDao: ContactDao
) : RepositoryContact {
    override suspend fun insertContact(contact: Contact){
        contactDao.insertContact(contact)
    }

    override suspend fun deleteContact(contact: Contact){
        contactDao.deleteContact(contact)
    }

    override suspend fun updateContact(contact: Contact){
        contactDao.updateContact(contact)
    }

    override fun getAllContact(): Flow<List<Contact>> {
        return contactDao.getAllContact()
    }

    override fun getContact(id: Int): Flow<Contact> {
        return contactDao.getContact(id)
    }
}