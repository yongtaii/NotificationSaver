package com.rnd.jyong.notificationsaver.core.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.gson.Gson
import com.rnd.jyong.notificationsaver.BuildConfig
import com.rnd.jyong.notificationsaver.ui.components.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

class RemoteConfigManager @Inject constructor() {

    lateinit var remoteConfig : FirebaseRemoteConfig
    lateinit var data : RemoteConfigData

    private val _invalidVersionEvent = SingleLiveEvent<Boolean>()
    var invalidVersionEvent: LiveData<Boolean> = _invalidVersionEvent

    init {

    }

    fun init(){
        initFirebaseRemoteConfig()
        fetchData()
    }

    private fun initFirebaseRemoteConfig(){
        Timber.d("initFirebaseRemoteConfig()")
        val builder = FirebaseRemoteConfigSettings.Builder().apply {
            minimumFetchIntervalInSeconds = FETCH_DEFAULT_INTEVAL_IN_SECONDS
        }
        remoteConfig = FirebaseRemoteConfig.getInstance().apply {
            setConfigSettingsAsync(builder.build())
        }
    }

    fun fetchData(){
        Timber.d("fetchData()")
        if(::remoteConfig.isInitialized){

            remoteConfig.fetchAndActivate()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        convertRemoteConfigString(remoteConfig.getString("configData"))
                        checkInvalidVersion()
                    }
                }
        }
    }

    private fun convertRemoteConfigString(remoteConfigString: String?) {

        if (remoteConfigString.isNullOrEmpty()) {
            data = RemoteConfigData()
            return
        }

        data = try {
            val gson = Gson()
            gson.fromJson(remoteConfigString, RemoteConfigData::class.java)
        } catch (e: Exception) {
            // 에러 시 기본 데이터 포멧으로 생성해서 시작한다.
            Timber.d(Log.getStackTraceString(e))
            RemoteConfigData()
        }

    }

    private fun checkInvalidVersion() {
        if (!::data.isInitialized) {
            return
        }
        val currentVersion: String = BuildConfig.VERSION_NAME
        val isExistInvalidVersion = data.disableVersions.contains(currentVersion)
        Timber.d("isExistInvalidVersion : $isExistInvalidVersion")
        Timber.d("data.disableVersions : ${data.disableVersions}")
        if (isExistInvalidVersion) {
            // 사용할 수 없는 버전일때만 이벤트를 뿌려라
            _invalidVersionEvent.value = true
        }
    }

    companion object {
        const val FETCH_DEFAULT_INTEVAL_IN_SECONDS = 0L
    }


}