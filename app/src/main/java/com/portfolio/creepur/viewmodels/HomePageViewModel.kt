package com.portfolio.creepur.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.portfolio.creepur.models.Bookmark
import com.portfolio.creepur.models.Data
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.repos.Firebase
import com.portfolio.creepur.repos.RetrofitClient

class HomePageViewModel(private val currentUser: UserAccountSignedIn?) : ViewModel() {

    private val client: RetrofitClient = RetrofitClient
    private var activeCreepData: Data? = null

    fun getUserInfo(): LiveData<Data> {
        return client.getUserData()
    }

    fun setActiveCreepData(data: Data){
        this.activeCreepData = data
    }

    fun getActiveCreepData(): Data? = this.activeCreepData

    // might use this later on a personal settings page
    fun getUserAccountSignedIn(): LiveData<UserAccountSignedIn> = Firebase.getUserAccountSignedIn()

    fun getACreepInfo(username: String, context: Context) {
        client.callForAnAccount(username, context)
    }

    fun addCreepToFirebase(bookmark: Bookmark){
        if(currentUser != null) {
            Firebase.inputBookmarkToDatabase(bookmark, currentUser)
        }
    }
}