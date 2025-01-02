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

class TimeEntriesAdapter(
    private val onDeleteClick: (TimeEntry) -> Unit = {},
    private val onEditClick: (TimeEntry) -> Unit = {}
) : ListAdapter<TimeEntry, TimeEntriesAdapter.TimeEntryViewHolder>(TimeEntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryViewHolder {
        val binding = ItemTimeEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeEntryViewHolder(binding, onDeleteClick, onEditClick)
    }

    override fun onBindViewHolder(holder: TimeEntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TimeEntryViewHolder(
        private val binding: ItemTimeEntryBinding,
        private val onDeleteClick: (TimeEntry) -> Unit,
        private val onEditClick: (TimeEntry) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        fun bind(timeEntry: TimeEntry) {
            binding.textViewDescription.text = timeEntry.description
            binding.textViewStartTime.text = dateFormatter.format(timeEntry.startTime)
            
            val endTime = java.util.Date(timeEntry.startTime.time + timeEntry.duration * 60000L)
            binding.textViewEndTime.text = dateFormatter.format(endTime)
            binding.textViewDuration.text = "${timeEntry.duration} min"

            binding.deleteButton.setOnClickListener { onDeleteClick(timeEntry) }
            binding.editButton.setOnClickListener { onEditClick(timeEntry) }
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