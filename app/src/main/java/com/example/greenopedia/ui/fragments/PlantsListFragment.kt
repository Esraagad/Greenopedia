package com.example.greenopedia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenopedia.databinding.FragmentPlantsListBinding
import com.example.greenopedia.ui.adapters.PlantsListAdapter
import com.example.greenopedia.ui.viewmodels.PlantsViewModel
import com.example.greenopedia.utils.Constants.QUERY_PAGE_SIZE
import com.example.greenopedia.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantsListFragment : Fragment() {
    private val viewModel: PlantsViewModel by viewModels()
    private lateinit var plantsListAdapter: PlantsListAdapter
    private lateinit var binding: FragmentPlantsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlantsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPlantsRecyclerView()
        observePlants()
    }

    private fun setupPlantsRecyclerView() {
        plantsListAdapter = PlantsListAdapter()
        binding.plantsRecyclerView.apply {
            adapter = plantsListAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(customScrollListener)
        }
    }

    private fun observePlants() {
        viewModel.plants.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { plantsResponse ->
                        plantsListAdapter.differ.submitList(plantsResponse.plantsList.toList())
                        val totalPages = plantsResponse.meta.total / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.plantsPageNum == totalPages

                        if (isLastPage) {
                            binding.plantsRecyclerView.setPadding(0, 0, 0, 0)
                        }
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, message, Toast.LENGTH_LONG)
                            .show()
                        showErrorMessage(message)
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        binding.itemErrorMessage.retryButton.setOnClickListener {
            viewModel.getAllPlants()
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        binding.itemErrorMessage.root.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        binding.itemErrorMessage.root.visibility = View.VISIBLE
        binding.itemErrorMessage.errorMessageTextView.text = message
        isError = true
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val customScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getAllPlants()
                isScrolling = false
            }
        }
    }
}