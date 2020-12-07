package com.taegeon.portfolio.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taegeon.portfolio.data.Documents
import com.taegeon.portfolio.data.ImgData
import com.taegeon.portfolio.listener.SearchStatusListener
import com.taegeon.portfolio.net.DaumApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val documents: MutableLiveData<ArrayList<Documents>> by lazy {
        MutableLiveData<ArrayList<Documents>>()
    }
    val isSuccessful: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val searchStatusListener: MutableLiveData<SearchStatusListener> by lazy {
        MutableLiveData<SearchStatusListener>()
    }
    private val requestPage: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun runImgSearch(daumApiManager: DaumApiManager, keyword: String, loadMore: Boolean) {
        if (keyword.trim() != "") {
            searchStatusListener.value?.onSearchStatusChange(false)
            val page = requestPage.value ?: 0
            Log.d("MainViewModel", "requestPage : ${page + 1}")
            // very slow
            requestPage.postValue((requestPage.value ?: 0) + 1)

            daumApiManager.retrofitService.requestSearchImg(keyword = keyword, page = page + 1).enqueue(object :
                Callback<ImgData> {
                override fun onFailure(call: Call<ImgData>, t: Throwable) {
                    isSuccessful.value = false
                    searchStatusListener.value?.onSearchStatusChange(true)
                    Log.d("MainViewModel", "requestSearchImg, onFailure, $t")
                }

                override fun onResponse(call: Call<ImgData>, response: Response<ImgData>) {
                    if (response.isSuccessful) {
                        isSuccessful.value = true
                        val arrayList: ArrayList<Documents> = ArrayList()
                        Log.d("MainViewModel", "response : ${response.body().toString()}")

                        if (loadMore) {
                            val newList = ArrayList<Documents>()
                            documents.value?.let { newList.addAll(it) }
                            newList.addAll(response.body()?.documents ?: emptyList())
                            documents.value = newList
                        } else {
                            arrayList.addAll(response.body()?.documents ?: emptyList())
                            documents.value = arrayList
                        }
                        searchStatusListener.value?.onSearchStatusChange(true)
                    } else {
                        isSuccessful.value = false
                        searchStatusListener.value?.onSearchStatusChange(true)
                        Log.d("MainViewModel", "requestSearchImg, onResponse, isSuccessful is false, $response.errorBody()")
                    }
                }
            })
        }
    }

    fun initailize() {
        requestPage.value = 0
        documents.value = ArrayList()
    }
}