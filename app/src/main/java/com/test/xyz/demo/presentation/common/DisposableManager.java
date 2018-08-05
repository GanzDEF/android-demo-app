package com.test.xyz.demo.presentation.common;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposableManager {
    private CompositeDisposable compositeDisposable;

    public void add(Disposable disposable) {
        if (disposable != null) {
            getCompositeDisposable().add(disposable);
        }
    }

    public void dispose() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    private CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }

        return compositeDisposable;
    }
}
