package com.abdullahalomair.flickerexplorer.controller

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.model.GalleryItem
import com.abdullahalomair.flickerexplorer.model.Photo

class PhotoInterestingAdapter(private val getActivity: Activity,
                              private val context: Context,
                              private val galleryItems: List<GalleryItem> = emptyList(),
                              private val photos: List<Photo> = emptyList())
    :RecyclerView.Adapter<PhotoHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_display_photos,parent,false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        if (photos.isEmpty()) {
            holder.bind(getActivity, galleryItems[position])
        }else{
            holder.bind(getActivity, photos[position])
        }
    }

    override fun getItemCount(): Int {
        return if (photos.isEmpty()) {
            galleryItems.size
        } else{
            photos.size
        }
    }
}