package com.rafi.belajarroomdatabase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.belajarroomdatabase.data.entity.Contact
import com.rafi.belajarroomdatabase.repository.RepositoryContact
import kotlinx.coroutines.launch

class ContactViewModel(
    private val repositoryContact: RepositoryContact
): ViewModel(){
    var uiState by mutableStateOf(ContactUIState())

    fun UpdateState(contactEvent: ContactEvent){
        uiState = uiState.copy(
            contactEvent = contactEvent
        )
    }

    fun validateFields(): Boolean {
        val event = uiState.contactEvent
        val errorState = FormErrorState(
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            nomor = if (event.nomor.isNotEmpty()) null else "Nomor tidak boleh kosong",
            email = if (event.email.isNotEmpty()) null else "Email tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // menyimpan data ke repository
    fun saveData(){
        val currentEvent = uiState.contactEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryContact.insertContact(currentEvent.toContactEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        contactEvent = ContactEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan: ${e.message}"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }

    //reset pesan SnackBar setelah ditampilkan
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class ContactEvent(
    val id: Int = 0,
    val nama: String = "",
    val nomor: String = "",
    val email: String = "",
    val jenisKelamin: String = "",
)

fun ContactEvent.toContactEntity(): Contact = Contact(
    id = id,
    nama = nama,
    nomor = nomor,
    email = email,
    jenisKelamin = jenisKelamin
)

data class FormErrorState(
    val nama: String? = null,
    val nomor: String? = null,
    val email: String? = null,
    val jenisKelamin: String? = null,
){
    fun isValid(): Boolean {
        return nama == null
                && nomor == null
                && email == null
                && jenisKelamin == null
    }
}

data class ContactUIState(
    val contactEvent: ContactEvent = ContactEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)