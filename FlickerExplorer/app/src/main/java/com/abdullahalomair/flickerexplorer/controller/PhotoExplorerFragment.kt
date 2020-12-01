package com.abdullahalomair.flickerexplorer.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.countries
import com.abdullahalomair.flickerexplorer.viewmodel.PhotoExplorerFragmentViewModel
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import java.io.IOException
import java.lang.reflect.Type

class PhotoExplorerFragment: Fragment() {
    private lateinit var photoExplorerViewModel: PhotoExplorerFragmentViewModel
    private lateinit var searchCityEditText: AutoCompleteTextView
    private lateinit var searchCityAdapter: ArrayAdapter<String>
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var photoInterestingAdapter: PhotoInterestingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoExplorerViewModel = ViewModelProvider(this)
            .get(PhotoExplorerFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.explorer_fragment, container, false)

        //Change actionBar title
        activity?.title = context?.getText(R.string.explorer)

        searchCityEditText = view.findViewById(R.id.search_city_by_name)
        searchCityAdapter = ArrayAdapter(
            context!!,
            R.layout.simple_dropdown_item,
            R.id.autoComplete, countries
        )
        searchCityEditText.setAdapter(searchCityAdapter)

        searchCityEditText.setOnItemClickListener { parent, view, position, id ->


        }

        photosRecyclerView = view.findViewById(R.id.interesting_recyclerView)
        progressBar = view.findViewById(R.id.explorer_progress)
        photosRecyclerView.layoutManager = GridLayoutManager(context, 3)

        photosRecyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        photoExplorerViewModel.getInterestingPhotos().observe(
            viewLifecycleOwner, { galleryItems ->
                photoInterestingAdapter = PhotoInterestingAdapter(activity!!,context!!, galleryItems)
                photosRecyclerView.adapter = photoInterestingAdapter
                photosRecyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        )
        return view
    }


    companion object{
        fun newInstance(): PhotoExplorerFragment{
            return PhotoExplorerFragment()
        }
    }
}