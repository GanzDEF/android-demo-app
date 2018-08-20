package com.test.xyz.demo.presentation.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableManager {
    private var compositeDisposable: CompositeDisposable? = null

    fun add(disposable: Disposable?) {
        if (disposable != null) {
            getCompositeDisposable()?.add(disposable)
        }
    }

    fun dispose() {
        compositeDisposable?.dispose()
    }

    private fun getCompositeDisposable(): CompositeDisposable? {
        if (compositeDisposable == null || compositeDisposable!!.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }

        return compositeDisposable
    }
}
