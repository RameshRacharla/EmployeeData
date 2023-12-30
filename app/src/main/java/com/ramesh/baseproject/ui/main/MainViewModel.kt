package com.ramesh.baseproject.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ramesh.baseproject.data.remote.response.DataResponse
import com.ramesh.baseproject.data.repository.UserRepository
import com.ramesh.baseproject.ui.base.BaseViewModel
import com.ramesh.baseproject.utils.common.Resource
import com.ramesh.baseproject.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 *  Author : @Ramesh Racharla
 *
 * */
@HiltViewModel
class MainViewModel @Inject constructor(
    networkHelper: NetworkHelper, private val userRepository: UserRepository
) : BaseViewModel(networkHelper) {
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val xAuth = userRepository.getAccessToken()
    val getDataResponse: MutableLiveData<Resource<DataResponse>> = MutableLiveData()
    override fun onCreate() {
    }

    fun getEmployeeData() = viewModelScope.launch {
        if (checkInternetConnectionWithMessage()) {
            loading.postValue(true)

            val response = userRepository.getData(
                ""
            )
            if (response.isSuccessful) {
                getDataResponse.postValue(Resource.success(response.body()))
            } else {
                responseFailureError("Something went Wrong!")
                responseFailureError(response.body()?.message.toString())
            }
            loading.postValue(false)
        }
    }
}