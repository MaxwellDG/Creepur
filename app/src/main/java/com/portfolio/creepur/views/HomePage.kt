package com.portfolio.creepur.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Outline
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.portfolio.creepur.R
import com.portfolio.creepur.core.MainApplication
import com.portfolio.creepur.models.Bookmark
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.viewmodels.HomePageViewModel
import com.portfolio.creepur.viewmodels.HomePageViewModelFactory
import com.portfolio.creepur.viewmodels.listeners.GestureDetector
import com.portfolio.creepur.viewmodels.listeners.NavigationItemSelected
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.fragment_account_info.*
import java.io.Serializable


class HomePage : AppCompatActivity(), Serializable, GestureDetector.ListenerForSwipe{

    private val viewModel: HomePageViewModel by lazy {
        val user: UserAccountSignedIn? = intent.getSerializableExtra("ACCOUNT") as UserAccountSignedIn?
        ViewModelProvider(this, HomePageViewModelFactory(user)).get(HomePageViewModel::class.java) }

    private val fragManager = supportFragmentManager
    private val fragmentAccountInfo: FragmentAccountInfo by lazy { FragmentAccountInfo() }
    private val fragmentAccountImages: FragmentAccountImages by lazy { FragmentAccountImages() }
    private val fragmentAccountFavorites: FragmentAccountFavorites by lazy { FragmentAccountFavorites() }
    private val fragmentAccountComments: FragmentAccountComments by lazy { FragmentAccountComments() }

    private val listener: GestureDetector.ListenerForSwipe by lazy { this }

    private val startingHeight: Int by lazy { constraintLayoutBottom.height }
    private val viewHeights: Int by lazy { creepInputUser.height + CreepEmButton.height }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // init
        initViews()

        // onClick listeners
        fab.setOnClickListener { view -> viewModel.addCreepToFirebase(
                Bookmark(creepUsername.text.toString(), creepReputationPoints.text.toString().toInt(),
                    creepReputation.text.toString(), creepAvatar.tag.toString(),
                    creepProStatus.text.toString().toBoolean()
                )
            )
            if(MainApplication.currentUser != null) {
                Snackbar.make(view, "Bookmark added", Snackbar.LENGTH_SHORT).show()
            } else{
                Snackbar.make(view, "Log in to create bookmarks.", Snackbar.LENGTH_LONG).show()
            }
        }

        CreepEmButton.setOnClickListener { makeAppropriateCall(spinner.selectedItemPosition, creepInputUser.text.toString()) }

        bottomNavigationView.setOnNavigationItemSelectedListener(NavigationItemSelected(constraintLayoutBottom,this,
                intent?.getSerializableExtra("ACCOUNT") as UserAccountSignedIn?))

