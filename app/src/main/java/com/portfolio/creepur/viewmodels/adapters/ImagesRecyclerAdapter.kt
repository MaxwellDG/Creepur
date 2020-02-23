package com.portfolio.creepur.viewmodels.adapters

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
}

class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private var image: ImageView = itemView.fragmentAccountImageImage
    private var ups: TextView = itemView.fragmentAccountImagesUps
    private var downs: TextView = itemView.fragmentAccountImagesDowns
    private var title: TextView = itemView.fragmentAccountImageTitle

    fun bind(dataImage: DataImages){
        ups.text = dataImage.ups.toString()
        downs.text = dataImage.downs.toString()
        title.text = dataImage.title.toString()

        val requestOptions = RequestOptions().placeholder(R.drawable.ic_person_outline_black_24dp)
            .error(R.drawable.ic_person_outline_black_24dp)
        Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions).load(dataImage.link).into(image)
    }


}