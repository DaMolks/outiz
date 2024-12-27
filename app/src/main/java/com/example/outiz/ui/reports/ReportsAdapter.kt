package com.example.outiz.ui.reports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.R
import com.example.outiz.databinding.ItemReportBinding
import com.example.outiz.models.ReportWithDetails
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class ReportsAdapter(
    private val onReportClick: (ReportWithDetails) -> Unit,
    private val onReportEdit: (ReportWithDetails) -> Unit,
    private val onReportDelete: (ReportWithDetails) -> Unit
) : ListAdapter<ReportWithDetails, ReportsAdapter.ReportViewHolder>(ReportDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReportViewHolder(private val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        fun bind(report: ReportWithDetails) {
            with(binding) {
                siteName.text = report.site.name
                callDate.text = dateFormat.format(report.report.callDate)
                callReason.text = report.report.callReason
                caller.text = root.context.getString(R.string.caller_format, report.report.caller)

                // Configuration des chips
                if (report.report.isTimeTrackingEnabled) {
                    timeChip.visibility = View.VISIBLE
                    val totalMinutes = report.timeEntries.sumOf { it.duration.toLong() }
                    val hours = TimeUnit.MINUTES.toHours(totalMinutes)
                    val minutes = totalMinutes - TimeUnit.HOURS.toMinutes(hours)
                    timeChip.text = root.context.getString(R.string.time_format, hours, minutes)
                } else {
                    timeChip.visibility = View.GONE
                }

                if (report.report.isPhotosEnabled) {
                    photosChip.visibility = View.VISIBLE
                    val photoCount = report.report.photosPaths?.size ?: 0
                    photosChip.text = root.context.getString(R.string.photos_count, photoCount)
                } else {
                    photosChip.visibility = View.GONE
                }

                signatureChip.visibility = if (report.report.hasSignature) View.VISIBLE else View.GONE

                root.setOnClickListener { onReportClick(report) }
                moreButton.setOnClickListener { showPopupMenu(it, report) }
            }
        }

        private fun showPopupMenu(view: View, report: ReportWithDetails) {
            PopupMenu(view.context, view).apply {
                menuInflater.inflate(R.menu.menu_report_item, menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_edit -> {
                            onReportEdit(report)
                            true
                        }
                        R.id.action_delete -> {
                            onReportDelete(report)
                            true
                        }
                        else -> false
                    }
                }
                show()
            }
        }
    }

    companion object ReportDiffCallback : DiffUtil.ItemCallback<ReportWithDetails>() {
        override fun areItemsTheSame(oldItem: ReportWithDetails, newItem: ReportWithDetails): Boolean {
            return oldItem.report.id == newItem.report.id
        }

        override fun areContentsTheSame(oldItem: ReportWithDetails, newItem: ReportWithDetails): Boolean {
            return oldItem == newItem
        }
    }
}