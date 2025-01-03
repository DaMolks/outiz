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
import java.util.Date

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
            with(binding) {
                tvDescription.text = timeEntry.description
                tvStartTime.text = dateFormatter.format(timeEntry.startTime)
                
                val endTime = Date(timeEntry.startTime.time + timeEntry.duration * 60000L)
                tvDuration.text = "${timeEntry.duration} min"

                btnDelete.setOnClickListener { onDeleteClick(timeEntry) }
            }
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