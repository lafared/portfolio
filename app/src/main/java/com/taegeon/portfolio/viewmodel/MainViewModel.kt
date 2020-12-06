package com.taegeon.portfolio.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taegeon.portfolio.data.Documents
import com.taegeon.portfolio.data.ImgData
import com.taegeon.portfolio.net.DaumApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val documents: MutableLiveData<List<Documents>> by lazy {
        MutableLiveData<List<Documents>>()
    }
    val isSuccessful: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun runImgSearch(keyword: String) {
        if (keyword.trim() != "") {
            DaumApiManager.getService().requestSearchImg(keyword = keyword).enqueue(object :
                Callback<ImgData> {
                override fun onFailure(call: Call<ImgData>, t: Throwable) {
                    isSuccessful.value = false
                    Log.d("MainViewModel", "requestSearchImg, onFailure, $t")
                }

                override fun onResponse(call: Call<ImgData>, response: Response<ImgData>) {
                    if (response.isSuccessful) {
                        isSuccessful.value = true
                        Log.d("MainViewModel", "response : ${response.body().toString()}")
                        documents.value = response.body()?.documents ?: emptyList()
                        // val url = documents.value?.get(0)?.image_url
                    } else {
                        isSuccessful.value = false
                        Log.d("MainViewModel", "requestSearchImg, onResponse, isSuccessful is false, $response.errorBody()")
                    }
                }
            })
        }
    }
}