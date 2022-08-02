package com.example.optisoltask.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class BindingAdapters {

    companion object {

        @JvmStatic
        @BindingAdapter("internetImage")
        open fun internetImage(view: ImageView, imageUrl: String?) {
            if (imageUrl != null)
                Glide.with(view.context).load(imageUrl)
                    .apply(RequestOptions().optionalCenterCrop()).into(
                        view
                    )


        }
    }

}