package com.rafi.belajarroomdatabase.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.belajarroomdatabase.ui.customwidget.TopAppBar
import com.rafi.belajarroomdatabase.ui.navigation.AlamatNavigasi
import com.rafi.belajarroomdatabase.ui.viewmodel.ContactEvent
import com.rafi.belajarroomdatabase.ui.viewmodel.ContactUIState
import com.rafi.belajarroomdatabase.ui.viewmodel.ContactViewModel
import com.rafi.belajarroomdatabase.ui.viewmodel.FormErrorState
import com.rafi.belajarroomdatabase.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiInsert : AlamatNavigasi{
    override val route: String = "insert_contact"
}

@Composable
fun InsertBodyContact(
    modifier: Modifier = Modifier,
    onValueChange: (ContactEvent) -> Unit,
    uiState: ContactUIState,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMahasiswa(
            contactEvent = uiState.contactEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun InsertContactView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState //Ambil UI State dari ViewModel
    val snackbarHostState = remember { SnackbarHostState() } //Snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) //Tampilkan Snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mahasiswa",
                modifier = Modifier
            )

            //Isi Body
            InsertBodyContact (
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.UpdateState(updatedEvent) // Update State di View Model
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()){
                            viewModel.saveData()
                            delay(500)
                            withContext(Dispatchers.Main) {
                                onNavigate()
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun FormMahasiswa(
    contactEvent: ContactEvent = ContactEvent(),
    onValueChange: (ContactEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){
    val jenisKelasmin = listOf("Laki-Laki", "Perempuan")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = contactEvent.nama,
            onValueChange = {
                onValueChange(contactEvent.copy(nama = it))
            },
            label = {
                Text("Nama")
            },
            isError = errorState.nama != null,
            placeholder = {
                Text("Masukkan Nama")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = contactEvent.nomor,
            onValueChange = {
                onValueChange(contactEvent.copy(nomor = it))
            },
            label = {
                Text("Nomor Telepon")
            },
            isError = errorState.nomor != null,
            placeholder = {
                Text("Masukkan Nomor")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.nomor ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Kelamin")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenisKelasmin.forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = contactEvent.jenisKelamin == jk,
                        onClick = {
                            onValueChange(contactEvent.copy(jenisKelamin = jk))
                        }
                    )
                    Text(
                        text = jk
                    )
                }
            }
        }
        Text(
            text = errorState.jenisKelamin ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = contactEvent.email,
            onValueChange = {
                onValueChange(contactEvent.copy(email = it))
            },
            label = {
                Text("Email")
            },
            isError = errorState.email != null,
            placeholder = {
                Text("Masukkan Email")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Text(
            text = errorState.email ?: "",
            color = Color.Red
        )

    }
}