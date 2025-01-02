package com.example.outiz.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.outiz.R
import com.example.outiz.databinding.FragmentTechnicianProfileBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TechnicianProfileFragment : Fragment() {
    private var _binding: FragmentTechnicianProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TechnicianProfileViewModel by viewModels()

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
        
        setupInputListeners()
        setupButtonListeners()
        observeViewModel()
    }

    private fun setupInputListeners() {
        binding.firstNameInput.doAfterTextChanged {
            viewModel.updateFirstName(it.toString())
        }

        binding.lastNameInput.doAfterTextChanged {
            viewModel.updateLastName(it.toString())
        }

        binding.employeeIdInput.doAfterTextChanged {
            viewModel.updateEmployeeId(it.toString())
        }

        binding.sectorInput.doAfterTextChanged {
            viewModel.updateSector(it.toString())
        }
    }

    private fun setupButtonListeners() {
        binding.btnSave.setOnClickListener {
            viewModel.saveTechnician()
        }
    }

    private fun observeViewModel() {
        viewModel.firstName.observe(viewLifecycleOwner) { name ->
            if (binding.firstNameInput.text.toString() != name) {
                binding.firstNameInput.setText(name)
            }
        }

        viewModel.lastName.observe(viewLifecycleOwner) { name ->
            if (binding.lastNameInput.text.toString() != name) {
                binding.lastNameInput.setText(name)
            }
        }

        viewModel.employeeId.observe(viewLifecycleOwner) { id ->
            if (binding.employeeIdInput.text.toString() != id) {
                binding.employeeIdInput.setText(id)
            }
        }

        viewModel.sector.observe(viewLifecycleOwner) { sector ->
            if (binding.sectorInput.text.toString() != sector) {
                binding.sectorInput.setText(sector)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }

        viewModel.saved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                Snackbar.make(
                    binding.root,
                    R.string.profile_saved,
                    Snackbar.LENGTH_SHORT
                ).show()
                findNavController().navigate(
                    TechnicianProfileFragmentDirections.actionTechnicianProfileFragmentToHomeFragment()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}