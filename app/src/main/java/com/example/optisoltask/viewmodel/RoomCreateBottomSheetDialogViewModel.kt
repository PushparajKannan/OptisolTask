package com.example.optisoltask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.optisoltask.repository.LocalDataRepository
import com.example.optisoltask.view.model.RoomsModel
import com.example.optisoltask.view.model.toRoomDbModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomCreateBottomSheetDialogViewModel
@Inject constructor(private val localDbRepo: LocalDataRepository) : ViewModel() {

    val roomsModel = MutableLiveData<RoomsModel>()

    val isRoomEditable = MutableLiveData(true)

    fun insertRoomData(model: RoomsModel) =
        viewModelScope.launch(Dispatchers.IO) { localDbRepo.insertRoom(model.toRoomDbModel()) }

    fun updateRoomData(model: RoomsModel) =
        viewModelScope.launch(Dispatchers.IO) { localDbRepo.updateRoomList(model.toRoomDbModel()) }


}