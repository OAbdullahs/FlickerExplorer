package com.abdullahalomair.flickerexplorer.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.model.Comment

class BottomSheetAdapter(private val context: Context,
                         private val comments:List<Comment>)
    :RecyclerView.Adapter<BottomSheetHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.bottom_sheet_data,parent,false)
            return BottomSheetHolder(view)
    }

    override fun onBindViewHolder(holder: BottomSheetHolder, position: Int) {
        val userName = comments[position].authorname
        val comment = comments[position]._content
        val date = comments[position].datecreate.toString()
        holder.bind(userName,comment,date)
    }

    override fun getItemCount(): Int = comments.size
}