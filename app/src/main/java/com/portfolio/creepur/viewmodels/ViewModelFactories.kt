package com.portfolio.creepur.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.viewmodels.adapters.BookmarkRecycler

class HomePageViewModelFactory (private val currentUser: UserAccountSignedIn?): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomePageViewModel(currentUser) as T
    }
}

class BookmarkViewModelFactory (private val currentUser: UserAccountSignedIn?, private val adapter: BookmarkRecycler): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookmarksViewModel(currentUser, adapter) as T
    }
}