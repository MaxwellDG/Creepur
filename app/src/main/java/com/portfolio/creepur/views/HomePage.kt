package com.portfolio.creepur.views

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.portfolio.creepur.R
import com.portfolio.creepur.models.Bookmark
import com.portfolio.creepur.models.Data
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.viewmodels.HomePageViewModel
import com.portfolio.creepur.viewmodels.HomePageViewModelFactory
import kotlinx.android.synthetic.main.activity_home_page.*
import java.io.Serializable
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class HomePage : AppCompatActivity(), Serializable {

    private val viewModel: HomePageViewModel by lazy {
        val user: UserAccountSignedIn? = intent.getSerializableExtra("ACCOUNT") as UserAccountSignedIn?
        ViewModelProvider(this, HomePageViewModelFactory(user)).get(HomePageViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // setting LiveData and how to update
        viewModel.getUserInfo().observe(this, Observer<Data> {
            updateViews(it)
            viewModel.setActiveCreepData(it)})

        // onClick listeners
        fab.setOnClickListener { view ->
            viewModel.addCreepToFirebase(Bookmark(creepUsername.text.toString(),
                creepReputationPoints.text.toString().toInt(), creepReputation.text.toString(),
                viewModel.getActiveCreepData()?.avatar.toString(), creepProStatus.text.toString().toBoolean() ))
            Snackbar.make(view, "Bookmark added", Snackbar.LENGTH_LONG) }

        CreepEmButton.setOnClickListener { viewModel.getACreepInfo(creepInputUser.text.toString(), this) }

        // if user signed in -> loads their info
        if(intent.getStringExtra("USERNAME") != null){
            viewModel.getACreepInfo(intent.getStringExtra("USERNAME")!!, this)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateViews(data: Data){
        val theFormat: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        theFormat.timeZone = TimeZone.getTimeZone("EST")
        val theDate: Date = Date(data.created!! * 1000)

        creepDateCreated.text = theFormat.format(theDate)
        creepProStatus.text = if(data.pro_expiration!!) "True" else "False"
        creepUsername.text = data.username
        creepReputationPoints.text = data.reputation.toString()
        creepReputation.text = data.reputation_name
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_person_outline_black_24dp)
            .error(R.drawable.ic_person_outline_black_24dp)
        Glide.with(this).applyDefaultRequestOptions(requestOptions).load(data.avatar.toString()).into(creepAvatar)
    }
}
