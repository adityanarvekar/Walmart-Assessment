package com.aditya.walmartassessment.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.walmartassessment.databinding.VhCountryBinding
import com.aditya.walmartassessment.domain.dto.CountryDomain

/**
 * Recyclerview Adapter
 */
class CountryAdapter(
    private var countryList: List<CountryDomain> = emptyList()
) : RecyclerView.Adapter<CountryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhCountryBinding.inflate(inflater, parent, false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount() = countryList.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.setData(countryList[position])
    }

    fun updateData(newList: List<CountryDomain>) {
        countryList = newList
        notifyDataSetChanged()
    }
}