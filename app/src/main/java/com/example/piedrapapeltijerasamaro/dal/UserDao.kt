package com.example.piedrapapeltijerasamaro.dal

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM usuarios")
    suspend fun getAll(): List<UserEntity>
    @Query("SELECT * FROM usuarios WHERE ID = :id")
    suspend fun get(id: Long): UserEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tarea: UserEntity): Long
    @Update
    suspend fun update(tarea: UserEntity)
    @Delete
    suspend fun delete(tarea: UserEntity)
}