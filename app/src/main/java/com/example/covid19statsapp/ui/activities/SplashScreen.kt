package com.example.covid19statsapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.covid19statsapp.R
import com.example.covid19statsapp.databinding.ActivitySplashScreenBinding
import java.util.*

class SplashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_splash_screen
        )

        val arr =
            arrayOf(
                "\"Clean your hands often. Use soap and water, or an alcohol-based hand rub.\"",
                "\"Maintain a safe prevention_ic from anyone who is coughing or sneezing.\"",
                "\"Cover your nose and mouth with your bent elbow or a tissue when you cough or sneeze\"",
                "\"If you have a fever, cough and difficulty breathing, seek medical attention\"",
                "\"Don’t touch your eyes, nose or mouth\""

            )
        val r = Random()
        val randomString: Int = r.nextInt(arr.size)

        val description = binding.splashDescription

        description.text = arr[randomString]

        val animator = AnimationUtils.loadAnimation(this,
            R.anim.splash_animation
        )

        binding.splashImage.startAnimation(animator)
        description.startAnimation(animator)
        binding.splashText.startAnimation(animator)

        val toMainActivity = Intent(this, MainActivity::class.java)
        //running on thread
        val timer = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(toMainActivity)
                    finish()
                }
            }
        }
        timer.start()
    }
}