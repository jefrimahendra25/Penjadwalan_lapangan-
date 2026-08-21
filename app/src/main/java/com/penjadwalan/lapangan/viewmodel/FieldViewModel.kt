package com.penjadwalan.lapangan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.penjadwalan.lapangan.data.Field
import com.penjadwalan.lapangan.data.FieldRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FieldViewModel(private val repository: FieldRepository) : ViewModel() {
    
    private val _fields = MutableStateFlow<List<Field>>(emptyList())
    val fields: StateFlow<List<Field>> = _fields.asStateFlow()
    
    private val _selectedField = MutableStateFlow<Field?>(null)
    val selectedField: StateFlow<Field?> = _selectedField.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadFields()
    }
    
    fun loadFields() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllFields().collect { fieldList ->
                _fields.value = fieldList
                _isLoading.value = false
            }
        }
    }
    
    fun selectField(field: Field) {
        _selectedField.value = field
    }
    
    fun getFieldsByType(type: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getFieldsByType(type).collect { fieldList ->
                _fields.value = fieldList
                _isLoading.value = false
            }
        }
    }
    
    fun addField(field: Field) {
        viewModelScope.launch {
            repository.insertField(field)
        }
    }
    
    fun updateField(field: Field) {
        viewModelScope.launch {
            repository.updateField(field)
        }
    }
    
    fun deleteField(field: Field) {
        viewModelScope.launch {
            repository.deleteField(field)
        }
    }
    
    suspend fun getFieldById(fieldId: Long): Field? = repository.getFieldById(fieldId)
    
    companion object {
        fun Factory(repository: FieldRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(FieldViewModel::class.java)) {
                        return FieldViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}
