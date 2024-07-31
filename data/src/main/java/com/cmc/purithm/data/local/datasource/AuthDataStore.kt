package com.cmc.purithm.data.local.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cmc.purithm.data.remote.ApiConfig
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class AuthDataStore @Inject constructor(
    private val context : Context
) {
    private val Context.dataStore by preferencesDataStore(AUTH_PREFS)
    private val accessTokenPrefs = stringPreferencesKey(AUTH_ACCESS_TOKEN_KEY)
    private val refreshTokenPrefs = stringPreferencesKey(AUTH_REFRESH_TOKEN_KEY)

    suspend fun getAccessToken() : String {
        return context.dataStore.data.first().let {
            it[accessTokenPrefs] ?: ""
        }
    }

    suspend fun setAccessToken(accessToken : String) {
        // FIXME : 토큰 저장은 단일로 변경해야함. 서버 안바빠지면 요청
        ApiConfig.ACCESS_TOKEN = accessToken
        context.dataStore.edit {
            it[accessTokenPrefs] = accessToken
        }
    }

    suspend fun getRefreshToken() : String {
        return context.dataStore.data.first().let {
            it[refreshTokenPrefs] ?: ""
        }
    }

    suspend fun setRefreshToken(refreshToken : String) {
        context.dataStore.edit {
            it[refreshTokenPrefs] = refreshToken
        }
    }

    companion object {
        private const val AUTH_PREFS = "PURITHM_AUTH_PREFS"
        private const val AUTH_ACCESS_TOKEN_KEY = "X-ACCESS-TOKEN"
        private const val AUTH_REFRESH_TOKEN_KEY = "X-REFRESH-TOKEN"
    }
}