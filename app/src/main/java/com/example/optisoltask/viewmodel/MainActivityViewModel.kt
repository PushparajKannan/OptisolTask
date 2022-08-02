package com.example.optisoltask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.optisoltask.repository.ApiDataRepository
import com.example.optisoltask.repository.LocalDataRepository
import com.example.optisoltask.view.model.RoomsModel
import com.example.optisoltask.view.model.Videos
import com.example.optisoltask.view.model.toRoomDbModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val networkRepo: ApiDataRepository,
    private val localDbRepo: LocalDataRepository
) : ViewModel() {

    val isVideoList = MutableLiveData(true)


    fun fetchVideosPaging(): Flow<PagingData<Videos>> {
        return networkRepo.fetchVideoList()
    }

    val roomList = localDbRepo.getRoomsLiveDataFromDb()

}