package com.android.blinkitadminjc.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.android.blinkitjc.utils.Utils
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class AdminViewModel : ViewModel() {

    private val _isImageUploaded = MutableStateFlow(false)
    val isImageUploaded: StateFlow<Boolean> get() = _isImageUploaded

    private val _downloadUrls = MutableStateFlow<List<String>>(emptyList())
    val downloadUrls: StateFlow<List<String>> get() = _downloadUrls

    fun saveImageInDB(imageUris: List<Uri>) {
        val downloadURLs = mutableListOf<String>()

        imageUris.forEach { uri ->
            val imageRef =
                FirebaseStorage.getInstance().reference.child(Utils.getCurrentUserID().toString())
                    .child("images").child(UUID.randomUUID().toString())
            imageRef.putFile(uri).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val url = task.result.toString()
                    downloadURLs.add(url)

                    if (downloadURLs.size == imageUris.size) {
                        _isImageUploaded.value = true
                        _downloadUrls.value = downloadURLs
                    }
                } else {

                    _isImageUploaded.value = false
                }
            }
        }
    }
}
