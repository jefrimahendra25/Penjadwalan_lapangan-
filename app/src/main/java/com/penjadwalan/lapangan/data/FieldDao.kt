package com.penjadwalan.lapangan.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldDao {
    @Query("SELECT * FROM fields")
    fun getAllFields(): Flow<List<Field>>

    @Query("SELECT * FROM fields WHERE id = :fieldId")
    suspend fun getFieldById(fieldId: Long): Field?

    @Query("SELECT * FROM fields WHERE type = :type")
    fun getFieldsByType(type: String): Flow<List<Field>>

    @Query("SELECT * FROM fields WHERE isOpen = 1")
    fun getOpenFields(): Flow<List<Field>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertField(field: Field): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFields(fields: List<Field>)

    @Update
    suspend fun updateField(field: Field)

    @Delete
    suspend fun deleteField(field: Field)

    @Query("DELETE FROM fields")
    suspend fun deleteAllFields()
}
