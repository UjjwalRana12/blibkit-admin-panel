package com.android.blinkitadminjc.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.android.blinkitjc.utils.Utils
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AdminViewModel:ViewModel() {

    private val _isImageUploaded = MutableStateFlow(false)
    var isImageUploaded : StateFlow<Boolean> = _isImageUploaded

    private val _downloadUrls = MutableStateFlow<ArrayList<String?>>(arrayListOf())
    var downloadUrls : StateFlow<ArrayList<String?>> = _downloadUrls


    fun saveImageInDB(imageUri:ArrayList<Uri>){
        val downloadURLs=ArrayList<String?>()

        imageUri.forEach {uri->
            val imageRef= FirebaseStorage.getInstance().reference.child(Utils.getCurrentUserID().toString())
            imageRef.putFile(uri).continueWithTask{
                imageRef.downloadUrl
            }.addOnCompleteListener {task->
                val url = task.result
                downloadURLs.add(url.toString())

                if(downloadURLs.size==imageUri.size){
                    _isImageUploaded.value = true
                    _downloadUrls.value= downloadURLs
                }

            }
        }

    }
}