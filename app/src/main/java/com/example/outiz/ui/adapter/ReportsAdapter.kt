package com.example.outiz.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemReportBinding
import com.example.outiz.models.Report
import java.text.SimpleDateFormat
import java.util.*

class ReportsAdapter(
    private val onReportClick: (Report) -> Unit = {},
    private val onReportEdit: (Report) -> Unit = {},
    private val onReportDelete: (Report) -> Unit = {}
) : ListAdapter<Report, ReportsAdapter.ReportViewHolder>(ReportDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding, onReportClick, onReportEdit, onReportDelete)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReportViewHolder(
        private val binding: ItemReportBinding,
        private val onReportClick: (Report) -> Unit,
        private val onReportEdit: (Report) -> Unit,
        private val onReportDelete: (Report) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(report: Report) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

            binding.apply {
                siteName.text = report.caller
                callDate.text = dateFormat.format(report.callDate)
                callReason.text = report.callReason

                root.setOnClickListener { onReportClick(report) }
                moreButton.setOnClickListener { 
                    onReportEdit(report) 
                    onReportDelete(report)
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
}