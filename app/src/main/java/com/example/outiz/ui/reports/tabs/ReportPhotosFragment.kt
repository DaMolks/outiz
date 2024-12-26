package com.example.outiz.ui.reports.tabs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.outiz.databinding.FragmentReportPhotosBinding
import com.example.outiz.ui.reports.EditReportViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReportPhotosFragment : Fragment() {

    private var _binding: FragmentReportPhotosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditReportViewModel by viewModels({ requireParentFragment() })

    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>
    private lateinit var choosePictureLauncher: ActivityResultLauncher<String>
    private var currentPhotoPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLaunchers()
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
        setupObservers()
        setupListeners()
    }

    private fun registerLaunchers() {
        takePictureLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                currentPhotoPath?.let { path ->
                    viewModel.addPhoto(path)
                }
            }
        }

        choosePictureLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { imageUri ->
                // Copier l'image sélectionnée dans le dossier de l'application
                val file = createImageFile()
                file.outputStream().use { outputStream ->
                    requireContext().contentResolver.openInputStream(imageUri)?.use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                viewModel.addPhoto(file.absolutePath)
            }
        }
    }

    private fun setupRecyclerView() {
        photosAdapter = PhotosAdapter { photo ->
            viewModel.removePhoto(photo)
        }

        binding.photosRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = photosAdapter
        }
    }

    private fun setupObservers() {
        viewModel.photosPaths.observe(viewLifecycleOwner) { paths ->
            photosAdapter.submitList(paths)
            binding.emptyView.visibility = if (paths.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupListeners() {
        binding.takePictureButton.setOnClickListener {
            takePicture()
        }

        binding.choosePictureButton.setOnClickListener {
            choosePictureLauncher.launch("image/*")
        }
    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                val photoFile = createImageFile()
                currentPhotoPath = photoFile.absolutePath

                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.outiz.fileprovider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureLauncher.launch(takePictureIntent)
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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
}