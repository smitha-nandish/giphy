package com.smi.giphy.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class GifsPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val fragmentsList = arrayListOf<Fragment>()
    private val fragmentsTitleList = arrayListOf<String>()

    override fun getItemCount(): Int  {
        return fragmentsList.size
    }

    override fun createFragment(position: Int): Fragment = fragmentsList[position]

    fun getPageTitle(position: Int): String = fragmentsTitleList[position]

    fun addFragment(fragment: Fragment, title: String) {
        fragmentsList.add(fragment)
        fragmentsTitleList.add(title)
    }
}