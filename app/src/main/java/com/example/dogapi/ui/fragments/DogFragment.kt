package com.example.dogapi.ui.fragments

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dogapi.R
import com.example.dogapi.data.remote.DogApiService
import com.example.dogapi.data.repository.DogRepository
import com.example.dogapi.viewModel.DogViewModel
import com.example.dogapi.viewModel.DogViewModelFactory
import com.squareup.picasso.Picasso

class DogFragment : Fragment() {

    private lateinit var viewModel: DogViewModel
    private lateinit var imageView: ImageView
    private lateinit var nextImageButton: Button
    private lateinit var downloadButton: Button
    private lateinit var goBackButton: Button

    private var currentImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.dogImageView)
        nextImageButton = view.findViewById(R.id.nextImageButton)
        downloadButton = view.findViewById(R.id.downloadButton)
        goBackButton = view.findViewById(R.id.goBackButton)

        val apiService = DogApiService.create()
        val repository = DogRepository(apiService)
        val factory = DogViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(DogViewModel::class.java)

        viewModel.dogImage.observe(viewLifecycleOwner) { imageUrl ->
            imageUrl?.let {
                currentImageUrl = it
                Picasso.get().load(it).into(imageView)
            }
        }

        nextImageButton.setOnClickListener {
            viewModel.getRandomDogImage()
        }

        downloadButton.setOnClickListener {
            currentImageUrl?.let { imageUrl ->
                downloadImage(imageUrl)
            }
        }

        goBackButton.setOnClickListener {
            requireActivity().onBackPressed()  
        }

        viewModel.getRandomDogImage()
    }

    private fun downloadImage(imageUrl: String) {
        val request = DownloadManager.Request(Uri.parse(imageUrl))
            .setTitle("Dog Image")
            .setDescription("Завантаження зображення собаки")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "dog_image.jpg"
            )
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}
