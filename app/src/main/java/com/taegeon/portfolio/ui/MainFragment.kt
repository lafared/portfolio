package com.taegeon.portfolio.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.taegeon.portfolio.adapter.ImgListAdapter
import com.taegeon.portfolio.data.ImgData
import com.taegeon.portfolio.databinding.MainFragmentBinding
import com.taegeon.portfolio.net.DaumApiManager
import com.taegeon.portfolio.viewmodel.TmpItem
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class MainFragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val mainFragmentBinding = MainFragmentBinding.inflate(layoutInflater)

        val list = ArrayList<TmpItem>()
        for (idx in 1..100) {
            list.add(TmpItem(idx.toString()))
        }

        mainFragmentBinding.imgList.adapter = ImgListAdapter(list)

        val observableEditText = mainFragmentBinding.inputImgName.afterTextChangeEvents()
        compositeDisposable.add(
            observableEditText.debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe { runImgSearch(it.view.text.toString()) }
        )

        return mainFragmentBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun runImgSearch(keyword: String) {
        if (keyword.trim() != "") {
            DaumApiManager.getService().requestSearchImg(keyword = keyword).enqueue(object : Callback<ImgData> {
                override fun onFailure(call: Call<ImgData>, t: Throwable) {
                    Toast.makeText(context, "search fail, onFailure", Toast.LENGTH_SHORT).show()
                    Log.d("MainFragment", "requestSearchImg, onFailure, $t")
                }

                override fun onResponse(call: Call<ImgData>, response: Response<ImgData>) {
                    if (response.isSuccessful) {
                        Log.d("MainFragment", "response : ${response.body().toString()}")
                    } else {
                        Toast.makeText(context, "search fail, onResponse, isSuccessful is false", Toast.LENGTH_SHORT).show()
                        Log.d("MainFragment", "requestSearchImg, onResponse, isSuccessful is false, $response.errorBody()")
                    }
                }
            })
        }
    }
}