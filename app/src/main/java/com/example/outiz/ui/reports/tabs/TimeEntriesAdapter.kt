package com.example.outiz.ui.reports.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.R
import com.example.outiz.databinding.ItemTimeEntryBinding
import com.example.outiz.models.TimeEntry
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class TimeEntriesAdapter(
    private val onDeleteClick: (TimeEntry) -> Unit
) : ListAdapter<TimeEntry, TimeEntriesAdapter.TimeEntryViewHolder>(TimeEntryDiffCallback) {

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

        private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        fun bind(timeEntry: TimeEntry) {
            with(binding) {
                // TODO: Remplacer par le nom réel du technicien
                technicianName.text = timeEntry.technicianId
                interventionDate.text = dateFormat.format(timeEntry.date)

                val hours = TimeUnit.MINUTES.toHours(timeEntry.duration)
                val minutes = timeEntry.duration - TimeUnit.HOURS.toMinutes(hours)
                duration.text = root.context.getString(R.string.time_format, hours, minutes)

                deleteButton.setOnClickListener { onDeleteClick(timeEntry) }
            }
        }
    }

    companion object TimeEntryDiffCallback : DiffUtil.ItemCallback<TimeEntry>() {
        override fun areItemsTheSame(oldItem: TimeEntry, newItem: TimeEntry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TimeEntry, newItem: TimeEntry): Boolean {
            return oldItem == newItem
        }
    }
}