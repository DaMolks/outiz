package com.example.outiz.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemTimeEntryBinding
import com.example.outiz.models.TimeEntry
import java.time.format.DateTimeFormatter

class TimeEntryAdapter(
    private var timeEntries: List<TimeEntry> = emptyList(),
    private val onEditClick: (TimeEntry) -> Unit,
    private val onDeleteClick: (TimeEntry) -> Unit
) : RecyclerView.Adapter<TimeEntryAdapter.TimeEntryViewHolder>() {

    class TimeEntryViewHolder(val binding: ItemTimeEntryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryViewHolder {
        val binding = ItemTimeEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TimeEntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeEntryViewHolder, position: Int) {
        val timeEntry = timeEntries[position]
        val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        holder.binding.apply {
            textViewTaskType.text = timeEntry.taskType
            textViewDescription.text = timeEntry.description
            textViewStartTime.text = timeEntry.startTime.format(dateFormat)
            textViewEndTime.text = timeEntry.endTime.format(dateFormat)
            textViewDuration.text = String.format("%d min", timeEntry.duration)

            editButton.setOnClickListener { onEditClick(timeEntry) }
            deleteButton.setOnClickListener { onDeleteClick(timeEntry) }
        }
    }

    override fun getItemCount() = timeEntries.size

    fun updateEntries(newEntries: List<TimeEntry>) {
        timeEntries = newEntries
        notifyDataSetChanged()
    }
}