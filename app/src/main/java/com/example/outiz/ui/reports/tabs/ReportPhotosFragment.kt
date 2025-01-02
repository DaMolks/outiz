package com.example.outiz.ui.reports.tabs

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.outiz.databinding.FragmentReportPhotosBinding
import com.example.outiz.ui.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ReportPhotosFragment : Fragment() {
    private var _binding: FragmentReportPhotosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels({ requireParentFragment() })
    private lateinit var photoAdapter: PhotoAdapter
    private var currentPhotoPath: String? = null

    private val takePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            currentPhotoPath?.let { path ->
                viewModel.addPhoto(path)
            }
        }
    }

    private val selectPicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                // Copier l'image sélectionnée dans le dossier de l'application
                val file = createImageFile()
                context?.contentResolver?.openInputStream(uri)?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                viewModel.addPhoto(file.absolutePath)
            }
        }
    }

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            takePhoto()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupButtons()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        photoAdapter = PhotoAdapter { photoPath ->
            viewModel.removePhoto(photoPath)
        }

        binding.rvPhotos.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = photoAdapter
        }
    }

    private fun setupButtons() {
        binding.btnTakePhoto.setOnClickListener {
            checkCameraPermission()
        }

        binding.btnChoosePhoto.setOnClickListener {
            selectPhotoFromGallery()
        }
    }

    private fun observeViewModel() {
        viewModel.photosPaths.observe(viewLifecycleOwner) { paths ->
            photoAdapter.submitList(paths)
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                takePhoto()
            }
            else -> {
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun takePhoto() {
        val photoFile = createImageFile()
        currentPhotoPath = photoFile.absolutePath

        val photoURI: Uri = FileProvider.getUriForFile(
            requireContext(),
            "com.example.outiz.fileprovider",
            photoFile
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }

        takePicture.launch(intent)
    }

    private fun selectPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        selectPicture.launch(intent)
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireContext().getExternalFilesDir(null)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(reportId: Long) = ReportPhotosFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_REPORT_ID, reportId)
            }
        }
        
        private const val ARG_REPORT_ID = "report_id"
    }
}