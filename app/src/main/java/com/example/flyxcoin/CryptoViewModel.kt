package com.example.flyxcoin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CryptoViewModel(private val repository: CryptoRepository = CryptoRepository()) : ViewModel() {

    val cryptoList = mutableStateOf<List<CryptoData>>(emptyList())
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)
    val username = mutableStateOf("Crypto Trader") // Default username

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    init {
        fetchPrices()
        fetchUsername()
    }

    private fun fetchUsername() {
        viewModelScope.launch {
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                try {
                    val document = firestore.collection("users").document(firebaseUser.uid).get().await()
                    username.value = document.getString("username") ?: "Crypto Trader"
                } catch (e: Exception) {
                    // Handle error, maybe log it or show a message
                }
            }
        }
    }

    fun fetchPrices() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            try {
                val result = repository.getPrices()
                cryptoList.value = result
            } catch (e: Exception) {
                error.value = "Gagal memuat data: ${e.localizedMessage}"
            } finally {
                isLoading.value = false
            }
        }
    }
}
