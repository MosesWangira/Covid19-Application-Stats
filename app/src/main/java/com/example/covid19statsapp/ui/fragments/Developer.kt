package com.example.covid19statsapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.covid19statsapp.R
import com.example.covid19statsapp.databinding.FragmentDeveloperBinding

class Developer : Fragment() {
    lateinit var binding: FragmentDeveloperBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_developer, container, false)

        openUrlOnClick(binding.github, "https://github.com/MosesWangira")
        openUrlOnClick(binding.linkedIn, "https://www.linkedin.com/in/moses-ouma-967b58168/")
        openUrlOnClick(binding.medium, "https://medium.com/@proudmoses")

        binding.gmail.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "proudmoses@gmail.com", null
                )
            )
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }



        return binding.root
    }

    /**
     * open url
     * */
    private fun openUrl(uriString: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
        startActivity(browserIntent)
    }

    /**
     * open url onClick
     * */
    private fun openUrlOnClick(view: View, uriString: String) {
        view.setOnClickListener {
            openUrl(uriString)
        }
    }
}