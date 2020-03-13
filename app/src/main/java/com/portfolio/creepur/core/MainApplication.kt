package com.portfolio.creepur.core

import android.app.Application
import com.portfolio.creepur.models.UserAccountSignedIn

class MainApplication : Application() {

companion object{
    val context by lazy { this }
    var currentUser: UserAccountSignedIn? = null
}

    // To-Do: Add Koin DI modules here
}