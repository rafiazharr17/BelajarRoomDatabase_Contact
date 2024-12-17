package com.rafi.belajarroomdatabase.data.entity

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val nomor: String,
    val email: String,
    val jenisKelamin: String,
)
