package com.example.outiz.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemTimeEntryBinding
import com.example.outiz.models.TimeEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimeEntryAdapter(
    private val onDeleteClick: (TimeEntry) -> Unit
) : ListAdapter<TimeEntry, TimeEntryAdapter.TimeEntryViewHolder>(TimeEntryDiffCallback()) {

    private val dateFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

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
            with(binding) {
                tvDescription.text = timeEntry.description
                tvStartTime.text = dateFormatter.format(timeEntry.startTime)
                
                val endTime = Date(timeEntry.startTime.time + timeEntry.duration * 60000L)
                tvDuration.text = String.format(Locale.getDefault(), "%d min", timeEntry.duration)

                btnDelete.setOnClickListener { onDeleteClick(timeEntry) }
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