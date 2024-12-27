package com.example.outiz.ui.sites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.R
import com.example.outiz.databinding.ItemSiteBinding
import com.example.outiz.models.Site

class SitesAdapter(
    private val onEditClick: (Site) -> Unit,
    private val onDeleteClick: (Site) -> Unit
) : ListAdapter<Site, SitesAdapter.SiteViewHolder>(SiteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        return SiteViewHolder(
            ItemSiteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onEditClick,
            onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SiteViewHolder(
        private val binding: ItemSiteBinding,
        private val onEditClick: (Site) -> Unit,
        private val onDeleteClick: (Site) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(site: Site) {
            binding.apply {
                textViewSiteName.text = site.name
                textViewSiteCode.text = site.codeS
                textViewClientName.text = site.clientName
                textViewSiteAddress.text = site.address

                moreButton.setOnClickListener { view ->
                    PopupMenu(view.context, view).apply {
                        inflate(R.menu.menu_site_item)
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.action_edit -> {
                                    onEditClick(site)
                                    true
                                }
                                R.id.action_delete -> {
                                    onDeleteClick(site)
                                    true
                                }
                                else -> false
                            }
                        }
                        show()
                    }
                }
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