package com.android.blinkitjc.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.google.firebase.auth.FirebaseAuth

object Utils {

    fun showToast(context: Context, message: String = "Default message") {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getCurrentUserID(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }
}

@Composable
fun CommonDialog(isLoading: Boolean, onDismissRequest: () -> Unit = {}) {
    if (isLoading) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
               CircularProgressIndicator()
            }
        }
    }
}
