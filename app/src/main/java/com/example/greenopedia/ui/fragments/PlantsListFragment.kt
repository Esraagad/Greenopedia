package com.example.greenopedia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.greenopedia.databinding.FragmentPlantsListBinding
import com.example.greenopedia.ui.viewmodels.PlantsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantsListFragment : Fragment() {
    private val viewModel: PlantsViewModel by viewModels()

    lateinit var binding: FragmentPlantsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlantsListBinding.inflate(inflater, container, false)
        return binding.root
    }
}