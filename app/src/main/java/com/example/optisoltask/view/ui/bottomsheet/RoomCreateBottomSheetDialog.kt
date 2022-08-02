package com.example.optisoltask.view.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.optisoltask.R
import com.example.optisoltask.databinding.DialogRoomCreateBottomSheetBinding
import com.example.optisoltask.view.ui.activity.MainActivity
import com.example.optisoltask.viewmodel.RoomCreateBottomSheetDialogViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RoomCreateBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogRoomCreateBottomSheetBinding

    private val navArguments by navArgs<RoomCreateBottomSheetDialogArgs>()

    private val activityContext: MainActivity by lazy {
        val activity = requireNotNull(activity)
        (activity as MainActivity)
    }

    private val viewModel by viewModels<RoomCreateBottomSheetDialogViewModel>()


    private val dateFormat = "dd/MM/yyyy  HH:mm:ss"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_room_create_bottom_sheet,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.roomsModel.value = navArguments.model

        binding.btnCreate.text = navArguments.type

        viewModel.isRoomEditable.value = navArguments.type != "Update"

        binding.btnCreate.setOnClickListener {

            viewModel.roomsModel.value?.let { _it ->
                if (_it.roomName.trim().isEmpty()) {
                    Toast.makeText(requireContext(), "Enter Room Name", Toast.LENGTH_SHORT).show()
                } else {
                    _it.apply {
                        this.roomName.trim()
                        this.createdDateTime = getCurrentUTCTime()
                    }
                    if (navArguments.type == "Create") {
                        viewModel.insertRoomData(_it)
                    } else {
                        viewModel.updateRoomData(_it)
                    }
                    dismiss()
                }
            } ?: kotlin.run {
                Toast.makeText(requireContext(), "Null room", Toast.LENGTH_SHORT).show()
            }


        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }


    }

    fun getCurrentUTCTime(): String {
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date())
    }


}