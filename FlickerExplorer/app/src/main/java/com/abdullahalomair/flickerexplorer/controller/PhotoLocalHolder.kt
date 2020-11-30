package com.abdullahalomair.flickerexplorer.controller

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.bumptech.glide.Glide


class PhotoLocalHolder(view: View): RecyclerView.ViewHolder(view) {
    private val imageView:ImageView = view.findViewById(R.id.imageBrowseView)

    fun bind(url: String){
        Log.i("GG", url)
        Glide.with(itemView).load(url).into(imageView)
    }
}