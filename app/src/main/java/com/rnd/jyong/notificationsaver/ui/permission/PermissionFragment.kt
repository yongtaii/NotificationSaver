package com.rnd.jyong.notificationsaver.ui.permission

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rnd.jyong.notificationsaver.R
import com.rnd.jyong.notificationsaver.data.preference.JPreference
import com.rnd.jyong.notificationsaver.databinding.FragmentPermissionBinding
import com.rnd.jyong.notificationsaver.utils.SystemUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : Fragment() {

    private val viewModel: PermissionViewModel by viewModels()
    private lateinit var binding : FragmentPermissionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_permission, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.goSetting.observe(viewLifecycleOwner) {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        }
    }

    override fun onResume() {
        super.onResume()
        if(SystemUtils.isMyPackageNotificationListenerEnabled(context)) {
            findNavController().navigate(R.id.action_permissionFragment_to_mainFragment)
        }
    }

}