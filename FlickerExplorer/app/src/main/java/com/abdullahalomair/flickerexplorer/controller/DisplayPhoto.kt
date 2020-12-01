package com.abdullahalomair.flickerexplorer.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.abdullahalomair.flickerexplorer.PHOTO_ID
import com.abdullahalomair.flickerexplorer.PHOTO_TITLE
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.URL
import com.abdullahalomair.flickerexplorer.viewmodel.DisplayPhotoViewModel
import com.bumptech.glide.Glide

class DisplayPhoto : AppCompatActivity() {
    private val displayPhotoViewModel: DisplayPhotoViewModel by lazy {
        ViewModelProvider(this).get(DisplayPhotoViewModel::class.java)
    }
    private lateinit var photoImageView: ImageView
    private lateinit var likeImageView: ImageView
    private lateinit var photoTitleTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_photo)
        val photoUrl = intent.getStringExtra(URL)
        val photoId = intent.getStringExtra(PHOTO_ID)
        val photoTitle = intent.getStringExtra(PHOTO_TITLE)

        photoTitleTextView = findViewById(R.id.photo_title)
        photoImageView = findViewById(R.id.expanded_photo)
        likeImageView = findViewById(R.id.favorite_image)

        Glide.with(this).load(photoUrl)
            .thumbnail(Glide.with(this).load(R.drawable.placeholder))
            .into(photoImageView)

        
        if (photoId != null) {
            displayPhotoViewModel.getPhotoComments(photoId).observe(
                this,{comments ->
                    Log.i("Data",comments.size.toString())
                }
            )
        }
        photoTitleTextView.text = photoTitle

    }
}