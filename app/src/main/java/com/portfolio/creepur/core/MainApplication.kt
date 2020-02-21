package com.portfolio.creepur.core

import android.app.Application
import android.content.Context
import com.portfolio.creepur.models.UserAccountSignedIn

class MainApplication : Application() {

companion object{
    lateinit var context: Context
    var currentUser: UserAccountSignedIn? = null
}

    override fun onCreate() {
        context = applicationContext
        super.onCreate()
    }


    // TODO: When this project is done (MVP) then just go back in and figure out how to integrate databinding :D (or Koin, if there's no useful space for databinding) //


}