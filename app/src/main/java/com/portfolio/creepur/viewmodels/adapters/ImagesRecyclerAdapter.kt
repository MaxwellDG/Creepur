package com.portfolio.creepur.viewmodels.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.portfolio.creepur.R
import com.portfolio.creepur.models.DataImages
import kotlinx.android.synthetic.main.segment_fragment_account_image.view.*

class ImagesRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var imagesList: ArrayList<DataImages> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImagesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.segment_fragment_account_image, parent, false))
    }

    override fun getItemCount(): Int = imagesList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ImagesViewHolder -> holder.bind(imagesList[position])
        }
    }

    fun setData(list: ArrayList<DataImages>?){
        imagesList.clear()
        if(list != null) {
            this.imagesList.addAll(list)
        }
        notifyDataSetChanged()
    }
}

class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private var image: ImageView = itemView.fragmentAccountImageImage
    private var imageSubstitute: TextView = itemView.fragmentAccountImageSubstituteImage
    private var ups: TextView = itemView.fragmentAccountImagesUps
    private var downs: TextView = itemView.fragmentAccountImagesDowns
    private var title: TextView = itemView.fragmentAccountImageTitle

    fun bind(dataImage: DataImages?){
        // loads image if post is a single image. Otherwise, a hyperlink is posted with the full post
        if(dataImage?.link?.endsWith("png")!! || dataImage.link.endsWith("jpeg") || dataImage.link.endsWith("jpg")) {
            image.visibility = View.VISIBLE
            imageSubstitute.visibility = View.INVISIBLE
            Glide.with(itemView.context).load(dataImage.link).into(image)
        } else {
            image.visibility = View.INVISIBLE
            imageSubstitute.visibility = View.VISIBLE
            imageSubstitute.text = dataImage.link
        }
        // filling the other fields with data
        ups.text = dataImage.ups.toString()
        downs.text = dataImage.downs.toString()
        title.text = dataImage.title
    }
}