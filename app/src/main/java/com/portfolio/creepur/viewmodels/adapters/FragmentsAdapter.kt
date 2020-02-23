package com.portfolio.creepur.viewmodels.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentsAdapter(private val fragmentManager: FragmentManager, private val intt: Int) : FragmentStatePagerAdapter(fragmentManager, intt) {

    private var allTheFragments: HashMap<String, Fragment> = hashMapOf()

    fun addFragment(fragName: String, frag: Fragment){
        allTheFragments[fragName] = frag
    }

    override fun getItem(position: Int): Fragment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}