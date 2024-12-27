package com.example.outiz.ui.reports.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemTimeEntryBinding
import com.example.outiz.models.TimeEntry

class TimeEntryAdapter : ListAdapter<TimeEntry, TimeEntryAdapter.TimeEntryViewHolder>(TimeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryViewHolder {
        return TimeEntryViewHolder(
            ItemTimeEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TimeEntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TimeEntryViewHolder(private val binding: ItemTimeEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(timeEntry: TimeEntry) {
            binding.apply {
                // TODO: Implement binding
            }
        }
    }
}

class TimeDiffCallback : DiffUtil.ItemCallback<TimeEntry>() {
    override fun areItemsTheSame(oldItem: TimeEntry, newItem: TimeEntry): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TimeEntry, newItem: TimeEntry): Boolean {
        return oldItem == newItem
    }
}