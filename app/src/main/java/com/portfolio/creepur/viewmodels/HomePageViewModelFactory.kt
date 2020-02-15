package com.portfolio.creepur.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.portfolio.creepur.models.UserAccountSignedIn

class HomePageViewModelFactory (private val currentUser: UserAccountSignedIn?): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomePageViewModel(currentUser) as T
    }
}