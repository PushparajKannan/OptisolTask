package com.example.optisoltask.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.optisoltask.R
import com.example.optisoltask.databinding.ItemRoomsListBinding
import com.example.optisoltask.view.model.RoomsModel

class RoomsListAdapter(var itemClickListener: (model: RoomsModel) -> Unit) :
    ListAdapter<RoomsModel, RoomsListAdapter.ViewHolder>(diffCallBack) {

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<RoomsModel>() {
            override fun areItemsTheSame(
                oldItem: RoomsModel,
                newItem: RoomsModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RoomsModel,
                newItem: RoomsModel
            ): Boolean {
                return isContentSame(oldItem, newItem)
            }

        }

        fun isContentSame(oldItem: RoomsModel, newItem: RoomsModel): Boolean {

            return (oldItem.roomName == newItem.roomName)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemRoomsListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_rooms_list,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {

            val tempModel = currentList[position]

            holder.bindView(tempModel)


        } catch (e: Exception) {
        }


    }


    inner class ViewHolder(itemView: ItemRoomsListBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val binding: ItemRoomsListBinding = itemView

        fun bindView(model: RoomsModel) {

            binding.model = model
            itemView.setOnClickListener {
                itemClickListener(model)
            }


        }


    }

    override fun submitList(list: List<RoomsModel>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}