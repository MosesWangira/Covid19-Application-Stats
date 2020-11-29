package com.example.covid19statsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19statsapp.R
import com.example.covid19statsapp.data.adapter.RegionAdapter
import com.example.covid19statsapp.data.model.DataClassItem
import com.example.covid19statsapp.data.network.RetrofitBuilder
import com.example.covid19statsapp.databinding.FragmentRegionsBinding
import com.example.covid19statsapp.util.isNetworkAvailable
import com.example.covid19statsapp.util.snackbar
import com.example.covid19statsapp.util.toast
import kotlinx.android.synthetic.main.fragment_regions.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class Regions : Fragment() {

    lateinit var binding: FragmentRegionsBinding

    lateinit var emptyView: View

    lateinit var rotate: Animation
    lateinit var loading: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_regions, container, false)

        emptyView = binding.emptyView

        loading = binding.regionLoad
        loading.visibility = View.GONE
        rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)

        if(isNetworkAvailable(requireContext())){
            emptyView.visibility = View.GONE
            loading.visibility = View.VISIBLE
            loading.startAnimation(rotate)
        }

        fetchStatistics()

        binding.refreshImage.setOnClickListener {
            if (!isNetworkAvailable(requireContext())) {
                it.snackbar("Check Internet Connection")
            }
            else{
                it.snackbar("Loading")
                loading.visibility = View.VISIBLE
                loading.startAnimation(rotate)
                emptyView.visibility = View.GONE
                fetchStatistics()
            }
        }

        return binding.root
    }


    private fun fetchStatistics() {

        val fetchingStatistics = RetrofitBuilder.apiService.getStatistics()

        fetchingStatistics.enqueue(object : Callback<MutableList<DataClassItem>> {

            override fun onFailure(call: Call<MutableList<DataClassItem>>, t: Throwable) {
                loading.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            }

            override fun onResponse(
                call: Call<MutableList<DataClassItem>>,
                response: Response<MutableList<DataClassItem>>
            ) {
                if (response.isSuccessful) {
                    loading.clearAnimation()
                    loading.visibility = View.GONE
                    val stats = response.body()
                    stats?.let {
                        emptyView.visibility = View.GONE
                        showStatistics(it)
                    }
                }
            }

        })

    }

    private fun showStatistics(stats: MutableList<DataClassItem>) {
        try {
            recyclerViewDisplay.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewDisplay.hasFixedSize()
            recyclerViewDisplay.adapter = RegionAdapter(stats)

            val resId: Int = R.anim.layout_animation_fall_down
            val animation: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(requireContext(), resId)
            recyclerViewDisplay.layoutAnimation = animation
        }catch (e: Exception){
            Log.d("Null recycler Item", "crash caused by null exception on recycler view")
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_item, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searching(newText)
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)

    }

    private fun searching(textToSearch: String) {

        val fetchingStatistics = RetrofitBuilder.apiService.getStatistics()
        fetchingStatistics.enqueue(object : Callback<MutableList<DataClassItem>> {
            override fun onFailure(call: Call<MutableList<DataClassItem>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<MutableList<DataClassItem>>,
                response: Response<MutableList<DataClassItem>>
            ) {
                if (response.isSuccessful) {
                    val stats = response.body()!!

                    val listSize = stats.size
                    val filteredList = mutableListOf<DataClassItem>()
                    val myLoop = listSize - 1

                    for (x in 0..myLoop) {
                        val textCheck = stats.elementAt(x).country

                        if (textCheck.toLowerCase().contains(textToSearch.toLowerCase())) {
                            val country = stats.elementAt(x).country
                            val totalCases = stats.elementAt(x).cases
                            val totalRecoveries = stats.elementAt(x).recovered
                            val totalDeaths = stats.elementAt(x).deaths

                            val todayCases = stats.elementAt(x).todayCases
                            val todayRecoveries = stats.elementAt(x).todayRecovered
                            val todayDeaths = stats.elementAt(x).todayDeaths

                            val critical = stats.elementAt(x).critical
                            val tested = stats.elementAt(x).tests
                            val date = stats.elementAt(x).updated
                            val continent = stats.elementAt(x).continent
                            val countryImage = stats.elementAt(x).countryInfo

                            val filter = DataClassItem(
                                country, totalCases, totalRecoveries, totalDeaths,
                                todayCases, todayRecoveries, todayDeaths,
                                critical, tested, date, continent, countryImage
                            )

                            filteredList.add(filter)

                            try {
                                recyclerViewDisplay.adapter = RegionAdapter(filteredList)
                            }catch (e: Exception){
                                Log.d("Null recycler Item", "crash caused by null exception on recycler view")
                            }
                        }
                    }
                }
            }

        })

    }
}