package com.portfolio.creepur.views

import android.annotation.SuppressLint
import android.graphics.Outline
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.portfolio.creepur.R
import com.portfolio.creepur.models.Data
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.viewmodels.HomePageViewModel
import com.portfolio.creepur.viewmodels.HomePageViewModelFactory
import com.portfolio.creepur.viewmodels.listeners.AnimationPromptListener
import com.portfolio.creepur.viewmodels.listeners.GestureDetector
import com.portfolio.creepur.viewmodels.listeners.NavigationItemSelected
import kotlinx.android.synthetic.main.activity_home_page.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class HomePage : AppCompatActivity(), Serializable{

    private val viewModel: HomePageViewModel by lazy {
        val user: UserAccountSignedIn? = intent.getSerializableExtra("ACCOUNT") as UserAccountSignedIn?
        ViewModelProvider(this, HomePageViewModelFactory(user)).get(HomePageViewModel::class.java) }

    //private val listOfViewsStandard: List<View> by lazy { listOf(creepText, creepProStatus, creepDateCreated,
      //  creepText2, creepReputation, creepReputationPoints, fab, spinner, creepSpinnerOptionsText) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // init
        initViews()


        supportFragmentManager.beginTransaction().replace(R.id.fragmentSpace, HomepageFragmentAccountInfo()).commit()





        // setting LiveData and how to update
        viewModel.getUserInfo().observe(this, Observer<Data> {
            Log.d("TAG", "Observed. Updating view")
            //updateViews(it)
            viewModel.setActiveCreepData(it)
        })

        // if user signed in -> loads their info
        if (savedInstanceState != null) {
            viewModel.getACreepInfo(intent.getStringExtra("USERNAME")!!, this)
        }

        // onClick listeners
        /* fab.setOnClickListener { view -> viewModel.addCreepToFirebase(
                Bookmark(creepUsername.text.toString(), creepReputationPoints.text.toString().toInt(),
                    creepReputation.text.toString(), viewModel.getActiveCreepData()?.avatar.toString(),
                    creepProStatus.text.toString().toBoolean()
                )
            )
            Snackbar.make(view, "Bookmark added", Snackbar.LENGTH_SHORT).show()
        }
        */

        CreepEmButton.setOnClickListener { viewModel.makeAppropriateCall(spinner.selectedItemPosition, creepInputUser.text.toString(), this) }

        bottomNavigationView.setOnNavigationItemSelectedListener(NavigationItemSelected(this,
                intent.getSerializableExtra("ACCOUNT") as UserAccountSignedIn?))

        constraintLayoutBottom.setOnTouchListener(GestureDetector(AnimationPromptListener()))
    }


   /* @SuppressLint("SimpleDateFormat")
    private fun updateViews(data: Data){
        val theFormat: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        theFormat.timeZone = TimeZone.getTimeZone("EST")
        val theDate: Date = Date(data.created!! * 1000)

        creepDateCreated.text = theFormat.format(theDate)
        creepProStatus.text = if(data.pro_expiration!!) "True" else "False"
        creepUsername.text = data.username
        creepReputationPoints.text = data.reputation.toString()
        creepReputation.text = data.reputation_name

        Glide.with(this).load(data.avatar.toString()).into(creepAvatar)
    }
    */

    private fun initViews(){
        constraintLayoutBottom.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, (view.height+15), 25F)
            }
        }
        constraintLayoutBottom.clipToOutline = true
        bottomNavigationView.menu.getItem(1).isChecked = true
        val backgroundAnimation: AnimationDrawable = homePageConstraint.background as AnimationDrawable
        backgroundAnimation.setEnterFadeDuration(4000)
        backgroundAnimation.setExitFadeDuration(4000)
        backgroundAnimation.start()
        ArrayAdapter.createFromResource(this, R.array.imgurApiOptionsArray, android.R.layout.simple_spinner_item).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter }
        spinner.setSelection(1)
    }

   // private fun toggleViewVisibility(){
     //  listOfViewsStandard.forEach { view -> view.visibility = if(view.visibility == View.VISIBLE) View.GONE else View.VISIBLE } // add fade out/in animation (might have to separate two packs of views due to animation purposes
    //}
}




