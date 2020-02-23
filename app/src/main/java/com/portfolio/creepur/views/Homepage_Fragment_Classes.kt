package com.portfolio.creepur.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.portfolio.creepur.R
import com.portfolio.creepur.viewmodels.adapters.ImagesRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_account_images.view.*

class HomepageFragmentAccountInfo : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_account_info, container, false)
    //TODO: maybe just like the below example, the views will get their shit assigned here. Maybe I can even add functions in here for that? Gosh diggity doo

    // TODO: just need to now figure out how to get these classes to converse with the same viewModel (or different viewmodel? maybe?) as the homePage's
}

class HomepageFragmentAccountImages : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view: View = inflater.inflate(R.layout.fragment_account_images, container, false)
        view.imagesRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.imagesRecycler.adapter = ImagesRecyclerAdapter()
        return view
    }
}