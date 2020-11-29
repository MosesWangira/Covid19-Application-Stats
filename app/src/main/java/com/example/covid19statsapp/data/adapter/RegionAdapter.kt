package com.example.covid19statsapp.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19statsapp.R
import com.example.covid19statsapp.data.model.DataClassItem
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

        /**
         * Change date updated to formatted string
         * */
        val timeUpdated = statistics.updated

        holder.view.dateUpdated.text = statistics.updated.toString()
    }


    class StatisticsViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}