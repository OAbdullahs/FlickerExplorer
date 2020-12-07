package com.abdullahalomair.flickerexplorer.controller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
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
    private lateinit var noCommentTextView: TextView




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_modal, container, false)
        noCommentTextView = view.findViewById(R.id.no_comments_massage)
        bottomSheetRecyclerView = view.findViewById(R.id.bottom_sheet_recyclerview)
        if (comments.isEmpty()){
            bottomSheetRecyclerView.visibility = View.GONE
            noCommentTextView.visibility = View.VISIBLE
        }else {
            bottomSheetRecyclerView.visibility = View.VISIBLE
            noCommentTextView.visibility = View.GONE
            bottomSheetRecyclerView.layoutManager = LinearLayoutManager(context)
            adapter = BottomSheetAdapter(requireContext(), comments)
            bottomSheetRecyclerView.adapter = adapter
        }


        return view
    }

}