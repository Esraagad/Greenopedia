package com.example.greenopedia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greenopedia.R
import com.example.greenopedia.data.remote.responses.Data
import com.example.greenopedia.databinding.FragmentPlantDetailsBinding
import com.example.greenopedia.ui.viewmodels.PlantsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantDetailsFragment : Fragment() {
    private val viewModel: PlantsViewModel by viewModels()
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
        setupToolbar()
        plant = args.plant
        previewPlantsCard(plant)
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        // Customize the toolbar if needed
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)  // To show the back button
            title = getString(R.string.toolbar_plant_details_screen)
        }

        toolbar.setNavigationOnClickListener {
            // Handle the back button click or other actions
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
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
            //TODO:Check for nullability
            plantIndexTextView.text = "${plant.bibliography}\n${plant.scientificName}"
            moreDetailsButton.setOnClickListener {

            }
        }
    }
}