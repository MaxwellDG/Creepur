package com.portfolio.creepur.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.portfolio.creepur.R
import com.portfolio.creepur.models.Bookmark
import com.portfolio.creepur.models.Data
import com.portfolio.creepur.models.DataImages
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.repos.Firebase
import com.portfolio.creepur.repos.RetrofitClient
import com.portfolio.creepur.views.HomepageFragmentAccountInfo

class HomePageViewModel(private val currentUser: UserAccountSignedIn?) : ViewModel() {

    private val client: RetrofitClient = RetrofitClient
    private var activeCreepData: Data? = null

    fun getUserInfo(): LiveData<Data> = client.getUserData()

    fun getUserImages(): LiveData<ArrayList<DataImages>> = client.getUserImages()  //TODO: this is starting to look like a massive job that will require a lot of fragments.

    fun makeAppropriateCall(spinnerInput: Int, username: String, context: Context){
        when(spinnerInput){
            0, 3 -> Toast.makeText(context, "Please select a creeping option.", Toast.LENGTH_LONG).show()
            1 -> getACreepInfo(username, context)
            2 -> getACreepImages(username, context)
            else -> Log.d("Tag", "You missed")
        }
    }

    fun setActiveCreepData(data: Data){
        this.activeCreepData = data
    }

    fun getActiveCreepData(): Data? = this.activeCreepData

    fun getACreepInfo(username: String, context: Context) {
        client.callForAnAccount(username, context)
    }

    fun getACreepImages(username: String, context: Context){
        client.callForAnAccountsImages(username, context)
    }

    fun addCreepToFirebase(bookmark: Bookmark){
        if(currentUser != null) {
            Firebase.inputBookmarkToDatabase(bookmark, currentUser)
        }
    }


}