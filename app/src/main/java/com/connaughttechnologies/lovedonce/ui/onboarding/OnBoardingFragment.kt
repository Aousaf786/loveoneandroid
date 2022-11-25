package com.connaughttechnologies.lovedonce.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.databinding.FragmentOnBoardingBinding

class OnBoardingFragment(
    private val page: Int,
    private val nextPage: (Int) -> Unit,
    private val skip: () -> Unit
) :
    Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentOnBoardingBinding>(
            inflater,
            R.layout.fragment_on_boarding,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            nextPage(page + 1)
        }
        binding.tvBtnSkip.setOnClickListener {
            skip.invoke()
        }

        fun showHiddenUi() {
            binding.apply {
                tvTitle.isVisible = true
                ivIndicator.isVisible = true
                btnNext.isVisible = true
            }
        }
        when (page) {
            0 -> {
                binding.layoutParent.setBackgroundColor(resources.getColor(R.color.white))
                binding.ivLogo.setImageDrawable(resources.getDrawable(R.drawable.img_splash))
                binding.tvTitle.isVisible = false
                binding.ivIndicator.isVisible = false
                binding.btnNext.isVisible = false
            }
            1 -> {
                showHiddenUi()
                binding.layoutParent.setBackgroundColor(resources.getColor(R.color.white))
                binding.ivLogo.setImageDrawable(resources.getDrawable(R.drawable.img_on_boarding_page1))
                binding.tvTitle.text = resources.getString(R.string.on_boarding_page1)
                binding.tvTitle.setTextColor(resources.getColor(R.color.black))
                binding.ivIndicator.setImageDrawable(resources.getDrawable(R.drawable.img_on_boarding_page1_indicator))
                binding.btnNext.background =
                    resources.getDrawable(R.drawable.bg_btn_on_boarding_page1)
                binding.btnNext.setTextColor(resources.getColor(R.color.white))
                binding.tvBtnSkip.setTextColor(resources.getColor(R.color.black))
            }
            2 -> {
                showHiddenUi()
                binding.layoutParent.setBackgroundColor(resources.getColor(R.color.color_31AFE5))
                binding.ivLogo.setImageDrawable(resources.getDrawable(R.drawable.img_on_boarding_page2))
                binding.tvTitle.text = resources.getString(R.string.on_boarding_page2)
                binding.tvTitle.setTextColor(resources.getColor(R.color.white))
                binding.ivIndicator.setImageDrawable(resources.getDrawable(R.drawable.img_on_boarding_page2_indicator))
                binding.btnNext.background =
                    resources.getDrawable(R.drawable.bg_btn_on_boarding_page2)
                binding.btnNext.setTextColor(resources.getColor(R.color.color_31AFE5))
                binding.tvBtnSkip.setTextColor(resources.getColor(R.color.white))
            }
            3 -> {
                showHiddenUi()
                binding.layoutParent.setBackgroundColor(resources.getColor(R.color.color_EEC013))
                binding.ivLogo.setImageDrawable(resources.getDrawable(R.drawable.img_on_boarding_page3))
                binding.tvTitle.text = resources.getString(R.string.on_boarding_page3)
                binding.tvTitle.setTextColor(resources.getColor(R.color.black))
                binding.ivIndicator.setImageDrawable(resources.getDrawable(R.drawable.img_on_boarding_page3_indicator))
                binding.btnNext.background =
                    resources.getDrawable(R.drawable.bg_btn_on_boarding_page3)
                binding.btnNext.setTextColor(resources.getColor(R.color.white))
                binding.tvBtnSkip.setTextColor(resources.getColor(R.color.black))
            }
        }

    }
}