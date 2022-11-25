package com.connaughttechnologies.lovedonce.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.data.DataLayer
import com.connaughttechnologies.lovedonce.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    lateinit var dataLayer: DataLayer
    private val pages = 4;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        binding.onBoardingPager.adapter = OnBoardingAdapter(this)
    }

    private inner class OnBoardingAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return pages
        }

        override fun createFragment(position: Int): Fragment {
            val nextPage = { nextPage: Int ->
                binding.onBoardingPager.currentItem = nextPage
                if (nextPage == pages)
                    finish()
            }
            val skip = {
                finish()
            }
            return OnBoardingFragment(position, nextPage, skip)
        }
    }
}