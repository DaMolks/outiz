package com.example.outiz.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.outiz.R
import com.example.outiz.databinding.FragmentTechnicianProfileBinding
import com.google.android.material.snackbar.Snackbar

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

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.profileCreated.observe(viewLifecycleOwner) { isCreated ->
            if (isCreated) {
                findNavController().navigate(R.id.action_technicianProfileFragment_to_homeFragment)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    private fun setupListeners() {
        binding.createProfileButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val sector = binding.sectorEditText.text.toString()
            val identifier = binding.identifierEditText.text.toString()
            val supervisor = binding.supervisorEditText.text.toString()

            viewModel.createProfile(
                firstName = firstName,
                lastName = lastName,
                sector = sector,
                identifier = identifier,
                supervisor = supervisor
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}