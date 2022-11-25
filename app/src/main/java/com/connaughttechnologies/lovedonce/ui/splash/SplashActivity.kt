package com.connaughttechnologies.lovedonce.ui.splash

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.MainActivity
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseActivity
import com.connaughttechnologies.lovedonce.databinding.ActivitySplashBinding
import com.connaughttechnologies.lovedonce.ui.auth.AuthActivity
import com.connaughttechnologies.lovedonce.ui.onboarding.OnBoardingActivity
import com.connaughttechnologies.lovedonce.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {
    override var viewModelClass: Class<SplashViewModel> = SplashViewModel::class.java
    override var layoutId: Int = R.layout.activity_splash
    override var bindingVariable: Int = BR.viewModel

    private lateinit var timerTask: TimerTask
    private lateinit var timer: Timer

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
    }

    override fun onResume() {
        super.onResume()

        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                navigateToActivity()
            }

        }
        lifecycleScope.launch {
            if (dataLayer.shouldShowOnBoarding().first()) {
                dataLayer.saveShouldShowOnBoarding(false)
                Intent(this@SplashActivity, OnBoardingActivity::class.java).apply {
                    startActivity(this)
                }
            } else
                timer.schedule(timerTask, Constants.DELAY_SPLASH)
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            timer.cancel()
            timer.purge()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun navigateToActivity() {
        lifecycleScope.launch {
            if (dataLayer.getIsLoggedIn().first()) {
                Intent(this@SplashActivity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(this)
                }
            } else {
                Intent(this@SplashActivity, AuthActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(this)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.default_notification_channel_name)
            val channelId = getString(R.string.default_notification_channel_id)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(channelId, name, importance)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}