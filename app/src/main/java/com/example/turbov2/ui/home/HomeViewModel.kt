package com.example.turbov2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Turbo.az"
    }
    val text: LiveData<String> = _text

    private val _selectedBrand = MutableLiveData<String>()
    val selectedBrand: LiveData<String> = _selectedBrand

    fun setSelectedBrand(brand: String) {
        _selectedBrand.value = brand
    }
}
