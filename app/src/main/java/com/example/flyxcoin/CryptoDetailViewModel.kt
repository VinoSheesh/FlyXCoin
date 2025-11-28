package com.example.flyxcoin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CryptoDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    val cryptoId: String = checkNotNull(savedStateHandle["cryptoId"])
    private val repository = CryptoRepository()

    private val _coinDetails = MutableStateFlow<CryptoData?>(null)
    val coinDetails = _coinDetails.asStateFlow()

    init {
        fetchCoinDetails()
    }

    private fun fetchCoinDetails() {
        viewModelScope.launch {
            try {
                // In a real app, you might have a dedicated endpoint for single coin details.
                // We filter from the list, which requires fetching all coins first.
                val allCoins = repository.getPrices()
                _coinDetails.value = allCoins.find { it.id == cryptoId }
            } catch (e: Exception) {
                // Handle error, e.g., post to a separate error state flow
                _coinDetails.value = null // Indicate failure
            }
        }
    }
}
