package com.abdullahalomair.flickerexplorer.controller


import android.app.Activity
import android.content.Intent
import android.icu.text.CaseMap
import android.provider.ContactsContract
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.*
import com.abdullahalomair.flickerexplorer.model.GalleryItem
import com.abdullahalomair.flickerexplorer.model.Photo
import com.bumptech.glide.Glide



class PhotoHolder(view: View): RecyclerView.ViewHolder(view) {
    private val imageView:ImageView = view.findViewById(R.id.imageBrowseView)
    private lateinit var activity: Activity

    fun bind(getActivity: Activity, photo: Photo){
        Glide.with(itemView).load(photo.url)
            .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
            .into(imageView)
        activity = getActivity

        imageView.setOnClickListener {
            goToExpandedPhoto(photo.url,photo.id,photo.title)
        }
    }
    fun bind(getActivity: Activity,galleryItem: GalleryItem){
        Glide.with(itemView).load(galleryItem.url)
            .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
            .into(imageView)
        activity = getActivity
        imageView.setOnClickListener {
            goToExpandedPhoto(galleryItem.url,galleryItem.id,galleryItem.title)
        }
    }

    private fun goToExpandedPhoto(url:String,id:String,title:String){
        val intent = Intent(activity,DisplayPhoto::class.java)
        intent.putExtra(URL,url)
        intent.putExtra(PHOTO_ID,id)
        intent.putExtra(PHOTO_TITLE,title)
        activity.startActivity(intent)
    }


}