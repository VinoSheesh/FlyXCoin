package com.example.flyxcoin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// UI State: Represents how the screen should look.
sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
}

// One-time events for navigation or showing messages.
sealed class AuthEvent {
    object RegistrationSuccess : AuthEvent()
    object LoginSuccess : AuthEvent()
    data class Error(val message: String) : AuthEvent()
}

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _eventChannel = Channel<AuthEvent>()
    val authEvents = _eventChannel.receiveAsFlow()

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                    val firebaseUser = authResult.user!!
                    val user = hashMapOf("username" to username, "email" to email)
                    firestore.collection("users").document(firebaseUser.uid).set(user).await()
                }
                _eventChannel.send(AuthEvent.RegistrationSuccess)
            } catch (e: Exception) {
                _eventChannel.send(AuthEvent.Error(e.localizedMessage ?: "Unknown error"))
            } finally {
                _uiState.value = AuthUiState.Idle // Always return to Idle state
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                withContext(Dispatchers.IO) {
                    auth.signInWithEmailAndPassword(email, password).await()
                }
                _eventChannel.send(AuthEvent.LoginSuccess)
            } catch (e: Exception) {
                _eventChannel.send(AuthEvent.Error(e.localizedMessage ?: "Unknown error"))
            } finally {
                _uiState.value = AuthUiState.Idle
            }
        }
    }
}
