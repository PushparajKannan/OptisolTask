package com.example.optisoltask.view.model

import android.os.Parcelable
import com.example.optisoltask.database.tablemodel.RoomsDbModel
import kotlinx.parcelize.Parcelize


@Parcelize
data class RoomsModel(
    var checked: Boolean = false,
    var roomName: String,
    var createdDateTime: String
) : Parcelable


fun RoomsModel.toRoomDbModel() = RoomsDbModel(this.checked, this.roomName, this.createdDateTime)


fun RoomsDbModel.toRoomsModel() = RoomsModel(this.isLive, this.name, this.createdDateTime)

fun List<RoomsDbModel>.toRoomsDbModelList() = map {
    RoomsModel(it.isLive, it.name, it.createdDateTime)
}.toList()