package com.abdullahalomair.flickerexplorer.controller

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.model.GalleryItem

class PhotoInterestingAdapter(private val getActivity: Activity,private val context: Context,
                              private val galleryItems: List<GalleryItem>)
    :RecyclerView.Adapter<PhotoHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.display_photos,parent,false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(getActivity,galleryItems[position])
    }

    override fun getItemCount(): Int = galleryItems.size
}