package com.example.outiz.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemReportBinding
import com.example.outiz.models.Report
import java.time.format.DateTimeFormatter

class ReportsAdapter(private val onReportClick: (Report) -> Unit) : 
    ListAdapter<Report, ReportsAdapter.ReportViewHolder>(ReportDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding, onReportClick)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReportViewHolder(
        private val binding: ItemReportBinding, 
        private val onReportClick: (Report) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(report: Report) {
            with(binding) {
                tvSiteName.text = report.siteName
                tvReportDate.text = report.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                tvDescription.text = report.description
                root.setOnClickListener { onReportClick(report) }
            }
        }
    }
}

class ReportDiffCallback : DiffUtil.ItemCallback<Report>() {
    override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean {
        return oldItem == newItem
    }
}