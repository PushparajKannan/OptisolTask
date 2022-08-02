package com.example.optisoltask.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.optisoltask.R
import com.example.optisoltask.databinding.ItemVideosListBinding
import com.example.optisoltask.view.model.Videos

class VideosListAdapter :
    PagingDataAdapter<Videos, VideosListAdapter.ViewHolder>(diffCallBack) {

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Videos>() {
            override fun areItemsTheSame(
                oldItem: Videos,
                newItem: Videos
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Videos,
                newItem: Videos
            ): Boolean {
                return isContentSame(oldItem, newItem)
            }

        }

        fun isContentSame(oldItem: Videos, newItem: Videos): Boolean {

            return (oldItem.id == newItem.id)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemVideosListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_videos_list,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {

            getItem(position)?.let {
                holder.bindView(it)
            }


        } catch (e: Exception) {
        }


    }


    inner class ViewHolder(itemView: ItemVideosListBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val binding: ItemVideosListBinding = itemView

        fun bindView(model: Videos) {

            binding.model = model


        }


    }


}