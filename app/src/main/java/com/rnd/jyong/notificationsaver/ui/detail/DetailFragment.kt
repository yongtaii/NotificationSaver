package com.rnd.jyong.notificationsaver.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rnd.jyong.notificationsaver.R
import com.rnd.jyong.notificationsaver.core.admob.AdmobManager
import com.rnd.jyong.notificationsaver.databinding.FragmentDetailBinding
import com.rnd.jyong.notificationsaver.ui.components.paging.DetailPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding : FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    @Inject lateinit var admobManager: AdmobManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            if(it == true){
                admobManager.showAd(requireActivity())
                findNavController().popBackStack()
            }
        }
    }

    private fun initView() {
        binding.toolbar.tvTitile.text = args.groupName

//        binding.recyclerView.layoutManager = LinearLayoutManager(context)
//        binding.recyclerView.adapter = GeneralBindAdapter<MainItemViewData, ItemDetailRecyclerviewRowBinding>(
//            R.layout.item_detail_recyclerview_row,
//            BR.data,
//            BR.position
//        )

        val detailPagingAdapter = DetailPagingAdapter()
//        mainPagingAdapter.setOnItemClickListener {
//            Log.d("yong1234","onClick!")
//            goDetail(it.groupName) }
        binding.bindAdapter(detailPagingAdapter = detailPagingAdapter)

        // Collect from the PagingData Flow in the ViewModel, and submit it to the
        // PagingDataAdapter.
        lifecycleScope.launch {
            // We repeat on the STARTED lifecycle because an Activity may be PAUSED
            // but still visible on the screen, for example in a multi window app
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.datas.collectLatest {
                    detailPagingAdapter.submitData(it)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                admobManager.showAd(requireActivity())
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )

    }
}

/**
 * Sets up the [RecyclerView] and binds [ArticleAdapter] to it
 */
private fun FragmentDetailBinding.bindAdapter(detailPagingAdapter: DetailPagingAdapter) {
    recyclerView.adapter = detailPagingAdapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, true)
//    val decoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
//    recyclerView.addItemDecoration(decoration)
}