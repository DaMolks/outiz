package com.example.outiz.ui.reports.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemTimeEntryBinding
import com.example.outiz.models.TimeEntry
import java.time.format.DateTimeFormatter

class TimeEntryAdapter : ListAdapter<TimeEntry, TimeEntryAdapter.TimeEntryViewHolder>(TimeEntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryViewHolder {
        val binding = ItemTimeEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeEntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeEntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TimeEntryViewHolder(private val binding: ItemTimeEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timeEntry: TimeEntry) {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            binding.apply {
                textViewTaskType.text = timeEntry.taskType
                textViewDescription.text = timeEntry.description
                textViewStartTime.text = timeEntry.startTime.format(formatter)
                textViewEndTime.text = timeEntry.endTime.format(formatter)
                
                // Calcul et affichage de la durée
                val duration = java.time.Duration.between(timeEntry.startTime, timeEntry.endTime)
                val hours = duration.toHours()
                val minutes = duration.toMinutesPart()
                textViewDuration.text = "$hours h $minutes min"
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
}