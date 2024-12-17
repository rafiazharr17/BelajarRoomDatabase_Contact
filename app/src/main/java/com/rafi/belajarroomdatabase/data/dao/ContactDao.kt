package com.rafi.belajarroomdatabase.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rafi.belajarroomdatabase.data.entity.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert
    suspend fun insertContact(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY nama ASC")
    fun getAllContact(): Flow<List<Contact>>

    @Query("SELECT * FROM contact WHERE id = :id")
    fun getContact(id : Int): Flow<Contact>

    @Delete
    suspend fun deleteContact (contact: Contact)

    @Update
    suspend fun updateContact (contact: Contact)
}