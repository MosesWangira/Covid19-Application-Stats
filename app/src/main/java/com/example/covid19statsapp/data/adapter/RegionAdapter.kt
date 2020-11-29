package com.example.covid19statsapp.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covid19statsapp.R
import com.example.covid19statsapp.data.model.CountryInfo
import com.example.covid19statsapp.data.model.DataClassItem
import com.example.covid19statsapp.util.convertMillisToFormattedDate
import kotlinx.android.synthetic.main.recycler_item.view.*

class RegionAdapter(val items: List<DataClassItem>) :
    RecyclerView.Adapter<RegionAdapter.StatisticsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StatisticsViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item,
            parent,
            false
        )
    )


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {

        val statistics = items[position]

        holder.view.country.text = statistics.country
        holder.view.totalCases.text = statistics.cases.toString()
        holder.view.totalRecoveries.text = statistics.recovered.toString()
        holder.view.totalDeaths.text = statistics.deaths.toString()

        holder.view.todayCases.text = statistics.todayCases.toString()
        holder.view.todayRecoveries.text = statistics.todayRecovered.toString()
        holder.view.todayDeaths.text = statistics.todayDeaths.toString()

        holder.view.criticalCases.text = statistics.critical.toString()

//        val urlFlag = statistics.countryInfo.flag
//        Glide
//            .with(holder.view.context)
//            .load(urlFlag)
//            .centerCrop()
//            .placeholder(R.drawable.global)
//            .into(holder.view.countryImage)

        /**
         * Change date updated to formatted string
         * */
        val timeUpdated = statistics.updated

        val convertedDate = convertMillisToFormattedDate(timeUpdated)

        holder.view.dateUpdated.text = convertedDate

        /**
         * Add country flag
         * */

    }


    class StatisticsViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}