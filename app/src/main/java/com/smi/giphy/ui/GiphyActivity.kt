package com.smi.giphy.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.smi.giphy.R
import com.smi.giphy.databinding.ActivityMainBinding
import com.smi.giphy.ui.fragments.FavouritesGifFragment
import com.smi.giphy.ui.fragments.TrendingGifsFragment
import com.smi.giphy.ui.viewpager.GifsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiphyActivity : FragmentActivity() {
    private lateinit var giphyViewModel: GiphyViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialiseGifsViewPager()
    }

    private fun initialiseGifsViewPager() {
        val gifsPagerAdapter = GifsPagerAdapter(this)
        gifsPagerAdapter.addFragment(TrendingGifsFragment(), getString(R.string.fragment_trending))
        gifsPagerAdapter.addFragment(FavouritesGifFragment(), getString(R.string.fragment_favourites))

        binding.pager.adapter = gifsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = gifsPagerAdapter.getPageTitle(position)
        }.attach()
    }

    override fun onBackPressed() {
        if (binding.pager.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.pager.currentItem = binding.pager.currentItem - 1
        }
    }
}