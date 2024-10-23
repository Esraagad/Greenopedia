package com.example.greenopedia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greenopedia.R
import com.example.greenopedia.data.remote.responses.Data
import com.example.greenopedia.databinding.FragmentPlantDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPlantDetailsBinding
    private val args: PlantDetailsFragmentArgs by navArgs()
    private lateinit var plant: Data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plant = args.plant
        previewPlantsCard(plant)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun previewPlantsCard(plant: Data) {
        binding.apply {
            Glide.with(binding.root.context)
                .load(plant.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(plantImageView)
            plantNameTextView.text = plant.commonName ?: "NA"
            plantFamilyTextView.text = plant.familyCommonName ?: "NA"
            plantAuthorTextView.text = plant.author ?: "NA"

            var index = if (plant.bibliography == null && plant.scientificName == null)
                "NA \n NA"
            else if (plant.bibliography == null)
                "NA \n ${plant.scientificName}"
            else if (plant.scientificName == null)
                "${plant.bibliography} \n NA"
            else
                plant.bibliography + "\n" + plant.scientificName
            plantIndexTextView.text = index

            moreDetailsButton.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("scientificName", plant.scientificName)
                }
                findNavController().navigate(
                    R.id.action_plantDetailsFragment_to_moreDetailsWebViewFragment,
                    bundle
                )
            }
        }
    }
}