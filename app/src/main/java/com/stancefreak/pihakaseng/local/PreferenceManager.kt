package com.stancefreak.pihakaseng.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceManager(private val context: Context) {

    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("app_prefs")
        private val ITEM_POSITION = intPreferencesKey("ITEM_POSITION")
    }

    fun fetchVpState(): Flow<Int> {
        return context.datastore.data.map {
            it[ITEM_POSITION] ?: 0
        }
    }

    suspend fun storeVpState(pref: Int) {
        context.datastore.edit {
            it[ITEM_POSITION] = pref
        }
    }

    suspend fun removeVpState() {
        context.datastore.edit {
            it.clear()
        }
    }

}