package com.abdullahalomair.flickerexplorer.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.viewmodel.LikedPhotoFragmentViewModel

class LikedPhotosFragment: Fragment() {
    private lateinit var likedPhotoViewModel: LikedPhotoFragmentViewModel
    private lateinit var photoAdapter: LikedPhotoAdapter
    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var noLikedPhotoTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        likedPhotoViewModel = ViewModelProvider(this)
            .get(LikedPhotoFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.local_photos_recyclerview, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        photoRecyclerView = view.findViewById(R.id.local_photo_recyclerview)
        photoRecyclerView.layoutManager = GridLayoutManager(context, 2)

        noLikedPhotoTextView = view.findViewById(R.id.no_liked_photo_massage)

        photoRecyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        likedPhotoViewModel.getPhotos().observe(
            viewLifecycleOwner,{ likedPhotos ->
                if (likedPhotos.isNotEmpty()) {
                    photoAdapter = LikedPhotoAdapter(activity!!, context!!, likedPhotos)
                    photoRecyclerView.adapter = photoAdapter
                    photoRecyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }else{
                    noLikedPhotoTextView.visibility = View.VISIBLE
                }
            }
        )
        return view
    }

    override fun onResume() {
        super.onResume()
        likedPhotoViewModel.getPhotos().observe(
            viewLifecycleOwner,{ likedPhotos ->
                photoAdapter = LikedPhotoAdapter(activity!!,context!!,likedPhotos)
                photoRecyclerView.adapter = photoAdapter
                photoRecyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        )
    }

    companion object{
        fun newInstance(): LikedPhotosFragment {
            return LikedPhotosFragment()
        }
    }
}