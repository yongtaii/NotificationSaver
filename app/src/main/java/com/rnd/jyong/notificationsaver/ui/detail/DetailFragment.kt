package com.rnd.jyong.notificationsaver.ui.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.rnd.jyong.notificationsaver.BR
import com.rnd.jyong.notificationsaver.R
import com.rnd.jyong.notificationsaver.databinding.FragmentDetailBinding
import com.rnd.jyong.notificationsaver.databinding.ItemDetailRecyclerviewRowBinding
import com.rnd.jyong.notificationsaver.ui.components.GeneralBindAdapter
import com.rnd.jyong.notificationsaver.ui.main.data.MainItemViewData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding : FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        viewModel.initData(args.groupName)
        viewModel.onPrev.observe(viewLifecycleOwner) {
            if(it == true) findNavController().popBackStack()
        }
    }

    private fun initView() {

        binding.toolbar.tvTitile.text = args.groupName

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = GeneralBindAdapter<MainItemViewData, ItemDetailRecyclerviewRowBinding>(
            R.layout.item_detail_recyclerview_row,
            BR.data,
            BR.position
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )

    }
}