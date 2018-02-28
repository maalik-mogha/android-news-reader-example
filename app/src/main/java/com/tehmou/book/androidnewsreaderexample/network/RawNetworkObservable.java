package com.tehmou.book.androidnewsreaderexample.network;

import android.util.Log;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RawNetworkObservable {
    private static final String TAG = RawNetworkObservable.class.getSimpleName();

    private RawNetworkObservable() {

    }

    public static Observable<Response> create(final String url) {
        return Observable.create(
                new ObservableOnSubscribe<Response>() {
                    OkHttpClient client = new OkHttpClient();

                     @Override
                     public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
                         try {
                             Response response = client.newCall(new Request.Builder().url(url).build()).execute();
                             emitter.onNext(response);
                             emitter.onComplete();
                             if (!response.isSuccessful()) emitter.onError(new Exception("error"));
                         } catch (IOException e) {
                             emitter.onError(e);
                         }

                     }
        })
        .subscribeOn(Schedulers.io());
    }

    public static Observable<String> getString(String url) {
        return create(url)
                .map(response -> {
                    try {
                        return response.body().string();
                    } catch (Exception e) {
                        Log.e(TAG, "Error reading url " + url);
                    }
                    return null;
                });
    }
}
