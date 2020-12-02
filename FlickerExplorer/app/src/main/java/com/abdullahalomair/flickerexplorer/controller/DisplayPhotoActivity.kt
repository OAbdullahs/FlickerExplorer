package com.abdullahalomair.flickerexplorer.controller

import android.database.sqlite.SQLiteConstraintException
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.abdullahalomair.flickerexplorer.PHOTO_ID
import com.abdullahalomair.flickerexplorer.PHOTO_TITLE
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.URL
import com.abdullahalomair.flickerexplorer.database.LikedPhotoDB
import com.abdullahalomair.flickerexplorer.viewmodel.DisplayPhotoViewModel
import com.bumptech.glide.Glide

private const val SHEET_TAG = "BottomSheetDialogFragment"
class DisplayPhoto : AppCompatActivity() {
    private val displayPhotoViewModel: DisplayPhotoViewModel by lazy {
        ViewModelProvider(this).get(DisplayPhotoViewModel::class.java)
    }
    private lateinit var photoImageView: ImageView
    private lateinit var likeImageView: ImageView
    private lateinit var photoTitleTextView: TextView
    private lateinit var commentImageView: ImageView
    private lateinit var repliesCounterTextView: TextView
    private var filledFavorite = false
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_photo)

        photoTitleTextView = findViewById(R.id.photo_title)
        photoImageView = findViewById(R.id.expanded_photo)
        likeImageView = findViewById(R.id.favorite_image)
        commentImageView = findViewById(R.id.comment_image)
        repliesCounterTextView = findViewById(R.id.replies_counter)

        //get data from the Fragment
        val photoUrl = intent.getStringExtra(URL)
        val photoId = intent.getStringExtra(PHOTO_ID)
        val photoTitle = intent.getStringExtra(PHOTO_TITLE)

        //Change the attributes based on the values
        photoTitleTextView.text = photoTitle
        Glide.with(this).load(photoUrl)
                .thumbnail(Glide.with(this).load(R.drawable.placeholder))
                .into(photoImageView)


        if (photoId != null && photoTitle != null && photoUrl != null) {
            displayPhotoViewModel.getLikedPhoto(photoId).observe(
                this,{ likedPhoto ->
                    if (likedPhoto != null){
                        filledFavorite = true
                        changeLikeButton(likedPhoto)
                    }
                    else{
                        filledFavorite = false
                        changeLikeButton(likedPhoto)
                    }
                    //set favorite image click listener
                    likeImageView.setOnClickListener {
                        filledFavorite = !filledFavorite
                        if (filledFavorite) {
                            val likedPhoto = LikedPhotoDB()
                            likedPhoto.photo_id = photoId
                            likedPhoto.title = photoTitle
                            likedPhoto.url = photoUrl

                            likedPhoto(likedPhoto)
                        }
                        else{
                            changeLikeButton(likedPhoto)
                        }
                    }
                }
            )
        }


        //Get comments from Flickr API
        if (photoId != null) {
            displayPhotoViewModel.getPhotoComments(photoId).observe(
                this,{comments ->
                  repliesCounterTextView.text = comments.size.toString()
                    //Display bottom sheet view
                   commentImageView.setOnClickListener {
                      val sheetFragment = CommentBottomSheetFragment(comments)
                       sheetFragment.show(supportFragmentManager,SHEET_TAG)

                        }
                }
            )
        }





    }
    private fun doesPhotoExist(likedPhotoDB: LikedPhotoDB): Boolean{
        var isPhotoExist = false
        displayPhotoViewModel.getLikedPhotos().observe(
            this,{listOfLiked ->
                for (list in listOfLiked){
                    if (list.photo_id == likedPhotoDB.photo_id){
                        isPhotoExist = true
                    }
                }
            }
        )
        return isPhotoExist
    }

    private fun changeLikeButton(likedPhotoDB: LikedPhotoDB?){
        val filledImage = this.getDrawable(R.drawable.ic_favorite_white_filled)
         val notFilledImage = this.getDrawable(R.drawable.ic_favorite_border_white)
        if (filledFavorite) {
            likeImageView.setImageDrawable(filledImage)
            likeImageView.setColorFilter(resources.getColor(R.color.red),PorterDuff.Mode.SRC_IN)
        }
        else{
            likeImageView.setImageDrawable(notFilledImage)
            likeImageView.setColorFilter(resources.getColor(R.color.white),PorterDuff.Mode.SRC_IN)
            if (likedPhotoDB != null){
                if (!doesPhotoExist(likedPhotoDB)) {
                    displayPhotoViewModel.deleteLikedPhoto(likedPhotoDB)
                }
            }

        }
    }
    private fun likedPhoto(likedPhotoDB: LikedPhotoDB){
        val filledImage = this.getDrawable(R.drawable.ic_favorite_white_filled)
                likeImageView.setImageDrawable(filledImage)
                likeImageView.setColorFilter(
                    resources.getColor(R.color.red),
                    PorterDuff.Mode.SRC_IN
                )
                if (!doesPhotoExist(likedPhotoDB)) {
                    displayPhotoViewModel.addLikedPhoto(likedPhotoDB)
                }

    }
}