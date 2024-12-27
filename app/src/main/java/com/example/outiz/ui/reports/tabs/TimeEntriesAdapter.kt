package com.example.outiz.ui.reports.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemTimeEntryBinding
import com.example.outiz.models.TimeEntry
import java.text.SimpleDateFormat
import java.util.*

class TimeEntriesAdapter(
    private var entries: List<TimeEntry> = emptyList(),
    private val onEditClick: (TimeEntry) -> Unit = {},
    private val onDeleteClick: (TimeEntry) -> Unit = {}
) : RecyclerView.Adapter<TimeEntriesAdapter.ViewHolder>() {

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
        val entry = entries[position]
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        holder.binding.apply {
            timeInfoText.text = dateFormat.format(entry.date)
            durationText.text = String.format("%d min", entry.duration)

            editButton.setOnClickListener { onEditClick(entry) }
            deleteButton.setOnClickListener { onDeleteClick(entry) }
        }
    }

    override fun getItemCount() = entries.size

    fun updateEntries(newEntries: List<TimeEntry>) {
        entries = newEntries
        notifyDataSetChanged()
    }
}