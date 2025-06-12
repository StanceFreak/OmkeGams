package com.stancefreak.pihakaseng.repository

import javax.inject.Inject

class AppRepository @Inject constructor(
    private val remote: RemoteRepository
) {
}