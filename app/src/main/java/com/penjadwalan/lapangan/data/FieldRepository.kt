package com.penjadwalan.lapangan.data

import kotlinx.coroutines.flow.Flow

class FieldRepository(private val fieldDao: FieldDao) {
    
    fun getAllFields(): Flow<List<Field>> = fieldDao.getAllFields()
    
    suspend fun getFieldById(fieldId: Long): Field? = fieldDao.getFieldById(fieldId)
    
    fun getFieldsByType(type: String): Flow<List<Field>> = fieldDao.getFieldsByType(type)
    
    fun getOpenFields(): Flow<List<Field>> = fieldDao.getOpenFields()
    
    suspend fun insertField(field: Field): Long = fieldDao.insertField(field)
    
    suspend fun insertFields(fields: List<Field>) = fieldDao.insertFields(fields)
    
    suspend fun updateField(field: Field) = fieldDao.updateField(field)
    
    suspend fun deleteField(field: Field) = fieldDao.deleteField(field)
    
    suspend fun deleteAllFields() = fieldDao.deleteAllFields()
}
