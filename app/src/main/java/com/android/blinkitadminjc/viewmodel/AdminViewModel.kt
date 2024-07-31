package com.android.blinkitadminjc.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.android.blinkitadminjc.model.Product
import com.android.blinkitjc.utils.Utils
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class AdminViewModel : ViewModel() {

    private val _isImageUploaded = MutableStateFlow(false)
    val isImageUploaded: StateFlow<Boolean> get() = _isImageUploaded

    private val _downloadUrls = MutableStateFlow<List<String>>(emptyList())
    val downloadUrls: StateFlow<List<String>> get() = _downloadUrls

    private val _isProductSaved = MutableStateFlow(false)
    val isProductSaved: StateFlow<Boolean> get() = _isProductSaved

    fun saveImageInDB(imageUris: List<Uri>) {
        val downloadURLs = mutableListOf<String>()

        imageUris.forEach { uri ->
            val imageRef = FirebaseStorage.getInstance().reference.child(Utils.getCurrentUserID().toString())
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

    fun saveProduct(product: Product) {
        FirebaseDatabase.getInstance().getReference("Admin").child("AllProducts/${product.productRandomId}")
            .setValue(product)
            .addOnSuccessListener {
                FirebaseDatabase.getInstance().getReference("Admin").child("ProductCategory/${product.productRandomId}")
                    .setValue(product)
                    .addOnSuccessListener {
                        FirebaseDatabase.getInstance().getReference("Admin").child("ProductTypes/${product.productRandomId}")
                            .setValue(product)
                            .addOnSuccessListener {
                                _isProductSaved.value = true
                            }
                    }
            }
    }

    fun saveAdmin(product: Product, imageUris: List<Uri>) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("AdminInfo")
        val storageReference = FirebaseStorage.getInstance().reference.child(Utils.getCurrentUserID().toString())

        val downloadURLs = mutableListOf<String>()

        // Upload images and store URLs
        imageUris.forEach { uri ->
            val imageRef = storageReference.child("images").child(UUID.randomUUID().toString())
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
                        // Save the admin info along with the image URLs
                        val updatedProduct = product.copy(imageUrls = downloadURLs)
                        databaseReference.setValue(updatedProduct).addOnSuccessListener {
                            _isImageUploaded.value = true
                            _downloadUrls.value = downloadURLs
                        }.addOnFailureListener {
                            _isImageUploaded.value = false
                        }
                    }
                } else {
                    _isImageUploaded.value = false
                }
            }
        }
    }
}
