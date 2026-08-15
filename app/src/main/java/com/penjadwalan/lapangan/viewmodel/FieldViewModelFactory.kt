package com.penjadwalan.lapangan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.penjadwalan.lapangan.data.FieldRepository

class FieldViewModelFactory(
    private val repository: FieldRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FieldViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FieldViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
