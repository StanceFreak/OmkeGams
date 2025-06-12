package com.stancefreak.pihakaseng.repository

import com.stancefreak.pihakaseng.network.NetworkHelper
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val helper: NetworkHelper
) {
}