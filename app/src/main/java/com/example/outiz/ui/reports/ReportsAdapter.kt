package com.example.outiz.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemReportBinding
import com.example.outiz.models.Report
import java.text.SimpleDateFormat
import java.util.Locale

class ReportsAdapter(
    private val onReportClick: (Report) -> Unit
) : ListAdapter<Report, ReportsAdapter.ReportViewHolder>(ReportDiffCallback()) {

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding, onReportClick, dateFormatter)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReportViewHolder(
        private val binding: ItemReportBinding,
        private val onReportClick: (Report) -> Unit,
        private val dateFormatter: SimpleDateFormat
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(report: Report) {
            with(binding) {
                tvReportSite.text = report.siteName
                tvReportDate.text = dateFormatter.format(report.date)
                tvReportDescription.text = report.description
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