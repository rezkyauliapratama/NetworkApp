package com.rezkyaulia.android.light_optimization_data;

import android.content.Context;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 7/4/2017.
 */

public class NetworkClient {

    private Context mContext;

    private static class SingletonHolder{
        public static NetworkClient singletonInstance =
                new NetworkClient();
    }


    // Providing Global point of access
    @Contract(pure = true)
    public static NetworkClient getInstance() {
        return SingletonHolder.singletonInstance;
    }

    private OkHttpClient sHttpClient;

    @NonNull
    public <T> HttpCore<T> as (Class<T> t) throws Exception{
        if (null == SingletonHolder.singletonInstance){
            throw new IOException("Instance is null");
        }

        if (sHttpClient == null){
            throw new IOException("OkhttpClient is null");
        }
        Timber.e("Initialize HTTP CORE");
        HttpCore<T> core = new HttpCore<T>(sHttpClient,t);
        return core;
    }

    public void client(Context context) {
        this.sHttpClient = new OkHttpClient().newBuilder()
                .cache(Utils.init().getCache(context, NConstants.init().MAX_CACHE_SIZE, NConstants.init().CACHE_DIR_NAME))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

}
