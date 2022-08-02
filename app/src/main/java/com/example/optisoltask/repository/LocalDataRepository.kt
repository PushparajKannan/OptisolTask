package com.example.optisoltask.repository

import com.example.optisoltask.database.db.AppDatabase
import com.example.optisoltask.database.tablemodel.RoomsDbModel
import com.example.optisoltask.view.model.toRoomsDbModelList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataRepository @Inject constructor(val database: AppDatabase) {

    /**
     * ROOM CURD OPERATION FROM LOCAL DATA BASE
     */


    fun getRoomsLiveDataFromDb() = database.roomsDao().getRoomsListUpdate().map {
        it!!.toRoomsDbModelList()
    }.flowOn(Dispatchers.IO)


    suspend fun updateRoomList(user: RoomsDbModel) = withContext(Dispatchers.IO) {
        database.roomsDao().updateRooms(user)
    }

    suspend fun insertRoom(data: RoomsDbModel) = withContext(Dispatchers.IO) {
        database.roomsDao().insertRoom(data)
    }


}