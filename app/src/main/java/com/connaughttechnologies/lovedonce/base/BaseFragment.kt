package com.connaughttechnologies.lovedonce.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.MainActivity
import com.connaughttechnologies.lovedonce.data.DataLayer
import com.connaughttechnologies.lovedonce.data.models.responses.Service
import com.connaughttechnologies.lovedonce.data.pref.RepositoryPref
import com.connaughttechnologies.lovedonce.data.remote.RepositoryRemote
import java.lang.Exception
import java.util.*

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> : Fragment() {

    lateinit var viewModel: VM
    lateinit var sharedViewModel: SharedViewModel
    abstract var viewModelClass: Class<VM>
    abstract var layoutId: Int
    lateinit var binding: B
    abstract var bindingVariable: Int
    lateinit var repositoryPref: RepositoryPref


    abstract fun initViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(viewModelClass)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        repositoryPref = RepositoryPref(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<B>(inflater, layoutId, container, false)
        initViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSharedViewModel()
        observeViewModel()
        observeSingleLiveEvent()
    }

    open fun observeSharedViewModel() {}

    open fun observeViewModel() {
        viewModel.eventShowToast.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    open fun observeSingleLiveEvent() {}

    fun fetchProfileDetail() {
        sharedViewModel.eventExeApiGetProfileDetail.call()
    }

    fun getLastKnownLocation() {
        sharedViewModel.eventGetLastKnownLocation.call()
    }

    fun showToast(message: String) {
        viewModel.showToast(message)
    }

    fun navigateBack() {
        findNavController().popBackStack()
    }

    fun getTimeZone(): String {
        return TimeZone.getDefault().id
    }

    fun getMainActivity(): MainActivity? {
        return try {
            (requireActivity() as MainActivity)
        } catch (e: Exception) {
            null
        }
    }

    fun addToCart(service: Service) {
        sharedViewModel.listCart.add(service)
        sharedViewModel.liveListCart.value = sharedViewModel.listCart
    }

    fun removeFromCart(service: Service) {
        sharedViewModel.listCart.remove(service)
        sharedViewModel.liveListCart.value = sharedViewModel.listCart
    }
}