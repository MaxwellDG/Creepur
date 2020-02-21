package com.portfolio.creepur.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.portfolio.creepur.models.Bookmark

import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.repos.Firebase
import com.portfolio.creepur.viewmodels.adapters.BookmarkRecycler

class BookmarksViewModel(private val currentUser: UserAccountSignedIn?,val adapter: BookmarkRecycler) : ViewModel() {

    private val bookmarkList: LiveData<ArrayList<Bookmark>> by lazy { Firebase.getActiveUsersBookmarks() }


    fun loadDataSet(){
        Firebase.getBookMarksFromDatabase(currentUser?.accountId!!, adapter)
    }

    fun getBookmarks(): LiveData<ArrayList<Bookmark>> = bookmarkList
}