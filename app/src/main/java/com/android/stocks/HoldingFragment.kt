package com.android.stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel


class HoldingFragment : Fragment() {

    private val stockVm: HoldingFragmentViewModel by viewModel()
    lateinit var binding: HoldingFragmentBinding
    private val holdingAdapter by lazy {
        HoldingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = HoldingFragmentBinding.inflate(inflater, container, false).apply {
        binding = this
        binding.viewModel = stockVm
        stockVm.getHolding()
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.holdingsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = holdingAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        }

        stockVm.holdingLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                holdingAdapter.addHoldings(it)
            }
        }
    }

}