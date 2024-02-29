package com.rnd.jyong.notificationsaver.core.admob

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.rnd.jyong.notificationsaver.BuildConfig
import com.rnd.jyong.notificationsaver.data.preference.JPreference
import com.rnd.jyong.notificationsaver.datastore.DataStoreKey
import com.rnd.jyong.notificationsaver.datastore.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdmobManager @Inject constructor() {

    private var mInterstitialAd: InterstitialAd? = null

    fun init(activityContext : Activity) {
        Timber.d("init()")

        if (BuildConfig.DEBUG) return

        MobileAds.initialize(activityContext) {
            loadAd(activityContext)
        }
    }

    fun loadAd(activityContext : Activity) {

        Timber.d("loadAd()")

        val adRequest = AdRequest.Builder().build()

//        val testAdUnitId = "ca-app-pub-3940256099942544/1033173712" // test 진입광고 ID
        val adUnitId = "ca-app-pub-4557410077390092/9402152657" // jeonyt0510 진입광고 ID

        InterstitialAd.load(activityContext,adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Timber.d("Ad was failed")
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Timber.d("Ad was loaded")
                mInterstitialAd = interstitialAd
            }
        })


    }

    fun showAd(activityContext : Activity) {

        Timber.d("showAd()")

        if(mInterstitialAd == null){
            Timber.d("mInterstitialAd is null")
            loadAd(activityContext)
            return
        }

        Timber.d("isAdmobTimePassed : ${isAdmobTimePassed(activityContext)}")

        if(BuildConfig.DEBUG || !isAdmobTimePassed(activityContext)) return

//        if(!isAdmobTimePassed(activityContext)) return

        CoroutineScope(Dispatchers.IO).launch {DataStoreManager.save(activityContext, DataStoreKey.LAST_ADMOB_TIME, System.currentTimeMillis()) }


        mInterstitialAd?.show(activityContext)

        loadAd(activityContext)

    }

    /**
     * 애드몹 미표출 시간이 지났는지 여부
     * @return true : admob 표출  , false : admob 미표출
     * */
    private fun isAdmobTimePassed(activityContext: Activity) : Boolean{
        val lastAdmobTime = runBlocking {DataStoreManager.get(activityContext, DataStoreKey.LAST_ADMOB_TIME, 0L) }

        val currentTime = System.currentTimeMillis()

        val passedSeconds = (currentTime -lastAdmobTime) / 1000;
//        val passedMinutes: Long = (currentTime - lastAdmobTime) / (1000 * 60)
        return passedSeconds >= 30
    }

}