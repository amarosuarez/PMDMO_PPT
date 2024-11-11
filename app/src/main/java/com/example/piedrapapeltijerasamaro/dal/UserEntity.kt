package com.example.piedrapapeltijerasamaro.dal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    var nick:String = "",
    var pj:Int = 0,
    var pg:Int = 0,
    var lg:Int = 0
)
