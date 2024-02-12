package com.alxabr.market_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

internal class StubFragment : Fragment() {

    companion object {
        private const val TITLE_RES_ARG = "StubFragment.TITLE_RES_ARG"

        fun newInstance(titleRes: Int): Fragment =
            StubFragment().apply {
                arguments = Bundle().apply {
                    putInt(TITLE_RES_ARG, titleRes)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.market_feature_stub_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.title).setText(requireArguments().getInt(TITLE_RES_ARG))
    }
}