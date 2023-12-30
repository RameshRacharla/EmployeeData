package com.ramesh.baseproject.ui.employeedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ramesh.baseproject.data.remote.response.DataResponse
import com.ramesh.baseproject.data.remote.response.EmployeeDetailsResponse
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
class EmployeeDetailsViewModel @Inject constructor(
    networkHelper: NetworkHelper, private val userRepository: UserRepository
) : BaseViewModel(networkHelper) {
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val getDataResponse: MutableLiveData<Resource<EmployeeDetailsResponse>> = MutableLiveData()
    override fun onCreate() {
    }

    fun getEmployeeDetails(id: String) = viewModelScope.launch {
        if (checkInternetConnectionWithMessage()) {
            loading.postValue(true)

            val response = userRepository.getEmployeeDetails(id)
            if (response.isSuccessful) {
                getDataResponse.postValue(Resource.success(response.body()))
            } else {
                responseFailureError("Something went Wrong!")
                responseFailureError(response.body()?.status.toString())
            }
            loading.postValue(false)
        }
    }
}