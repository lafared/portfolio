package com.taegeon.portfolio.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taegeon.portfolio.adapter.ImgListAdapter
import com.taegeon.portfolio.data.ImgData
import com.taegeon.portfolio.databinding.MainFragmentBinding
import com.taegeon.portfolio.net.DaumApiManager
import com.taegeon.portfolio.viewmodel.TmpItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val mainFragmentBinding = MainFragmentBinding.inflate(layoutInflater)

        val list = ArrayList<TmpItem>()
        for (idx in 1..100) {
            list.add(TmpItem(idx.toString()))
        }

        mainFragmentBinding.imgList.adapter = ImgListAdapter(list)
        mainFragmentBinding.test.setOnClickListener(
            View.OnClickListener {
                DaumApiManager.getService().requestSearchImg(keyword = "강아지").enqueue(object : Callback<ImgData> {
                    override fun onFailure(call: Call<ImgData>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                    override fun onResponse(call: Call<ImgData>, response: Response<ImgData>) {
                        Log.d("taegeon", "response : ${response?.body().toString()}")
                    }
                })
            }
        )

        return mainFragmentBinding.root
    }
}