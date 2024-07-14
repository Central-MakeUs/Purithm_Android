package com.cmc.purithm

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        Log.d(TAG, "onCreate: hashKey = ${Utility.getKeyHash(this)}")
    }

    companion object {
        private const val TAG = "GlobalApplication"
    }
}