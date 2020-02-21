package com.portfolio.creepur.viewmodels.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.portfolio.creepur.R
import com.portfolio.creepur.core.MainApplication
import com.portfolio.creepur.models.Bookmark
import com.portfolio.creepur.repos.Firebase
import kotlinx.android.synthetic.main.segment_bookmark.view.*
import java.util.*
import kotlin.collections.ArrayList

class BookmarkRecycler : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val bookmarkList: ArrayList<Bookmark> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookmarkViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.segment_bookmark, parent, false))
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BookmarkViewHolder -> holder.bind(bookmarkList[position])
        }
    }

    override fun getItemCount(): Int = bookmarkList.size

    fun setNewData(list: ArrayList<Bookmark>){
        this.bookmarkList.clear()
        this.bookmarkList.addAll(list)
        notifyDataSetChanged()
    }
}

class BookmarkViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    private val layout: ConstraintLayout = itemView.bookmarkConstraint
    private val name: TextView = itemView.bookmarkName
    private val rep: TextView = itemView.bookmarkRepNumber
    private val repName: TextView = itemView.bookmarkRep
    private val pro: TextView = itemView.bookmarkPro
    private val image: ImageView = itemView.bookmarkAvatar
    private val x: TextView = itemView.buttonX

    // annotations literally just to be able to capitalize something... lol
    @SuppressLint("DefaultLocale")
    @ExperimentalStdlibApi
    fun bind(bookmark: Bookmark){
        name.text = bookmark.username
        rep.text = bookmark.reputation.toString()
        repName.text = bookmark.reputationName
        pro.text = bookmark.pro.toString().capitalize(Locale.US)
        layout.setOnClickListener { x.visibility = if(x.visibility == View.VISIBLE) View.GONE else View.VISIBLE }
        x.setOnClickListener { Firebase.deleteBookmarkFromFirebase(bookmark, MainApplication.currentUser!! ) }

        val requestOptions = RequestOptions().placeholder(R.drawable.ic_person_outline_black_24dp)
            .error(R.drawable.ic_person_outline_black_24dp)
        Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions).load(bookmark.avatar).into(image)
    }
}