package com.example.outiz.ui.sites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.R
import com.example.outiz.databinding.ItemSiteBinding
import com.example.outiz.models.Site

class SitesAdapter(
    private val onSiteClick: (Site) -> Unit,
    private val onSiteEdit: (Site) -> Unit,
    private val onSiteDelete: (Site) -> Unit
) : ListAdapter<Site, SitesAdapter.SiteViewHolder>(SiteDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        val binding = ItemSiteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SiteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SiteViewHolder(private val binding: ItemSiteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(site: Site) {
            with(binding) {
                siteName.text = site.name
                siteCode.text = root.context.getString(R.string.site_code_format, site.codeS)
                clientName.text = root.context.getString(R.string.site_client_format, site.clientName)
                siteAddress.text = site.address

                root.setOnClickListener { onSiteClick(site) }

                moreButton.setOnClickListener { showPopupMenu(it, site) }
            }
        }

        private fun showPopupMenu(view: View, site: Site) {
            PopupMenu(view.context, view).apply {
                menuInflater.inflate(R.menu.menu_site_item, menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_edit -> {
                            onSiteEdit(site)
                            true
                        }
                        R.id.action_delete -> {
                            onSiteDelete(site)
                            true
                        }
                        else -> false
                    }
                }
                show()
            }
        }
    }

    companion object SiteDiffCallback : DiffUtil.ItemCallback<Site>() {
        override fun areItemsTheSame(oldItem: Site, newItem: Site): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Site, newItem: Site): Boolean {
            return oldItem == newItem
        }
    }
}