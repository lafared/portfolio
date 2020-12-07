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
import com.taegeon.portfolio.adapter.ImgListAdapter
import com.taegeon.portfolio.databinding.MainFragmentBinding
import com.taegeon.portfolio.listener.FragmentListener
import com.taegeon.portfolio.listener.ImgListScrollListener
import com.taegeon.portfolio.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class MainFragment : Fragment(), FragmentListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val compositeDisposable = CompositeDisposable()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.imgList.adapter = ImgListAdapter(this)

        mainViewModel.isSuccessful.observe(viewLifecycleOwner, {
            if(!it) {
                Toast.makeText(context, R.string.search_fail, Toast.LENGTH_SHORT).show()
            } else {
                val im = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
            }
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

    override fun onChangeImgFragment(imageUrl: String, displaySitename: String, datetime: String) {
        val bundle = Bundle()
        bundle.putString("imageUrl", imageUrl)
        bundle.putString("displaySitename", displaySitename)
        bundle.putString("datetime", datetime)

        val imgViewFragment = ImgViewFragment.newInstance()
        imgViewFragment.arguments = bundle

        activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, imgViewFragment)
                ?.addToBackStack(null)
                ?.commit()
    }
}