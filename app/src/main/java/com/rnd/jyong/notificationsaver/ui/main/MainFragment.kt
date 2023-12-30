package com.rnd.jyong.notificationsaver.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.rnd.jyong.notificationsaver.R
import com.rnd.jyong.notificationsaver.core.admob.AdmobManager
import com.rnd.jyong.notificationsaver.databinding.FragmentMainBinding
import com.rnd.jyong.notificationsaver.ui.components.paging.MainPagingAdapter
import com.rnd.jyong.notificationsaver.utils.PopupUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding : FragmentMainBinding
    @Inject lateinit var admobManager: AdmobManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
//        binding.re

        val mainPagingAdapter = MainPagingAdapter()
        mainPagingAdapter.setOnItemClickListener {
            admobManager.showAd(requireActivity())
            goDetail(it.groupName)
        }
        mainPagingAdapter.setOnItemLongClickListener {
            PopupUtils.createSimplePopup(activity = requireActivity(),
                message = getString(R.string.popup_delete_message_title),
                okListener = { dialog, _ ->
                    dialog.dismiss()
                    viewModel.deleteGroup(it.groupName)
                }).show()
        }
        binding.bindAdapter(mainPagingAdapter = mainPagingAdapter)

        // Collect from the PagingData Flow in the ViewModel, and submit it to the
        // PagingDataAdapter.
        lifecycleScope.launch {
            // We repeat on the STARTED lifecycle because an Activity may be PAUSED
            // but still visible on the screen, for example in a multi window app
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.datas.collectLatest {
                    mainPagingAdapter.submitData(it)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.goDetail.observe(viewLifecycleOwner) {
            goDetail(it)
        }
        viewModel.showSetting.observe(viewLifecycleOwner) {
            if(it == true) {
                showPopupMenu()
            }
        }
    }

    /**
     * 상세페이지 이동
     * */
    private fun goDetail(groupName : String) {
        val direction = MainFragmentDirections.actionMainFragmentToDetailFragment(groupName)
        findNavController().navigate(direction)
    }

    private fun showPopupMenu(){
        PopupMenu(requireActivity(), binding.toolbar.ivRightBtn).apply {

            menuInflater.inflate(R.menu.menu_setting, this.menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_opensource -> {
                        startActivity(Intent(requireActivity(), OssLicensesMenuActivity::class.java))
                    }
                }
                false
            }

        }.show()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
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
private fun FragmentMainBinding.bindAdapter(mainPagingAdapter: MainPagingAdapter) {
    recyclerView.adapter = mainPagingAdapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
//    val decoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
//    recyclerView.addItemDecoration(decoration)
}