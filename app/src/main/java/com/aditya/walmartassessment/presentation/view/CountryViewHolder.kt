package com.aditya.walmartassessment.presentation.view

import androidx.recyclerview.widget.RecyclerView
import com.aditya.walmartassessment.R
import com.aditya.walmartassessment.databinding.VhCountryBinding
import com.aditya.walmartassessment.domain.dto.CountryDomain

/**
 * viewholder for recyclerview data
 */
class CountryViewHolder(
    private val binding: VhCountryBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun setData(countryDomain: CountryDomain) {
        with(binding) {
            txtCode.text = countryDomain.code
            txtCapital.text = countryDomain.capital
            txtNameRegion.text = itemView.resources.getString(
                R.string.name_region_format, countryDomain.name, countryDomain.region
            )
        }
    }
}