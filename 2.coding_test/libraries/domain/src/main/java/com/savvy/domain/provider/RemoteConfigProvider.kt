package com.savvy.domain.provider

import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigProvider @Inject constructor(
    private val moshi: Moshi
)