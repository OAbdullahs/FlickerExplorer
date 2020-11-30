package com.abdullahalomair.flickerexplorer.controller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.viewmodel.LocalPhotosFragmentViewModel

class LocalPhotosFragment: Fragment() {
private lateinit var localPhotosViewModel:LocalPhotosFragmentViewModel
private lateinit var photoLocalAdapter: PhotoLocalAdapter
private lateinit var photoLocalRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localPhotosViewModel = ViewModelProvider(this)
            .get(LocalPhotosFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.local_photos_recyclerview, container, false)
        photoLocalRecyclerView = view.findViewById(R.id.local_photo_recyclerview)
        photoLocalRecyclerView.layoutManager = GridLayoutManager(context,2,)

        val bundle = this.arguments
        if (bundle != null) {
            val lat = bundle.getString(LAT, "")
            val lon = bundle.getString(LON, "")
            localPhotosViewModel.fetchLocalPhotos(lat, lon).observe(
                viewLifecycleOwner, { photos ->
                    Log.i("GG",photos.toString())
//                    photoLocalAdapter = PhotoLocalAdapter(this,photos)
//                    photoLocalRecyclerView.adapter = photoLocalAdapter

                }
            )

        }
        else {
            //TODO
        }
        return view
    }

companion object{

    fun newInstance(): LocalPhotosFragment {

        return LocalPhotosFragment()
    }
}
}