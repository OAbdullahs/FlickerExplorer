package com.abdullahalomair.flickerexplorer.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.abdullahalomair.flickerexplorer.PHOTO_ID
import com.abdullahalomair.flickerexplorer.PHOTO_TITLE
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.URL
import com.bumptech.glide.Glide

class DisplayPhoto : AppCompatActivity() {
    private lateinit var photoImageView: ImageView
    private lateinit var likeImageView: ImageView
    private lateinit var photoTitleTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_photo)
        val photoUrl = intent.getStringExtra(URL)
        val photoID = intent.getStringExtra(PHOTO_ID)
        val photoTitle = intent.getStringExtra(PHOTO_TITLE)

        photoTitleTextView = findViewById(R.id.photo_title)
        photoImageView = findViewById(R.id.expanded_photo)
        likeImageView = findViewById(R.id.favorite_image)

        Glide.with(this).load(photoUrl)
            .thumbnail(Glide.with(this).load(R.drawable.placeholder))
            .into(photoImageView)

        photoTitleTextView.text = photoTitle

    }
}