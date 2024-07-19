package com.cmc.purithm.data.local.datasource

import android.content.Context
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
    @ApplicationContext private val context : Context
){
    private val Context.dataStore by preferencesDataStore(name = MEMBER_PREFS)
    private val firstRunDataStore = booleanPreferencesKey(MEMBER_FIRST_RUN)

    suspend fun setFirstRun(){
        context.dataStore.edit {
            it[firstRunDataStore] = false
        }
    }

    suspend fun getFirstRun() : Boolean{
        return context.dataStore.data.first().let {
            it[firstRunDataStore] ?: true
        }
    }

    companion object {
        private const val MEMBER_PREFS = "PURITHM_MEMBER_PREFS"
        private const val MEMBER_FIRST_RUN = "FIRST_RUN"
    }
}