package com.android.blinkitadminjc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.blinkitadminjc.model.Product
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())

    val products: StateFlow<List<Product>> get() = _products


    fun updateSearchText(newText: String) {
        _searchText.value = newText
    }


    fun setSearching(isSearching: Boolean) {
        _isSearching.value = isSearching
    }

    fun fetchProducts() {
        val productList = mutableListOf<Product>()
        val databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("AllProducts")

        databaseReference.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                dataSnapshot.children.forEach { snapshot ->
                    val product = snapshot.getValue(Product::class.java)
                    if (product != null) {
                        productList.add(product)
                    }
                }
                _products.value = productList
            } else {
                Log.d("HomeViewModel", "No data available")
            }
        }.addOnFailureListener { exception ->
            Log.e("HomeViewModel", "Error fetching data", exception)
        }
    }

}
