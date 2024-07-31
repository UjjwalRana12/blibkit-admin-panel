package com.android.blinkitadminjc.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    // Function to update the search text
    fun updateSearchText(newText: String) {
        _searchText.value = newText
    }

    // Function to update the search state
    fun setSearching(isSearching: Boolean) {
        _isSearching.value = isSearching
    }
}
