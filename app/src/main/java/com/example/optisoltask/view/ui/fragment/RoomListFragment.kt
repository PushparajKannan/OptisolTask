package com.example.optisoltask.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.optisoltask.R
import com.example.optisoltask.databinding.FragmentRoomsListBinding
import com.example.optisoltask.view.adapter.RoomsListAdapter
import com.example.optisoltask.view.model.RoomsModel
import com.example.optisoltask.view.ui.activity.MainActivity
import com.example.optisoltask.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoomListFragment : Fragment() {
    private lateinit var binding: FragmentRoomsListBinding

    /**
     * PARENT ACTIVITY CONTEXT
     **/
    private val activityContext: MainActivity by lazy {
        val activity = requireNotNull(activity)
        (activity as MainActivity)
    }

    /**
     * SHARED VIEW_MODEL LOGIC
     **/
    private val viewModel: MainActivityViewModel by lazy {
        activityContext.viewModel
    }

    private val adapter: RoomsListAdapter by lazy {
        RoomsListAdapter() {
            itemUpdates(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rooms_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvRoomsList.adapter = adapter


        getRoomList()

        binding.fabBtnAddRoom.setOnClickListener {
            popBottomSheet(
                RoomsModel(checked = false, roomName = "", createdDateTime = ""),
                "Create"
            )
        }

    }

    private fun getRoomList() {
        lifecycleScope.launch {
            viewModel.roomList.collect {
                adapter.submitList(null)
                adapter.submitList(it)
            }
        }
    }

    private fun itemUpdates(model: RoomsModel) {
        popBottomSheet(model, "Update")
    }

    private fun popBottomSheet(model: RoomsModel, type: String) {
        val action = RoomListFragmentDirections.actionRoomListFragmentToRoomCreateBottomSheetDialog(
            model,
            type
        )
        requireView().findNavController().navigate(
            action,
            NavOptions.Builder().setLaunchSingleTop(true).build()
        )
    }


}