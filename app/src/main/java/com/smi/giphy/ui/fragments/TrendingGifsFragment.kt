package com.smi.giphy.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.smi.giphy.R
import com.smi.giphy.databinding.FragmentTrendingBinding
import com.smi.giphy.ui.recyclerview.GifRecyclerViewAdapter
import com.smi.giphy.ui.GifUiState
import com.smi.giphy.ui.GiphyViewModel
import kotlinx.coroutines.launch

class TrendingGifsFragment : Fragment() {

    private lateinit var giphyViewModel: GiphyViewModel

    private var _binding: FragmentTrendingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        giphyViewModel = ViewModelProvider(requireActivity())[GiphyViewModel::class.java]

        initSearchBar()
        observeGifUiState()

        giphyViewModel.fetchTrendingGifs()
    }

    private fun observeGifUiState() {
        binding.recyclerview.layoutManager = LinearLayoutManager(this@TrendingGifsFragment.context)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                giphyViewModel.gifUiState.collect { uiState ->
                    when (uiState) {
                        is GifUiState.Success -> {
                            val listOfGifs = uiState.success
                            binding.recyclerview.adapter = this@TrendingGifsFragment.activity?.let {
                                GifRecyclerViewAdapter(listOfGifs)
                                { gif ->
                                    giphyViewModel.saveGifToFavourite(gif)
                                    Toast.makeText(
                                        this@TrendingGifsFragment.context,
                                        getString(R.string.text_saved),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        is GifUiState.Failed -> {
                            val error = uiState.error
                            Toast.makeText(
                                this@TrendingGifsFragment.context,
                                "Failed with $error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is GifUiState.Loading, is GifUiState.Empty -> {}
                    }
                }
            }
        }
    }

    private fun initSearchBar() {
        binding.searchBar.isActivated = true
        binding.searchBar.isIconified = false
        binding.searchBar.clearFocus()

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { giphyViewModel.searchFor(it) }
                return false
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}