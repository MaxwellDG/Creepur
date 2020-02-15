package com.portfolio.creepur.viewmodels.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.portfolio.creepur.R
import com.portfolio.creepur.models.Bookmark
import kotlinx.android.synthetic.main.segment_bookmark.view.*

class BookmarkRecycler : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var bookmarkList: MutableLiveData<ArrayList<Bookmark>> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookmarkViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.segment_bookmark, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BookmarkViewHolder -> holder.bind(bookmarkList.value!![position])
        }
    }

    override fun getItemCount(): Int {
        return bookmarkList.value!!.size
    }

    fun setData(bookmarkList: ArrayList<Bookmark>){
        // TODO: all this shit is a disaster. Do it later after you've actually input a Bookmark to Firebase
    }
}

class BookmarkViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val name: TextView = itemView.bookmarkName
    private val rep: TextView = itemView.bookmarkRepNumber
    private val repName: TextView = itemView.bookmarkRep
    private val pro: TextView = itemView.bookmarkPro
    private val image: ImageView = itemView.bookmarkAvatar

    fun bind(bookmark: Bookmark){
        name.text = bookmark.username
        rep.text = bookmark.reputation.toString()
        repName.text = bookmark.reputationName
        pro.text = bookmark.pro.toString()

        val requestOptions = RequestOptions().placeholder(R.drawable.ic_person_outline_black_24dp)
            .error(R.drawable.ic_person_outline_black_24dp)
        Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions).load(bookmark.avatar).into(image)
    }

}