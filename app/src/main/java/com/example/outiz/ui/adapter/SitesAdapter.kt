package com.example.outiz.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemSiteBinding
import com.example.outiz.models.Site

class SitesAdapter(
    private val onSiteClick: (Site) -> Unit = {},
    private val onSiteEdit: (Site) -> Unit = {},
    private val onSiteDelete: (Site) -> Unit = {}
) : ListAdapter<Site, SitesAdapter.SiteViewHolder>(SiteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        val binding = ItemSiteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SiteViewHolder(binding, onSiteClick, onSiteEdit, onSiteDelete)
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SiteViewHolder(
        private val binding: ItemSiteBinding,
        private val onSiteClick: (Site) -> Unit,
        private val onSiteEdit: (Site) -> Unit,
        private val onSiteDelete: (Site) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(site: Site) {
            binding.apply {
                siteName.text = site.name
                siteCode.text = "Code S: ${site.codeS}"
                clientName.text = "Client: ${site.clientName}"
                siteAddress.text = site.address

                root.setOnClickListener { onSiteClick(site) }
                moreButton.setOnClickListener {
                    onSiteEdit(site)
                    onSiteDelete(site) 
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
}