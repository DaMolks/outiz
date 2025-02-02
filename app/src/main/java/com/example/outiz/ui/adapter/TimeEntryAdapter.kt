package com.example.outiz.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemTimeEntryBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.utils.formatDateTime

class TimeEntryAdapter(
    private val onDeleteClick: (TimeEntry) -> Unit
) : ListAdapter<TimeEntry, TimeEntryAdapter.TimeEntryViewHolder>(TimeEntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryViewHolder {
        val binding = ItemTimeEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TimeEntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeEntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TimeEntryViewHolder(private val binding: ItemTimeEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(timeEntry: TimeEntry) {
            binding.apply {
                tvStartTime.text = timeEntry.startTime.formatDateTime()
                tvDuration.text = "${timeEntry.duration} min"
                tvDescription.text = timeEntry.description

                btnDelete.setOnClickListener {
                    onDeleteClick(timeEntry)
                }
            }
        }
    }
}

class TimeEntryDiffCallback : DiffUtil.ItemCallback<TimeEntry>() {
    override fun areItemsTheSame(oldItem: TimeEntry, newItem: TimeEntry): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TimeEntry, newItem: TimeEntry): Boolean {
        return oldItem == newItem
    }
}