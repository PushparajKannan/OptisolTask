package com.example.optisoltask.database.tablemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class RoomsDbModel(
    @ColumnInfo(name = "isLive")
    var isLive: Boolean,
    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "createdDateTime")
    var createdDateTime: String

)

