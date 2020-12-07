package com.abdullahalomair.flickerexplorer.controller

import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.model.Cities
import com.abdullahalomair.flickerexplorer.model.Photo
import com.abdullahalomair.flickerexplorer.viewmodel.PhotoExplorerFragmentViewModel
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*

private const val GOOGLE_MAP_FRAGMENT = "GoogleMapBottomSheet"
class PhotoExplorerFragment : Fragment() {
    private lateinit var photoExplorerViewModel: PhotoExplorerFragmentViewModel
    private lateinit var searchCityEditText: AutoCompleteTextView
    private lateinit var searchCityAdapter: ArrayAdapter<String>
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var photoInterestingAdapter: PhotoInterestingAdapter
    private lateinit var googleSheet : GoogleMapBottomSheet
    private lateinit var photos: List<Photo>
    private lateinit var interestingPhotos: List<Photo>
    private val scope = CoroutineScope(Dispatchers.IO)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoExplorerViewModel = ViewModelProvider(this)
            .get(PhotoExplorerFragmentViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.explorer_fragment, container, false)
        searchCityEditText = view.findViewById(R.id.search_city_by_name)
        photosRecyclerView = view.findViewById(R.id.interesting_recyclerView)
        progressBar = view.findViewById(R.id.explorer_progress)
        photosRecyclerView.layoutManager = GridLayoutManager(context, 3)


        scope.launch {
            val allCitiesNames = getAllCitiesNames()
            withContext(Dispatchers.Main) {
                searchCityAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.simple_dropdown_item,
                    R.id.autoComplete, allCitiesNames
                )
                searchCityEditText.setAdapter(searchCityAdapter)
            }
        }
        searchCityEditText.setOnItemClickListener { parent, _, position, _ ->
            val getCityName: String = parent.getItemAtPosition(position).toString()
            scope.launch {
                try {
                    displayPhotoBySearch(getCityName)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            requireContext().getText(R.string.error_in_grpc),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            searchCityEditText.text.clear()
            val imm =
                context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }

        photosRecyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        photoExplorerViewModel.getInterestingPhotos().observe(
            viewLifecycleOwner, { galleryItems ->
                interestingPhotos = galleryItems
                photos = emptyList()
                photoInterestingAdapter =
                    PhotoInterestingAdapter(requireActivity(), requireContext(), galleryItems)
                photosRecyclerView.adapter = photoInterestingAdapter
                photosRecyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        )
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
                    googleSheet = if (interestingPhotos.isEmpty()) {
                        GoogleMapBottomSheet(photos)
                    }else{
                        GoogleMapBottomSheet(interestingPhotos)
                    }
                    googleSheet.show(requireActivity().supportFragmentManager,GOOGLE_MAP_FRAGMENT)
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

    private  fun displayPhotoBySearch(getCityName: String) {
        val gc = Geocoder(requireContext(), Locale.getDefault())
        val address: List<Address> = gc.getFromLocationName(getCityName, 1)
        if (address[0].hasLatitude() && address[0].hasLongitude()) {
            val longitude = address[0].longitude
            val latitude = address[0].latitude
            requireActivity().runOnUiThread {
                photosRecyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                photoExplorerViewModel.getSearchedBasedPhotos(
                    latitude.toString(),
                    longitude.toString()
                )
                    .observe(viewLifecycleOwner,
                        { searchedPhotos ->
                            photos = searchedPhotos
                            interestingPhotos = emptyList()
                            activity?.title = getCityName
                            photoInterestingAdapter =
                                PhotoInterestingAdapter(
                                    requireActivity(),
                                    requireContext(),
                                    photos = searchedPhotos
                                )
                            photosRecyclerView.adapter = photoInterestingAdapter
                            photosRecyclerView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                    )
            }
        }
    }

    private  fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString =
                context?.assets?.open(fileName)?.bufferedReader().use { it?.readText() ?: "" }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private suspend fun getAllCitiesNames(): List<String> {
        val jsonFileString = getJsonDataFromAsset("cities.json")
        val gson = Gson()
        val allCities: Cities = gson.fromJson(jsonFileString, Cities::class.java)
        return allCities.cities
    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }

    companion object {
        fun newInstance(): PhotoExplorerFragment {
            return PhotoExplorerFragment()
        }
    }
}