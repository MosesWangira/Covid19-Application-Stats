@file:Suppress("DEPRECATION")

package com.example.covid19statsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.daasuu.cat.CountAnimationTextView
import com.example.covid19statsapp.R
import com.example.covid19statsapp.data.model.DataClassItem
import com.example.covid19statsapp.data.model.GlobalDataClass
import com.example.covid19statsapp.data.network.RetrofitBuilder
import com.example.covid19statsapp.databinding.FragmentSummaryBinding
import com.example.covid19statsapp.util.isNetworkAvailable
import com.example.covid19statsapp.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Summary : Fragment() {

    lateinit var binding: FragmentSummaryBinding

    lateinit var rotate: Animation

    lateinit var globalAnimation: ImageView
    lateinit var africaAnimation: ImageView
    lateinit var kenyaAnimation: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_summary, container, false
        )

        rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)

        globalAnimation = binding.globalLoading
        africaAnimation = binding.africaLoading
        kenyaAnimation = binding.kenyaLoading

        if(isNetworkAvailable(requireContext())){
            globalAnimation.startAnimation(rotate)
            africaAnimation.startAnimation(rotate)
            kenyaAnimation.startAnimation(rotate)
        }
        else{
            requireContext().toast("No Internet Connection")
        }

        animateHomeViews()

        fetchGlobalStatistics()
        fetchStatisticsKenya()
        fetchStatisticsAfrica()


        return binding.root
    }

    private fun animate(number: CountAnimationTextView, maxCount: Int) {
        number
            .setAnimationDuration(3000)
            .countAnimation(0, maxCount)
    }

    private fun animateHomeViews() {
        val leftToNormal = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.left_to_normal
        )
        val rightToNormal = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.right_to_normal
        )

        binding.world.startAnimation(leftToNormal)
        binding.africa.startAnimation(rightToNormal)
        binding.kenya.startAnimation(leftToNormal)
    }

    /**
     * ***************************************************************************************
     * */
    /**
     * Get Item from API World
     * */
    private fun fetchGlobalStatistics(){
        val fetchingGloabalStatistics = RetrofitBuilder.apiService.getGlobalStatistics()

        fetchingGloabalStatistics.enqueue(object : Callback<GlobalDataClass> {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onFailure(call: Call<GlobalDataClass>, t: Throwable) {
                globalAnimation.clearAnimation()
                globalAnimation.setImageDrawable(resources.getDrawable(R.drawable.no_wifi))
            }

            override fun onResponse(
                call: Call<GlobalDataClass>,
                response: Response<GlobalDataClass>
            ) {
                if (response.isSuccessful) {
                    globalAnimation.clearAnimation()
                    globalAnimation.visibility = View.GONE
                    val globalStats = response.body()!!

                    val globalTotalCases = globalStats.cases
                    val globalTotalRecoveries = globalStats.recovered
                    val globalTotalDeaths = globalStats.deaths

                    val totalCases = binding.totalCasesGlobal
                    val totalRecovery = binding.totalGlobalRecovery
                    val totalDeaths = binding.totalDeathGlobal

                    animate(totalCases, globalTotalCases)
                    animate(totalRecovery, globalTotalRecoveries)
                    animate(totalDeaths, globalTotalDeaths)

                }
            }
        })
    }



    /**
     * ******************************************************************************************
     * */

    /**
     * *****************************************************************************************
     * */

    /**
     * ***************************************************************************************
     * */
    /**
     * Get Item from API Africa
     * */

    private fun fetchStatisticsAfrica() {

        val fetchingStatistics = RetrofitBuilder.apiService.getStatistics()

        fetchingStatistics.enqueue(object : Callback<MutableList<DataClassItem>> {

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onFailure(call: Call<MutableList<DataClassItem>>, t: Throwable) {
                africaAnimation.clearAnimation()
                africaAnimation.setImageDrawable(resources.getDrawable(R.drawable.no_wifi))            }

            override fun onResponse(
                call: Call<MutableList<DataClassItem>>,
                response: Response<MutableList<DataClassItem>>
            ) {
                if (response.isSuccessful) {
                    africaAnimation.clearAnimation()
                    africaAnimation.visibility = View.GONE
                    val stats = response.body()!!

                    val listSize = stats.size
                    val myLoop = listSize - 1

                    var sumCasesAfrica = 0
                    var sumRecoveriesAfrica = 0
                    var sumDeathsAfrica = 0

                    for (x in 0..myLoop) {
                        val checkCountryAfrica = stats.elementAt(x).continent

                        /**
                         * Check if country is from africa
                         * */

                        if(checkCountryAfrica == "Africa") {
                            /**
                             * sum of all total cases in continent africa
                             * */
                            val totalCasesAfrica = stats.elementAt(x).cases
                            sumCasesAfrica += totalCasesAfrica

                            /**
                             * sum of all recoveries africa
                             * */
                            val totalRecoveriesAfrica = stats.elementAt(x).recovered
                            sumRecoveriesAfrica += totalRecoveriesAfrica

                            /**
                             * sum of all recoveries africa
                             * */
                            val totalDeathAfrica = stats.elementAt(x).deaths
                            sumDeathsAfrica += totalDeathAfrica

                        }

                    }
                    val totalCasesAfricaWide = binding.totalCasesAfrica
                    val totalRecoveryAfrica = binding.totalRecoveryAfrica
                    val totalDeathsAfrica = binding.totalDeathAfrica

                    animate(totalCasesAfricaWide, sumCasesAfrica)
                    animate(totalRecoveryAfrica, sumRecoveriesAfrica)
                    animate(totalDeathsAfrica, sumDeathsAfrica)

                }
            }

        })

    }






    /**
     * ******************************************************************************************
     * */

    /**
     * *****************************************************************************************
     * */

    /**
     * Get Items from API for Kenya
     * */

    private fun fetchStatisticsKenya() {

        val fetchingStatistics = RetrofitBuilder.apiService.getStatistics()

        fetchingStatistics.enqueue(object : Callback<MutableList<DataClassItem>> {

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onFailure(call: Call<MutableList<DataClassItem>>, t: Throwable) {
                kenyaAnimation.clearAnimation()
                kenyaAnimation.setImageDrawable(resources.getDrawable(R.drawable.no_wifi))
            }

            override fun onResponse(
                call: Call<MutableList<DataClassItem>>,
                response: Response<MutableList<DataClassItem>>
            ) {
                if (response.isSuccessful) {
                    kenyaAnimation.clearAnimation()
                    kenyaAnimation.visibility = View.GONE
                    val stats = response.body()!!

                    val listSize = stats.size
                    val myLoop = listSize - 1

                    for (x in 0..myLoop) {
                        val textCheck = stats.elementAt(x).country

                        if (textCheck.toLowerCase() == "Kenya".toLowerCase()) {
                            val totalCases = stats.elementAt(x).cases
                            val totalRecoveries = stats.elementAt(x).recovered
                            val totalDeaths = stats.elementAt(x).deaths

                            val totalCasesKenya = binding.totalCaseKenya
                            val totalRecoveryKenya = binding.totalRecoveredKenya
                            val totalDeathsKenya = binding.totalDeathsKenya

                            animate(totalCasesKenya, totalCases)
                            animate(totalRecoveryKenya, totalRecoveries)
                            animate(totalDeathsKenya, totalDeaths)
                        }
                    }
                }
            }

        })

    }


    /**
     * ******************************************************************************************
     * */

    /**
     * *******************************************************************************************
     * **/

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh_summary -> {
                if (!isNetworkAvailable(requireContext())){
                    requireContext().toast("No Internet Connection")
                }
                else{
                    requireContext().toast("Loading")
                    fetchStatisticsKenya()
                    fetchStatisticsAfrica()
                    fetchGlobalStatistics()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
