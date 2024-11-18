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
    // Obtiene todos los usuarios
    @Query("SELECT * FROM usuarios")
    suspend fun getAll(): List<UserEntity>

    // Obtiene las partidas jugadas de un usuario
    @Query("SELECT pj FROM usuarios WHERE nick = :nick")
    suspend fun getPJ(nick: String): Int

    // Obtiene las partidas ganadas de un usuario
    @Query("SELECT pg FROM usuarios WHERE nick = :nick")
    suspend fun getPG(nick: String): Int

    // Obtiene las luchas ganadas de un usuario
    @Query("SELECT lg FROM usuarios WHERE nick = :nick")
    suspend fun getLG(nick: String): Int

    // Obtiene un usuario
    @Query("SELECT * FROM usuarios WHERE nick = :nick")
    suspend fun get(nick: String): UserEntity

    // Comprueba si un nombre de usuario ya está registrado
    @Query("SELECT COUNT(*) FROM usuarios WHERE nick = :nick")
    suspend fun existsByNick(nick: String): Boolean

    // Inserta un usuario, reemplazando en caso de conflicto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity): Long

    // Actualiza un usuario
    @Update
    suspend fun update(user: UserEntity)

    // Elimina un usuario
    @Delete
    suspend fun delete(user: UserEntity)
}