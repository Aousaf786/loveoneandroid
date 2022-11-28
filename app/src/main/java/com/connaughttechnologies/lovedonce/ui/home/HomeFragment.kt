package com.connaughttechnologies.lovedonce.ui.home

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.databinding.FragmentHomeBinding
import com.connaughttechnologies.lovedonce.ui.home.adapter.EventCardsAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override var viewModelClass: Class<HomeViewModel> = HomeViewModel::class.java
    override var layoutId: Int = R.layout.fragment_home
    override var bindingVariable: Int = BR.viewModel

    private lateinit var eventCardsAdapter: EventCardsAdapter

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.getEventCards()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.init(repositoryPref.user.first())
        }

        eventCardsAdapter = EventCardsAdapter()
        binding.rvEventCards.adapter = eventCardsAdapter

        binding.ivChat.setOnClickListener {
            sharedViewModel.isGroupChat = false
            findNavController().navigate(R.id.chatFragment)
        }

        binding.contentOrderStatus.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }
        binding.contentSearch.setOnClickListener {
            findNavController().navigate(R.id.memorialSearchFragment)
        }
        binding.contentServices.setOnClickListener {
            findNavController().navigate(R.id.servicesFragment)
        }
        binding.contentSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
        binding.contentCart.setOnClickListener {
            findNavController().navigate(R.id.cartFragment)
        }
    }

}