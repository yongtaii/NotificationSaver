package com.rnd.jyong.notificationsaver.core.admob

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
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

    companion object {
        const val IS_TEST = false
        private val FRONT_BANNER_UNIT_ID = if(IS_TEST){
            "ca-app-pub-3940256099942544/1033173712" // test 진입광고 ID
        }else{
            "ca-app-pub-4557410077390092/9402152657" // jeonyt0510 진입광고 ID
        }
//        private val BOTTOM_BANNER_UNIT_ID = if(IS_TEST){
//            "ca-app-pub-3940256099942544/6300978111" // test 하단 배너광고 ID
//        }else{
//            "ca-app-pub-3940256099942544/6300978111" // jeonyt0510 하단 배너광고 ID
//        }
    }

    /**
     * init ADMOB
     * */
    fun init(activityContext : Activity) {
        Timber.d("init()")

        MobileAds.initialize(activityContext) {
            loadFrontAd(activityContext)
        }
    }

    /**
     * 진입광고 로드
     * */
    fun loadFrontAd(activityContext : Activity) {

        Timber.d("loadAd()")

        val adRequest = AdRequest.Builder().build()


        InterstitialAd.load(activityContext,FRONT_BANNER_UNIT_ID, adRequest, object : InterstitialAdLoadCallback() {
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

    /**
     * 진입광고 표시
     * */
    fun showFrontAd(activityContext : Activity) {

        Timber.d("showAd()")

        if(mInterstitialAd == null){
            Timber.d("mInterstitialAd is null")
            loadFrontAd(activityContext)
            return
        }

        Timber.d("isAdmobTimePassed : ${isAdmobTimePassed(activityContext)}")

        if(!isAdmobTimePassed(activityContext)) return

//        if(!isAdmobTimePassed(activityContext)) return

        CoroutineScope(Dispatchers.IO).launch {DataStoreManager.save(activityContext, DataStoreKey.LAST_ADMOB_TIME, System.currentTimeMillis()) }


        mInterstitialAd?.show(activityContext)

        loadFrontAd(activityContext)

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

    /**
     * Banner 광고를 로드한다
     * */
    fun loadBannerAdView(adView : AdView) = adView.loadAd(AdRequest.Builder().build())
//        .apply {
//            setAdSize(AdSize.BANNER)
//            adUnitId = "ca-app-pub-3940256099942544/6300978111" }
//        .run {
//            loadAd(AdRequest.Builder().build())
//        }

}