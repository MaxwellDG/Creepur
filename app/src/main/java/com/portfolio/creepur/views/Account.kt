package com.portfolio.creepur.views

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.portfolio.creepur.R
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.repos.Firebase
import com.portfolio.creepur.viewmodels.AccountViewModel
import com.portfolio.creepur.viewmodels.listeners.NavigationItemSelected
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_account.bottomNavigationView
import kotlinx.android.synthetic.main.activity_home_page.*

class Account : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory(application).create(AccountViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        init()

        bottomNavigationView.setOnNavigationItemSelectedListener(NavigationItemSelected(this, intent.getSerializableExtra("ACCOUNT") as UserAccountSignedIn))

        settingsLogout.setOnClickListener { viewModel.logout(this) }
        settingsDelete.setOnClickListener { Firebase.deleteFirebaseAccount(intent.getSerializableExtra("ACCOUNT") as UserAccountSignedIn)
            viewModel.logout(this)}
    }

    private fun init(){
        bottomNavigationView.menu.getItem(0).isChecked = true
        val animations: AnimationDrawable = accountConstraint.background as AnimationDrawable
        animations.setEnterFadeDuration(4000)
        animations.setExitFadeDuration(4000)
        animations.start()
    }
}
