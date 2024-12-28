package com.example.outiz.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemReportBinding
import com.example.outiz.models.ReportWithDetails

class ReportsAdapter(
    private val reports: List<ReportWithDetails>,
    private val onReportClick: (ReportWithDetails) -> Unit
) : RecyclerView.Adapter<ReportsAdapter.ReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding, onReportClick)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(reports[position])
    }

    override fun getItemCount() = reports.size

    class ReportViewHolder(
        private val binding: ItemReportBinding,
        private val onReportClick: (ReportWithDetails) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reportWithDetails: ReportWithDetails) {
            val report = reportWithDetails.report
            binding.apply {
                tvReportSite.text = report.siteId.toString()
                tvReportDate.text = report.date.toString()
                tvReportDescription.text = report.description

                root.setOnClickListener { onReportClick(reportWithDetails) }
            }
        }
    }
}