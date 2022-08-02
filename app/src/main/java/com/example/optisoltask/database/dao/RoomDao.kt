package com.example.optisoltask.database.dao

import androidx.room.*
import com.example.optisoltask.database.tablemodel.RoomsDbModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoomsList(config: List<RoomsDbModel>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRooms(vararg config: RoomsDbModel?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(vararg config: RoomsDbModel?)

    @Delete
    suspend fun deleteRooms(vararg config: RoomsDbModel?)

    @Query("SELECT * FROM RoomsDbModel  ORDER BY name DESC")
    abstract fun loadRoomList(): Flow<List<RoomsDbModel>?>

    fun getRoomsListUpdate() =
        loadRoomList().distinctUntilChanged()
}