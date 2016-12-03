package com.houxy.days.common.utils.rx;

import com.houxy.days.bean.Result;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Houxy on 2016/9/23.
 */

public class RxHelper {

    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        } ;
    }

    public static <T> Observable.Transformer<Result<T>, T> handleResult() {
        return new Observable.Transformer<Result<T>, T>() {
            @Override
            public Observable<T> call(Observable<Result<T>> resultObservable) {
                return resultObservable.flatMap(new Func1<Result<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(Result<T> tResult) {
                        if (!tResult.isError()) {
                            return createData(tResult.getResults());
                        } else {
                            return Observable.error(new Throwable("妹子不见了~"));
                        }
                    }
                });
            }
        } ;
    }

    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
