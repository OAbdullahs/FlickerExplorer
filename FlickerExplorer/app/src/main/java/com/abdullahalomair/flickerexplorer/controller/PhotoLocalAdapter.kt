package com.abdullahalomair.flickerexplorer.controller

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.model.Photo

class PhotoLocalAdapter(private val context: Context, val photos: List<Photo>)
    :RecyclerView.Adapter<PhotoLocalHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoLocalHolder {
        val view = LayoutInflater.from(context)
            .inflate(
                R.layout.display_local_photos, parent, false
            )
        return PhotoLocalHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoLocalHolder, position: Int) {
        Log.i("GG",photos[position].url)
        holder.bind(photos[position].url)
    }

    override fun getItemCount(): Int = photos.size
}