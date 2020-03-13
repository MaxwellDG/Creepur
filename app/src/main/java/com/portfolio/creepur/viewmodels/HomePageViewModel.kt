package com.portfolio.creepur.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.portfolio.creepur.models.Bookmark
import com.portfolio.creepur.models.Data
import com.portfolio.creepur.models.DataImages
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.repos.Firebase
import com.portfolio.creepur.repos.RetrofitClient

class HomePageViewModel(private val currentUser: UserAccountSignedIn?) : ViewModel() {

    private val client: RetrofitClient = RetrofitClient
    private lateinit var aUserData: LiveData<Data>

    //LiveData Methods

    fun getUserData(): LiveData<Data> = client.getUserData()

    fun getUserImages(): LiveData<ArrayList<DataImages>> = client.getUserImages()



    // Methods for calling ImgurAPI or FireBase

    fun getACreepInfo(username: String, context: Context) {
        client.callForAnAccount(username, context)
    }

    fun getACreepImages(username: String, context: Context){
        client.callForAnAccountsImages(username, context)
    }

    fun getACreepFavorites(username: String, context: Context){

    }

    fun getACreepComments(username: String, context: Context){

    }


    fun addCreepToFirebase(bookmark: Bookmark){
        if(currentUser != null) {
            Firebase.inputBookmarkToDatabase(bookmark, currentUser)
        }
    }




}