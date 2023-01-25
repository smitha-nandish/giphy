package com.smi.giphy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.smi.giphy.R
import com.smi.giphy.databinding.FragmentFavouritesBinding
import com.smi.giphy.ui.recyclerview.GifRecyclerViewAdapter
import com.smi.giphy.ui.GifUiState
import com.smi.giphy.ui.GiphyViewModel
import kotlinx.coroutines.launch

class FavouritesGifFragment : Fragment() {

    private lateinit var giphyViewModel: GiphyViewModel

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        giphyViewModel = ViewModelProvider(requireActivity())[GiphyViewModel::class.java]

        observeGifState()

        giphyViewModel.fetchFavouriteGifs()
    }

    private fun observeGifState() {
        binding.recyclerview.layoutManager = GridLayoutManager(this@FavouritesGifFragment.context, 2)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                giphyViewModel.gifSavedUiState.collect { uiState ->
                    when (uiState) {
                        is GifUiState.Success -> {
                            val listOfGifs = uiState.success
                            binding.recyclerview.adapter =
                                this@FavouritesGifFragment.activity?.let {
                                    GifRecyclerViewAdapter(listOfGifs) { gif ->
                                        giphyViewModel.removeGifFromFavourite(gif)
                                        Toast.makeText(
                                            this@FavouritesGifFragment.context,
                                            getString(R.string.text_removed),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                        is GifUiState.Failed -> {
                            val error = uiState.error
                            Toast.makeText(
                                this@FavouritesGifFragment.context,
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}