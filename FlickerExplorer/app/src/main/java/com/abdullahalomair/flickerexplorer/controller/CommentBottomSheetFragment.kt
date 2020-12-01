package com.abdullahalomair.flickerexplorer.controller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.model.Comment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CommentBottomSheetFragment(private val comments:List<Comment>)
    : BottomSheetDialogFragment() {
    private lateinit var bottomSheetRecyclerView: RecyclerView
    private lateinit var adapter: BottomSheetAdapter




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_modal, container, false)

        bottomSheetRecyclerView = view.findViewById(R.id.bottom_sheet_recyclerview)
        bottomSheetRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = BottomSheetAdapter(context!!, comments)
        bottomSheetRecyclerView.adapter = adapter


        return view
    }

}