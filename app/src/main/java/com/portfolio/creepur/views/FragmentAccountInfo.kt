package com.portfolio.creepur.views

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.portfolio.creepur.R
import com.portfolio.creepur.core.MainApplication
import com.portfolio.creepur.models.Data
import com.portfolio.creepur.viewmodels.HomePageViewModel
import com.portfolio.creepur.viewmodels.HomePageViewModelFactory
import kotlinx.android.synthetic.main.fragment_account_info.view.*
import java.text.SimpleDateFormat
import java.util.*


class FragmentAccountInfo : Fragment() {

    private val viewModel: HomePageViewModel by lazy { ViewModelProvider(this, HomePageViewModelFactory(MainApplication.currentUser)).get(HomePageViewModel::class.java) }

    private lateinit var name: TextView
    private lateinit var rep: TextView
    private lateinit var repName: TextView
    private lateinit var pro: TextView
    private lateinit var created: TextView
    private lateinit var avatar: ImageView
    private lateinit var cardView: CardView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_account_info, container, false)
        name = view.creepUsername
        rep = view.creepReputationPoints
        repName = view.creepReputation
        pro = view.creepProStatus
        created = view.creepDateCreated
        avatar = view.creepAvatar
        cardView = view.homepageCard
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("TAG", "Calling onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        viewModel.getUserData().observe(viewLifecycleOwner, Observer {
            updateViews(it)
        })
        if(savedInstanceState != null) {
            Log.d("TAG", "Fragment savedInstanceState not null")
            name.text = savedInstanceState.getString("NAME", "Username")
            pro.text = savedInstanceState.getString("PRO", "False")
            created.text = savedInstanceState.getString("CREATED", "null")
            rep.text = savedInstanceState.getString("REP", "null")
            repName.text = savedInstanceState.getString("REPNAME", "null")
            Glide.with(this).load(savedInstanceState.getString("AVATAR", "null")).into(avatar)
            avatar.tag = savedInstanceState.getString("AVATAR", "null")
        } else {
            Log.d("TAG", "Called getACreep")
            viewModel.getACreepInfo(MainApplication.currentUser?.userName.toString(), activity as Context)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateViews(data: Data){
        val theFormat: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        theFormat.timeZone = TimeZone.getTimeZone("EST")
        val theDate: Date = Date(data.created!! * 1000)

        created.text = theFormat.format(theDate)
        pro.text = if(data.pro_expiration!!) "True" else "False"
        name.text = data.username
        rep.text = data.reputation.toString()
        repName.text = data.reputation_name
        cardView.visibility = View.VISIBLE

        Glide.with(this).load(data.avatar.toString()).into(avatar)
        // to be able to retrieve the Uri later if creating a bookmark
        avatar.tag = data.avatar.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("NAME", name.text.toString())
        outState.putString("PRO", pro.text.toString())
        outState.putString("CREATED", created.text.toString())
        outState.putString("REP", rep.text.toString())
        outState.putString("REPNAME", repName.text.toString())
        outState.putString("AVATAR", avatar.tag.toString())
    }
}