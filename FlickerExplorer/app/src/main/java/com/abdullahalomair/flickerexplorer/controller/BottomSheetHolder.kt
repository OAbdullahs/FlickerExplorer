package com.abdullahalomair.flickerexplorer.controller

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.flickerexplorer.R
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class BottomSheetHolder(view: View):RecyclerView.ViewHolder(view) {
    private val userNameTextView: TextView = view.findViewById(R.id.user_name)
    private val userCommentTextView: TextView = view.findViewById(R.id.user_comments)
    private val userCommentDate: TextView = view.findViewById(R.id.user_comments_date)

    fun bind(userName: String, comment: String, createDate: String){
        userNameTextView.text = userName
        userCommentTextView.text = cleanComment(comment)
        userCommentDate.text = "${countCommentDates(createDate)}d"

    }
    private fun cleanComment(comment: String):String{
        return comment.replace(Regex(("<[^>]*>")),"")
            .replace("&amp","")
            .replace("b&gt;","")
            .replace(Regex("[\\n\\t]"),"")
    }
    //count how many days this comment
    private fun countCommentDates(createDate: String):String{
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = createDate.toLong() * 1000L
        val date: Date = Date(calendar.time.time)
        val diff: Long = Date().time - date.time
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString()
    }

}