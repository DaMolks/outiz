package com.example.outiz.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.outiz.databinding.DialogAddTimeEntryBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.viewmodel.ReportViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class AddTimeEntryDialog : DialogFragment() {
    private var binding: DialogAddTimeEntryBinding? = null
    private lateinit var viewModel: ReportViewModel
    private var reportId: Long = 0
    private var timeEntry: TimeEntry? = null // Pour la modification d'une entrée existante

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ReportViewModel::class.java]
        reportId = arguments?.getLong("reportId") ?: 0
        timeEntry = arguments?.getParcelable("timeEntry")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddTimeEntryBinding.inflate(layoutInflater)
        
        setupTimePickers()
        setupOwnerSwitch()
        timeEntry?.let { populateFields(it) }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (timeEntry == null) "Nouvelle entrée de temps" else "Modifier l'entrée de temps")
            .setView(binding?.root)
            .setPositiveButton(if (timeEntry == null) "Ajouter" else "Modifier") { _, _ ->
                saveTimeEntry()
            }
            .setNegativeButton("Annuler", null)
            .create()
    }
