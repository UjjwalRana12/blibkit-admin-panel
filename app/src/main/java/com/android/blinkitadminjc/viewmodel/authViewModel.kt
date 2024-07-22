package com.android.blinkitadminjc.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.blinkitjc.model.Admin
import com.android.blinkitjc.utils.Utils
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    private var verificationId: String? = null
    private var storedPhoneNumber: String? = null

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isCurrentUser = MutableStateFlow(false)
    val isCurrentUser = _isCurrentUser

    init {
        auth.currentUser?.let {
            _isCurrentUser.value=true
        }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            storedPhoneNumber?.let {
                signInWithPhoneAuthCredential(credential, it)
            } ?: run {
                _uiState.value = AuthUiState.Error("Phone number is not available")
                _isLoading.value = false
            }
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            _uiState.value = AuthUiState.Error(exception.message)
            _isLoading.value = false
            Log.w(TAG, "onVerificationFailed", exception)

            when (exception) {
                is FirebaseAuthInvalidCredentialsException -> {
                    // Invalid request
                }

                is FirebaseTooManyRequestsException -> {
                    // The SMS quota for the project has been exceeded
                }

                is FirebaseAuthMissingActivityForRecaptchaException -> {
                    // reCAPTCHA verification attempted with null Activity
                }
            }
        }

        override fun onCodeSent(verId: String, token: PhoneAuthProvider.ForceResendingToken) {
            verificationId = verId
            _uiState.value = AuthUiState.CodeSent
            _isLoading.value = false
        }
    }

    fun startPhoneNumberVerification(phoneNumber: String, activity: ComponentActivity) {
        storedPhoneNumber = phoneNumber // Store the phone number
        _isLoading.value = true
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(code: String) {
        verificationId?.let {
            _isLoading.value = true
            val credential = PhoneAuthProvider.getCredential(it, code)
            storedPhoneNumber?.let { phoneNumber ->
                signInWithPhoneAuthCredential(credential, phoneNumber)
            } ?: run {
                _uiState.value = AuthUiState.Error("Phone number is not available")
                _isLoading.value = false
            }
        } ?: run {
            _uiState.value = AuthUiState.Error("Verification ID is not available")
            _isLoading.value = false
        }
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        phoneNumber: String
    ) {
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential).await()
                val AdminId = Utils.getCurrentUserID()
                if (AdminId != null) {
                    val admin = Admin(uid = AdminId, userPhoneNumber = phoneNumber, userAddress = null)
                    FirebaseDatabase.getInstance().getReference("Admin").child("adminInfo")
                        .child(admin.uid!!).setValue(admin)
                    _uiState.value = AuthUiState.Success
                } else {
                    _uiState.value = AuthUiState.Error("Failed to retrieve user ID")
                }
                _isLoading.value = false
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message)
                _isLoading.value = false
            }
        }
    }

    sealed class AuthUiState {
        object Idle : AuthUiState()
        object CodeSent : AuthUiState()
        object Success : AuthUiState()
        data class Error(val message: String?) : AuthUiState()
    }
}