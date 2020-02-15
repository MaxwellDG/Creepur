package com.portfolio.creepur.views

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.portfolio.creepur.R
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(LoginViewModel::class.java) }

    override fun onResume() {
        //OAuth2 callback goes to the view
        super.onResume()
        val uri: Uri? = intent.data
        if(uri != null && uri.toString().startsWith("creepur://callback")){
            val user: UserAccountSignedIn = viewModel.parseForUserAccount(uri)
            viewModel.inputToFirebase(user)
            viewModel.setSharedPrefs(user.userName!!)
            viewModel.startHomePageActivity(this, user.userName, user)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // automatically login user if possible
        viewModel.checkSharedPrefs()

        // onClick listeners
        buttonSignInWithout.setOnClickListener { viewModel.startHomePageActivity(applicationContext) }
        buttonSignin.setOnClickListener { viewModel.callForAuth(editTextUser.text.toString(), this) }
    }
}
