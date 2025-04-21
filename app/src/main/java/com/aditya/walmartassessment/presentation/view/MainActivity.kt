package com.aditya.walmartassessment.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.walmartassessment.WalmartApp
import com.aditya.walmartassessment.data.repository.RepositoryImpl
import com.aditya.walmartassessment.data.services.ApiService
import com.aditya.walmartassessment.data.utility.ConnectivityChecker
import com.aditya.walmartassessment.data.utility.Result
import com.aditya.walmartassessment.databinding.ActivityMainBinding
import com.aditya.walmartassessment.domain.usecase.GetCountryListUseCase
import com.aditya.walmartassessment.presentation.viewmodel.CountryViewModel
import com.aditya.walmartassessment.presentation.viewmodel.CountryViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var connectivityChecker: ConnectivityChecker
    private lateinit var countryAdapter: CountryAdapter
    private val viewModel: CountryViewModel by viewModels {
        val apiService = ApiService.getInstance()
        val repository = RepositoryImpl(apiService)
        val useCase = GetCountryListUseCase(repository)
        CountryViewModelFactory(useCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        connectivityChecker = (application as WalmartApp).connectivityChecker
        binding.rvCountries.layoutManager = LinearLayoutManager(this@MainActivity)

        countryAdapter = CountryAdapter()
        binding.rvCountries.adapter = countryAdapter

        lifecycleScope.launch {
            // First, check network and trigger API call if connected
            if(connectivityChecker.isConnected.value){
                viewModel.getCountryList()
            }

            // Observe network changes to trigger API call when connection returns
            launch {
                connectivityChecker.isConnected.collect { isConnected ->
                    if (isConnected && viewModel.countryListState.value is Result.Empty) {
                        viewModel.getCountryList()
                    }

                    // Hide "No Connection" only if data is fetched successfully at least once
                    binding.txtNoConnection.visibility =
                        if (isConnected || viewModel.countryListState.value is Result.Success) View.GONE
                        else View.VISIBLE
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.countryListState.collect { result ->
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                countryAdapter.updateData(result.data)
                            }

                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@MainActivity,
                                    result.exception.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            is Result.Empty -> {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Something went wrong",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.scrollPosition = (binding.rvCountries.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
    }

    override fun onResume() {
        super.onResume()
        (binding.rvCountries.layoutManager as LinearLayoutManager)
            .scrollToPosition(viewModel.scrollPosition)
    }
}