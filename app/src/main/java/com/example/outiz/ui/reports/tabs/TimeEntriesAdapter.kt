package com.example.outiz.ui.reports.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemTimeEntryBinding
import com.example.outiz.models.TimeEntry
import java.time.format.DateTimeFormatter
import java.util.Locale

class TimeEntriesAdapter(
    private val onEditClick: (TimeEntry) -> Unit = {},
    private val onDeleteClick: (TimeEntry) -> Unit = {}
) : ListAdapter<TimeEntry, TimeEntriesAdapter.ViewHolder>(TimeEntryDiffCallback()) {

    class ViewHolder(val binding: ItemTimeEntryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTimeEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = getItem(position)
        val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        holder.binding.apply {
            textViewTaskType.text = entry.taskType
            textViewDescription.text = entry.description
            textViewStartTime.text = entry.startTime.format(dateFormat)
            textViewEndTime.text = entry.endTime.format(dateFormat)
            textViewDuration.text = String.format("%d min", entry.duration)

            editButton.setOnClickListener { onEditClick(entry) }
            deleteButton.setOnClickListener { onDeleteClick(entry) }
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