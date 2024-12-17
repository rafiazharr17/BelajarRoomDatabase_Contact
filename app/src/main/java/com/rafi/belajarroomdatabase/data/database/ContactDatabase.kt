package com.rafi.belajarroomdatabase.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rafi.belajarroomdatabase.data.dao.ContactDao
import com.rafi.belajarroomdatabase.data.entity.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object{
        @Volatile
        private var Instance: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "Contact Database"
                )
                    .build().also { Instance = it }
            })
        }
    }
}