package com.abdullahalomair.flickerexplorer.controller

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.database.LikedPhotoDB

class LikedPhotoAdapter(private val getActivity: Activity, private val context: Context, val photos: List<LikedPhotoDB>)
    :RecyclerView.Adapter<PhotoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(context)
            .inflate(
                R.layout.activity_display_photos, parent, false
            )
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(getActivity,photos[position])
    }

    override fun getItemCount(): Int = photos.size
}