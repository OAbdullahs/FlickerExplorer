package com.abdullahalomair.flickerexplorer.controller

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.LAT
import com.abdullahalomair.flickerexplorer.LON
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.model.Photo
import com.abdullahalomair.flickerexplorer.viewmodel.LocalPhotosFragmentViewModel
import java.util.*


class LocalPhotosFragment: Fragment() {
private lateinit var localPhotosViewModel:LocalPhotosFragmentViewModel
private lateinit var photoLocalAdapter: PhotoLocalAdapter
private lateinit var photoLocalRecyclerView: RecyclerView
private lateinit var progressBar: ProgressBar
private lateinit var localPhotos:List<Photo>


override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localPhotosViewModel = ViewModelProvider(this)
            .get(LocalPhotosFragmentViewModel::class.java)
        setHasOptionsMenu(true)
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
            try {
            activity?.title = getCityName(lat, lon)
            }catch (e:IndexOutOfBoundsException){}
            localPhotosViewModel.fetchLocalPhotos(lat, lon).observe(
                viewLifecycleOwner, { photos ->
                    localPhotos = photos
                    photoLocalAdapter = PhotoLocalAdapter(requireActivity(),requireContext(), photos)
                    photoLocalRecyclerView.adapter = photoLocalAdapter
                    photoLocalRecyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            )

        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.google_map_menu,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.google_map_menu_item -> {
                try {
                    val googleSheet = GoogleMapBottomSheet(localPhotos)
                    googleSheet.show(requireActivity().supportFragmentManager,"Test")
                }catch (e: Exception){
                    Toast.makeText(requireContext()
                        ,requireActivity().getText(R.string.wait_to_fetch)
                        ,Toast.LENGTH_SHORT).show()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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