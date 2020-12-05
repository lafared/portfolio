package com.taegeon.portfolio.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taegeon.portfolio.adapter.ImgListAdapter
import com.taegeon.portfolio.databinding.MainFragmentBinding
import com.taegeon.portfolio.viewmodel.MainViewModel
import com.taegeon.portfolio.viewmodel.TmpItem

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val mainFragmentBinding = MainFragmentBinding.inflate(layoutInflater)

        val list = ArrayList<TmpItem>()
        for (idx in 1..100) {
            list.add(TmpItem(idx.toString()))
        }

        mainFragmentBinding.imgList.adapter = ImgListAdapter(list)

        return mainFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

}