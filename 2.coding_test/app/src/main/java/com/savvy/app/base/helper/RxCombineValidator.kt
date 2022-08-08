package com.savvy.app.base.helper

import io.reactivex.rxjava3.core.Observable

class RxCombineValidator(private vararg val observables: Observable<Boolean>) {
    /**
     * Combiner for your validations observable, which allows change UI if all observables emmit <b>true</b>
     * (use-full for change button state)
     */
    fun asObservable(): Observable<Boolean> {
        return Observable.combineLatest(observables.toMutableList()) { arrays ->
            arrays.forEach {
                if (!(it as Boolean)) {
                    return@combineLatest false
                }
            }
            return@combineLatest true
        }
    }
}
