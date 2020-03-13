package com.portfolio.creepur.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.portfolio.creepur.R
import com.portfolio.creepur.core.MainApplication
import com.portfolio.creepur.viewmodels.HomePageViewModel
import com.portfolio.creepur.viewmodels.HomePageViewModelFactory

class FragmentAccountComments : Fragment() {

    private val viewModel: HomePageViewModel by lazy { ViewModelProvider(this, HomePageViewModelFactory(MainApplication.currentUser)).get(HomePageViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_account_comments, container, false)
        return view
    }
}