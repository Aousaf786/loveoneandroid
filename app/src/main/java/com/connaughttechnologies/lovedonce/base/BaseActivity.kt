package com.connaughttechnologies.lovedonce.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.connaughttechnologies.lovedonce.BuildConfig
import com.connaughttechnologies.lovedonce.MainActivity
import com.connaughttechnologies.lovedonce.data.DataLayer
import com.connaughttechnologies.lovedonce.data.pref.RepositoryPref
import com.connaughttechnologies.lovedonce.data.remote.ApiService
import com.connaughttechnologies.lovedonce.data.remote.RepositoryRemote
import com.connaughttechnologies.lovedonce.ui.auth.AuthActivity
import com.connaughttechnologies.lovedonce.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseActivity<VM : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {

    lateinit var viewModel: VM
    lateinit var sharedViewModel: SharedViewModel
    abstract var viewModelClass: Class<VM>
    abstract var layoutId: Int
    lateinit var binding: B
    abstract var bindingVariable: Int

    //networking
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService
    lateinit var dataLayer: DataLayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = ViewModelProvider(this).get(viewModelClass)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
//        binding.lifecycleOwner = this
//        binding.setVariable(bindingVariable, viewModel)
//        binding.executePendingBindings()
        initViewModel()
        initNetwork()
        observeSharedViewModel()
        observeDataStore()
        observeViewModel()
        fetchToken()
    }

    abstract fun initViewModel()

    private fun initNetwork() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(ChuckerInterceptor.Builder(this).build())
        }
        okHttpClient = builder.build()
        retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create(
                Gson()
            )
        ).client(okHttpClient).build()
        apiService = retrofit.create(ApiService::class.java)
        dataLayer = DataLayer(RepositoryRemote(apiService), RepositoryPref(this), this)
    }

    open fun observeSharedViewModel() {

    }

    open fun observeDataStore() {
        dataLayer.getApiKey().asLiveData().observe(this, Observer {
            sharedViewModel.apiToken = it
        })
    }

    open fun observeViewModel() {
        viewModel.eventNavigateMain.observe(this, Observer {
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(this)
            }
        })
        viewModel.eventNavigateAuth.observe(this, Observer {
            Intent(this, AuthActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(this)
            }
        })


        viewModel.eventShowToast.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun fetchToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            sharedViewModel.fcmToken = token
            saveFcmToken(token)
            // Log and toast
        })
    }

    fun saveFcmToken(token: String) {
        lifecycleScope.launch {
            dataLayer.saveFcmToken(token)
        }
    }

    fun getFcmToken() = dataLayer.getFcmToken().asLiveData().value ?: ""

}