package com.example.outiz.ui.reports.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemPhotoBinding

class PhotosAdapter(
    private val onDeleteClick: (String) -> Unit
) : ListAdapter<String, PhotosAdapter.PhotoViewHolder>(PhotoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photoPath: String) {
            with(binding) {
                photoImage.setImageURI(android.net.Uri.parse("file://$photoPath"))
                deleteButton.setOnClickListener { onDeleteClick(photoPath) }
            }
        }
    }

    companion object PhotoDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}