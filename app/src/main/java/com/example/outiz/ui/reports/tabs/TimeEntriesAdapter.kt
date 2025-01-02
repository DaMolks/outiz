package com.example.outiz.ui.reports.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemTimeEntryBinding
import com.example.outiz.models.TimeEntry
import java.time.format.DateTimeFormatter

class TimeEntriesAdapter : ListAdapter<TimeEntry, TimeEntriesAdapter.TimeEntryViewHolder>(TimeEntryDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryViewHolder {
        val binding = ItemTimeEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeEntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeEntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TimeEntryViewHolder(private val binding: ItemTimeEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        fun bind(timeEntry: TimeEntry) {
            binding.textViewDescription.text = timeEntry.description
            binding.textViewStartTime.text = timeEntry.startTime.format(formatter)
            binding.textViewEndTime.text = timeEntry.startTime.plusMinutes(timeEntry.duration.toLong()).format(formatter)
            binding.textViewDuration.text = "${timeEntry.duration} min"
        }
    }

    private class TimeEntryDiffCallback : DiffUtil.ItemCallback<TimeEntry>() {
        override fun areItemsTheSame(oldItem: TimeEntry, newItem: TimeEntry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TimeEntry, newItem: TimeEntry): Boolean {
            return oldItem == newItem
        }
    }
}