package com.stancefreak.pihakaseng.repository

import com.stancefreak.pihakaseng.local.PreferenceManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val manager: PreferenceManager
) {
    suspend fun storeVpState(pref: Int) {
        return manager.storeVpState(pref)
    }

    fun fetchVpState(): Flow<Int> {
        return manager.fetchVpState()
    }

    suspend fun removeVpState() {
        return manager.removeVpState()
    }
}