package com.portfolio.creepur.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.repos.Firebase
import com.portfolio.creepur.repos.RetrofitClient
import com.portfolio.creepur.views.HomePage

class LoginViewModel (application: Application) : AndroidViewModel(application){

    private val preferences = application.getSharedPreferences("MODEL_PREFERENCES", Context.MODE_PRIVATE)
    private val context = application
    private val editor = preferences.edit()


    fun callForAuth(username: String, context: Context){
        RetrofitClient.callForAuth(username, context)
    }

    fun parseForUserAccount(oldUri: Uri): UserAccountSignedIn{
        // for some reason there's a # when there needs to be a ? at index 19
        val uri = Uri.parse(oldUri.toString().replace("#", "?"))
        return UserAccountSignedIn(uri.getQueryParameter("access_token"),
            uri.getQueryParameter("expires_in"), uri.getQueryParameter("token_type"),
            uri.getQueryParameter("refresh_token"), uri.getQueryParameter("account_username"),
            uri.getQueryParameter("account_id"), arrayListOf())
    }

    fun inputToFirebase(user: UserAccountSignedIn) {
        Firebase.inputToDatabase(user)
    }

    fun setSharedPrefs(username: String){
        editor.putString("USERNAME", username)
        editor.commit()
    }

    fun checkSharedPrefs() {
        val string: String? = preferences.getString("USERNAME", "null")
        if (string != "null") {
            val username = preferences.getString("USERNAME", "username")
            // startHomePageActivity(context, username) // TODO: gotta get account information from somewhere when using SharedPrefs
        }
    }

    fun startHomePageActivity(context: Context){
        val intent = Intent(context, HomePage::class.java)
        context.startActivity(intent)
    }

    fun startHomePageActivity(context: Context, user: String?, account: UserAccountSignedIn){
        val intent = Intent(context, HomePage::class.java)
        intent.putExtra("USERNAME", user)
        intent.putExtra("ACCOUNT", account)
        context.startActivity(intent)
    }
}