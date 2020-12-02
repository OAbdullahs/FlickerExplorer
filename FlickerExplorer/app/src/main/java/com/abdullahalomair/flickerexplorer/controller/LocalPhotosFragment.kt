package com.abdullahalomair.flickerexplorer.controller

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.LAT
import com.abdullahalomair.flickerexplorer.LON
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.viewmodel.LocalPhotosFragmentViewModel
import java.util.*


class LocalPhotosFragment: Fragment() {
private lateinit var localPhotosViewModel:LocalPhotosFragmentViewModel
private lateinit var photoLocalAdapter: PhotoLocalAdapter
private lateinit var photoLocalRecyclerView: RecyclerView
private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localPhotosViewModel = ViewModelProvider(this)
            .get(LocalPhotosFragmentViewModel::class.java)
    }


    private fun getCityName(_lat: String, _lon: String):String{
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(_lat.toDouble(), _lon.toDouble(), 1)
        return  addresses[0].getAddressLine(0)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.local_photos_recyclerview, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        photoLocalRecyclerView = view.findViewById(R.id.local_photo_recyclerview)
        photoLocalRecyclerView.layoutManager = GridLayoutManager(context, 2)

        photoLocalRecyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE


        val bundle = this.arguments
        if (bundle != null) {
             val lat =  bundle.getString(LAT, "")
             val lon = bundle.getString(LON, "")
            activity?.title = getCityName(lat, lon)
            localPhotosViewModel.fetchLocalPhotos(lat, lon).observe(
                viewLifecycleOwner, { photos ->
                    photoLocalAdapter = PhotoLocalAdapter(activity!!,context!!, photos)
                    photoLocalRecyclerView.adapter = photoLocalAdapter
                    photoLocalRecyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            )

        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        //Change actionBar title
        activity?.title = context?.getText(R.string.explorer)
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        onCreate(savedInstanceState)
    }

companion object{
    fun newInstance(): LocalPhotosFragment {
        return LocalPhotosFragment()
    }
}
}