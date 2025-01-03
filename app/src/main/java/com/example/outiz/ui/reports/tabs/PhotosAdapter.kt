package com.example.outiz.ui.reports.tabs

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.outiz.databinding.ItemPhotoBinding

class PhotosAdapter(
    private val photos: List<String>,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    inner class PhotoViewHolder(
        private val binding: ItemPhotoBinding,
        private val onDeleteClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photoPath: String) {
            Glide.with(binding.root.context)
                .load(Uri.parse(photoPath))
                .into(binding.imageView)

            binding.deleteButton.setOnClickListener { onDeleteClick(photoPath) }
        }
    }
}