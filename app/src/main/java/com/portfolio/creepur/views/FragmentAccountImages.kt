package com.portfolio.creepur.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.creepur.R
import com.portfolio.creepur.core.MainApplication
import com.portfolio.creepur.models.DataImages
import com.portfolio.creepur.viewmodels.HomePageViewModel
import com.portfolio.creepur.viewmodels.HomePageViewModelFactory
import com.portfolio.creepur.viewmodels.adapters.ImagesRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_account_images.view.*


class FragmentAccountImages : Fragment() {

    private val viewModel: HomePageViewModel by lazy { ViewModelProvider(this, HomePageViewModelFactory(MainApplication.currentUser)).get(HomePageViewModel::class.java) }

    private lateinit var recycler: RecyclerView
    private lateinit var userName: TextView
    private val adapter by lazy { ImagesRecyclerAdapter() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view: View = inflater.inflate(R.layout.fragment_account_images, container, false)
        userName = view.fragmentAccountImagesName
        recycler = view.imagesRecycler
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = this.adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getUserImages().observe(viewLifecycleOwner, Observer {
            updateView(it)
            adapter.setData(it) })
    }

    private fun updateView(listOfImages: ArrayList<DataImages>?){
        userName.text = listOfImages?.get(0)?.account_url
    }
}