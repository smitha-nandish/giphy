package com.smi.giphy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.smi.giphy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiphyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}