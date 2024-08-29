package com.cmc.purithm.data.local.datasource

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.lang.reflect.Member
import javax.inject.Inject

internal class MemberDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = MEMBER_PREFS)
    private val firstRunDataStore = booleanPreferencesKey(MEMBER_FIRST_RUN)
    private val firstFilterRunDataStore = booleanPreferencesKey(MEMBER_FILTER_FIRST_RUN)

    suspend fun setFirstRun(flag: Boolean) {
        Log.d(TAG, "setFirstRun: start")
        context.dataStore.edit {
            it[firstRunDataStore] = flag
        }
    }

    suspend fun getFirstRun(): Boolean {
        Log.d(TAG, "getFirstRun: start")
        return context.dataStore.data.first().let {
            it[firstRunDataStore] ?: true
        }
    }

    suspend fun setFirstFilterRun(flag: Boolean) {
        Log.d(TAG, "setFirstFilterRun: start")
        context.dataStore.edit {
            it[firstFilterRunDataStore] = flag
        }
    }

    suspend fun getFirstFilterRun(): Boolean {
        Log.d(TAG, "getFirstFilterRun: start")
        return context.dataStore.data.first().let {
            it[firstFilterRunDataStore] ?: true
        }
    }

    companion object {
        private const val TAG = "MemberDataStore"
        private const val MEMBER_PREFS = "PURITHM_MEMBER_PREFS"
        private const val MEMBER_FIRST_RUN = "FIRST_RUN"
        private const val MEMBER_FILTER_FIRST_RUN = "FILTER_FIRST_RUN"
    }
}