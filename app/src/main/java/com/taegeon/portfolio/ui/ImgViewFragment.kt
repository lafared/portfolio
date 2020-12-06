package com.taegeon.portfolio.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taegeon.portfolio.R
import com.taegeon.portfolio.databinding.ImgviewFragmentBinding
import com.taegeon.portfolio.viewmodel.MainViewModel

class ImgViewFragment : Fragment() {

    companion object {
        fun newInstance() = ImgViewFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val viewBinding = ImgviewFragmentBinding.inflate(LayoutInflater.from(context), container, false)
//        return inflater.inflate(R.layout.imgview_fragment, container, false)
        return viewBinding.root
    }

}