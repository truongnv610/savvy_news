package com.savvy.domain.base.usecase

import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class FetchRemoteConfigUseCase @Inject constructor(
//    @Named(REMOTE_CONFIG_DEFAULTS) @XmlRes private val remoteConfigDefault: Int,
//    private val remoteConfig: FirebaseRemoteConfig,
//    private val firebaseRemoteConfigSettings: FirebaseRemoteConfigSettings
) {

    fun execute(): Completable {
        return Completable.create { completable ->
//            remoteConfig
//                .setConfigSettingsAsync(firebaseRemoteConfigSettings)
//                .continueWithTask {
//                    remoteConfig.setDefaultsAsync(remoteConfigDefault)
//                    remoteConfig.fetchAndActivate()
//                }
//                .addOnCompleteListener {
//                    completable.onComplete()
//                }
//                .addOnFailureListener {
//                    if (!completable.isDisposed) {
//                        completable.onError(it.cause ?: Throwable())
//                    }
//                }
            completable.onComplete()
        }
    }
}