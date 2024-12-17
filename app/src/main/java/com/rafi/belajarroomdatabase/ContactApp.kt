package com.rafi.belajarroomdatabase

import android.app.Application
import com.rafi.belajarroomdatabase.dependeciesinjection.ContainerApp

class ContactApp : Application() {
    lateinit var containerApp: ContainerApp // Fungsinya untuk menyimpan instance
    override fun onCreate(){
        super.onCreate()
        containerApp = ContainerApp(this) // Membuat instance ContainerApp
        // instance adalah object yang dibuat dari class
    }
}