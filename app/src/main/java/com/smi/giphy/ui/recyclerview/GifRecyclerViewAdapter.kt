package com.smi.giphy.ui.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.smi.giphy.data.model.Gif
import com.smi.giphy.databinding.ItemGifBinding
import com.smi.giphy.utils.DoubleClickListener

class GifRecyclerViewAdapter(
    private val gifsList: List<Gif> = arrayListOf(),
    private val doubleClickListener: (Gif) -> Unit,
) : RecyclerView.Adapter<GifRecyclerViewAdapter.GifViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: GifViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(gifsList[position])
        holder.itemView.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View) {
                doubleClickListener(gifsList[position])
            }
        })
    }

    override fun getItemCount(): Int = gifsList.size

    class GifViewHolder(private val binding: ItemGifBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Gif) {
            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(binding.root.context).asGif()
                .load(item.images.downsized_small.url)
                .placeholder(circularProgressDrawable)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.gifImageView)

        }
    }
}

