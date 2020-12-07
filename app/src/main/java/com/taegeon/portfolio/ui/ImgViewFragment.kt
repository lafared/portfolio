package com.taegeon.portfolio.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taegeon.portfolio.R
import com.taegeon.portfolio.databinding.ImgviewFragmentBinding
import com.taegeon.portfolio.util.GlideApp

class ImgViewFragment : Fragment() {

    companion object {
        fun newInstance() = ImgViewFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val viewBinding = ImgviewFragmentBinding.inflate(LayoutInflater.from(context), container, false)

        GlideApp.with(viewBinding.img.context)
            .load(arguments?.getString("imageUrl"))
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(viewBinding.img)

        val displaySitename = arguments?.getString("displaySitename")
        if (displaySitename?.trim() != "") {
            viewBinding.displaySitename.text = String.format(getString(R.string.display_sitename), displaySitename)
        }

        val datetime = arguments?.getString("datetime")
        if (datetime?.trim() != "") {
            viewBinding.datetime.text = String.format(getString(R.string.datetime), datetime)
        }

        return viewBinding.root
    }
}