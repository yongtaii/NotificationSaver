package com.rnd.jyong.notificationsaver.ui.permission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PermissionViewModel : ViewModel() {

    /**
     * 고객센터 전화 call LiveData
     */
    private val _goSetting = MutableLiveData<Boolean>()
    var goSetting: LiveData<Boolean> = _goSetting

    /**
     * Setting Button Clicked
     * */
    fun onClickSettingBtn(){
        _goSetting.value = true
    }

}