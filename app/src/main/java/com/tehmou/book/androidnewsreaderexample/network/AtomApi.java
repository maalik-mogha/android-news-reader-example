package com.tehmou.book.androidnewsreaderexample.network;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Url;

public interface AtomApi {
    @GET
    Call<ResponseBody> getFeed(@Url String url);
}
