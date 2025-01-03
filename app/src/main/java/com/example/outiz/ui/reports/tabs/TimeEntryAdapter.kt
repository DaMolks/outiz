package com.example.outiz.ui.reports.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemTimeEntryBinding
import com.example.outiz.models.TimeEntry
import java.text.SimpleDateFormat
import java.util.Locale

class TimeEntryAdapter(
    private val onDeleteClick: (TimeEntry) -> Unit
) : ListAdapter<TimeEntry, TimeEntryAdapter.TimeEntryViewHolder>(TimeEntryDiffCallback()) {

    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryViewHolder {
        val binding = ItemTimeEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeEntryViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: TimeEntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TimeEntryViewHolder(
        private val binding: ItemTimeEntryBinding,
        private val onDeleteClick: (TimeEntry) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(timeEntry: TimeEntry) {
            binding.apply {
                tvDescription.text = timeEntry.description
                tvStartTime.text = formatStartTime(timeEntry)
                tvDuration.text = formatDuration(timeEntry.duration)

                btnDelete.setOnClickListener { onDeleteClick(timeEntry) }
            }
        }

        private fun formatStartTime(timeEntry: TimeEntry): String {
            return timeFormatter.format(timeEntry.startTime)
        }

        private fun formatDuration(duration: Int): String {
            return String.format(Locale.getDefault(), "%d min", duration)
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