        homePageConstraint.setOnTouchListener(GestureDetector(listener))
    }

    private fun initViews(){
        // Initial fragment display
        if(fragManager.fragments.size == 0) {
            fragManager.beginTransaction().add(R.id.fragmentSpace, fragmentAccountInfo, "AccountInfo")
                .addToBackStack(null)
                .commit()
        }
        // Rounding edges of white container at bottom
        constraintLayoutBottom.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, (view.height+15), 25F)
            }
        }
        constraintLayoutBottom.clipToOutline = true
        // Setting proper colour for NavBar
        bottomNavigationView.menu.getItem(1).isChecked = true
        // Background animation start
        val backgroundAnimation: AnimationDrawable = homePageConstraint.background as AnimationDrawable
        backgroundAnimation.setEnterFadeDuration(4000)
        backgroundAnimation.setExitFadeDuration(4000)
        backgroundAnimation.start()
        // Initializing spinner
        ArrayAdapter.createFromResource(this, R.array.imgurApiOptionsArray, android.R.layout.simple_spinner_item).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter }
        spinner.setSelection(1)
    }

    private fun makeAppropriateCall(spinnerPosition: Int, username: String){
        val currentFrag: Fragment = fragManager.fragments.last()
        // Each case changes the fragment if it's currently un-active, and/or makes the appropriate call
        when(spinnerPosition) {
            0, 3 -> Toast.makeText(this, "Please select a creeping option.", Toast.LENGTH_LONG)
                .show()
            1 -> { if (currentFrag.tag != fragmentAccountInfo.tag) {
                    fragManager.beginTransaction()
                        .replace(R.id.fragmentSpace, fragmentAccountInfo, "AccountInfo")
                        .addToBackStack(null)
                        .commit()
                }
                viewModel.getACreepInfo(username, this)
                animateFab(true)
            }
            2 -> { if (currentFrag.tag != fragmentAccountImages.tag) {
                    fragManager.beginTransaction()
                        .replace(R.id.fragmentSpace, fragmentAccountImages, "AccountImages")
                        .addToBackStack(null)
                        .commit()
                }
                viewModel.getACreepImages(username, this)
                animateFab(false)
            }
            4 -> { if (currentFrag.tag != fragmentAccountFavorites.tag) {
                    fragManager.beginTransaction()
                        .replace(R.id.fragmentSpace, fragmentAccountFavorites, "AccountFavorites")
                        .addToBackStack(null)
                        .commit()
                }
                viewModel.getACreepFavorites(username, this)
                animateFab(false)
            }
            5 -> { if (currentFrag.tag != fragmentAccountComments.tag) {
                    fragManager.beginTransaction()
                        .replace(R.id.fragmentSpace, fragmentAccountComments, "AccountComments")
                        .addToBackStack(null)
                        .commit()
                }
                viewModel.getACreepComments(username, this)
                animateFab(false)
            }
            else ->  Log.d("Tag", "You missed")
        }
        animateDown()
    }


    override fun changeUI(direction: Int) {
        // several animations for the bottom bar going up and down on swipe
        val deltaY = if(direction == GestureDetector.ListenerForSwipe.DIRECTION_UP) this.startingHeight + viewHeights else this.startingHeight
        val anim: ValueAnimator = ValueAnimator.ofInt(constraintLayoutBottom.height, deltaY)
                            anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = constraintLayoutBottom.layoutParams
            layoutParams.height = value
            constraintLayoutBottom.layoutParams = layoutParams
        }
        anim.duration = 1000L

        val rotateNum = if(creepScroll.rotation == 180F) 0F else 180F
        val objAnim = ObjectAnimator.ofFloat(creepScroll, View.ROTATION, rotateNum)
        objAnim.duration = 1000L

        val visibility1 = ObjectAnimator.ofInt(creepSpinnerOptionsText, "visibility", if(creepSpinnerOptionsText.visibility == View.VISIBLE) View.GONE else View.VISIBLE)
        visibility1.duration = 100L
        val visibility2 = ObjectAnimator.ofInt(spinner, "visibility", if(spinner.visibility == View.VISIBLE) View.GONE else View.VISIBLE)
        visibility2.duration = 100L
        val fadeIn1 = ObjectAnimator.ofFloat(creepSpinnerOptionsText, View.ALPHA, if(creepSpinnerOptionsText.alpha == 0.0f) 1.0f else 0.0f)
        fadeIn1.duration = 500L
        val fadeIn2 = ObjectAnimator.ofFloat(spinner, View.ALPHA, if(creepSpinnerOptionsText.alpha == 0.0f) 1.0f else 0.0f)
        fadeIn2.duration = 500L

        val animFirstSet: AnimatorSet = AnimatorSet()
        animFirstSet.playTogether(anim, objAnim)
        val animSecondSet: AnimatorSet = AnimatorSet()
        animSecondSet.playTogether(visibility1, visibility2)
        val animThirdSet: AnimatorSet = AnimatorSet()
        animThirdSet.playTogether(fadeIn1, fadeIn2)

        val animSet: AnimatorSet = AnimatorSet()
        animSet.playSequentially(animFirstSet, animSecondSet, animThirdSet)
        animSet.start()
    }

    private fun animateDown(){
        // shrink the UI after a selection
        if(constraintLayoutBottom.height > startingHeight) {
            changeUI(GestureDetector.ListenerForSwipe.DIRECTION_DOWN)
        }
    }

    private fun animateFab(onAccountInfo: Boolean){
        // toggle appropriate state of fab (only available on fragmentAccountInfo)
        if(onAccountInfo){
            fab.alpha = 1.0F
            fab.isClickable = true
        } else {
            fab.alpha = .25F
            fab.isClickable = false
        }
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("WHAT", "what")
        super.onSaveInstanceState(outState)
        outState.putString("Wut", "wut")
        supportFragmentManager.putFragment(outState, "FRAGMENT", supportFragmentManager.fragments[0])
        Log.d("TAG", "Saved: ${supportFragmentManager.fragments[0].tag.toString()}")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // retrieve previous active fragment
        Log.d("TAG", "Fragment retrieved from savedInstanceState")
        val workingFrag: Fragment? = supportFragmentManager.getFragment(savedInstanceState, "FRAGMENT")
        if(workingFrag != null) {
            Log.d("TAG", "Working frag: ${workingFrag.tag}")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentSpace, workingFrag, workingFrag.tag)
        }
    }

     */
}




