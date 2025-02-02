package com.example.outiz.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemSiteBinding
import com.example.outiz.models.Site

class SitesAdapter : ListAdapter<Site, SitesAdapter.SiteViewHolder>(SiteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        return SiteViewHolder(
            ItemSiteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SiteViewHolder(private val binding: ItemSiteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(site: Site) {
            binding.apply {
                textViewSiteName.text = site.name
                textViewSiteCode.text = site.codeS
                textViewClientName.text = site.clientName
                textViewSiteAddress.text = site.address
            }
        }
    }
}

class SiteDiffCallback : DiffUtil.ItemCallback<Site>() {
    override fun areItemsTheSame(oldItem: Site, newItem: Site): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Site, newItem: Site): Boolean {
        return oldItem == newItem
    }
}
