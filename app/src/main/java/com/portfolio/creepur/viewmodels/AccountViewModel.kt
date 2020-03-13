package com.portfolio.creepur.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.repos.Firebase
import com.portfolio.creepur.views.LoginActivity

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = application.getSharedPreferences("MODEL_PREFERENCES", Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun logout(context: Context){
        editor.clear()
        editor.commit()
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }

    fun deleteFirebaseAccount(user: UserAccountSignedIn?){
        if(user != null) {
            Firebase.deleteFirebaseAccount(user)
        }
    }


}