package com.example.outiz.ui.sites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.outiz.R
import com.example.outiz.databinding.FragmentEditSiteBinding
import com.google.android.material.snackbar.Snackbar

class EditSiteFragment : Fragment() {

    private var _binding: FragmentEditSiteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditSiteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditSiteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("siteId")?.let { siteId ->
            viewModel.loadSite(siteId)
        }

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.site.observe(viewLifecycleOwner) { site ->
            site?.let {
                with(binding) {
                    siteNameInput.setText(it.name)
                    siteCodeInput.setText(it.codeS)
                    siteAddressInput.setText(it.address)
                    clientNameInput.setText(it.clientName)
                }
            }
        }

        viewModel.saved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                findNavController().popBackStack()
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
        binding.saveSiteButton.setOnClickListener {
            val name = binding.siteNameInput.text.toString()
            val code = binding.siteCodeInput.text.toString()
            val address = binding.siteAddressInput.text.toString()
            val clientName = binding.clientNameInput.text.toString()

            viewModel.saveSite(
                name = name,
                codeS = code,
                address = address,
                clientName = clientName
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}