package com.example.outiz.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.outiz.databinding.FragmentTechnicianProfileBinding
import com.google.android.material.snackbar.Snackbar

class TechnicianProfileFragment : Fragment() {
    private var _binding: FragmentTechnicianProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TechnicianProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTechnicianProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TechnicianProfileViewModel::class.java]

        setupSaveButton()
        observeViewModel()
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            val firstName = binding.firstNameInput.text.toString()
            val lastName = binding.lastNameInput.text.toString()
            val sector = binding.sectorInput.text.toString()

            if (validateInputs(firstName, lastName, sector)) {
                viewModel.saveTechnician(firstName, lastName, sector)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.saveSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().navigate(
                    TechnicianProfileFragmentDirections.actionTechnicianProfileFragmentToHomeFragment()
                )
            } else {
                Snackbar.make(
                    binding.root,
                    "Erreur lors de la création du profil",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun validateInputs(firstName: String, lastName: String, sector: String): Boolean {
        var isValid = true

        if (firstName.isBlank()) {
            binding.firstNameLayout.error = "Le prénom est requis"
            isValid = false
        } else {
            binding.firstNameLayout.error = null
        }

        if (lastName.isBlank()) {
            binding.lastNameLayout.error = "Le nom est requis"
            isValid = false
        } else {
            binding.lastNameLayout.error = null
        }

        if (sector.isBlank()) {
            binding.sectorLayout.error = "Le secteur est requis"
            isValid = false
        } else {
            binding.sectorLayout.error = null
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}