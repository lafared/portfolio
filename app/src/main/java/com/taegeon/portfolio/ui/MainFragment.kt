package com.taegeon.portfolio.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.taegeon.portfolio.R
import com.taegeon.portfolio.adapter.DataBindingAdapters
import com.taegeon.portfolio.databinding.MainFragmentBinding
import com.taegeon.portfolio.listener.ImgListScrollListener
import com.taegeon.portfolio.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class MainFragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.main_fragment, container, false)
        binding.mainViewModel = mainViewModel

        mainViewModel.isSuccessful.observe(viewLifecycleOwner, {
            if(!it) {
                Toast.makeText(context, R.string.search_fail, Toast.LENGTH_SHORT).show()
            } else {
                val im = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
            }
        })
        mainViewModel.documents.observe(viewLifecycleOwner, {
            DataBindingAdapters.BindImgNList(binding.imgList, mainViewModel.documents)
            DataBindingAdapters.BindImgNEmptyTxt(binding.noSearchResult, mainViewModel.documents)
        })

        compositeDisposable.add(
            binding.inputImgName.afterTextChangeEvents()
                .subscribe { mainViewModel.initailize() }
        )
        compositeDisposable.add(
            binding.inputImgName.afterTextChangeEvents()
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe { mainViewModel.runImgSearch(it.view.text.toString(), false) }
        )

        val imgListScrollListener = ImgListScrollListener(
            { mainViewModel.runImgSearch(binding.inputImgName.text.toString(), true) },
            binding.imgList.layoutManager as GridLayoutManager)
        binding.imgList.addOnScrollListener(imgListScrollListener)
        mainViewModel.searchStatusListener.value = imgListScrollListener

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}