package com.example.greenopedia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greenopedia.databinding.FragmentMoreDetailsWebViewBinding
import com.example.greenopedia.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoreDetailsWebViewFragment : Fragment() {
    private lateinit var binding: FragmentMoreDetailsWebViewBinding
    private lateinit var scientificName: String
    private val args: MoreDetailsWebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreDetailsWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scientificName = args.scientificName
        loadPlantDetailsWebView()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = scientificName
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun loadPlantDetailsWebView() {
        binding.plantDetailsWebView.apply {
            webViewClient = WebViewClient()
            loadUrl(Constants.WIKIPIDIA_URL + scientificName)
        }
    }
}