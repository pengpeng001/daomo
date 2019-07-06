package com.baiwei.dome01.net;

import com.baiwei.dome01.api.API;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofigManager {
    private static volatile RetrofigManager retrofigManager;

    private Retrofit mRetrofit;

    private OkHttpClient mClient;

    private RetrofigManager (){
        initRetrofigManager();

    }

    private void initRetrofigManager() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(httpLoggingInterceptor);
        builder.addInterceptor(new LoggingInterceptor());

        builder.readTimeout(20 , TimeUnit.SECONDS);
        builder.writeTimeout(20 , TimeUnit.SECONDS);
        builder.connectTimeout(20 , TimeUnit.SECONDS);
        mClient = builder.build();

        mRetrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }
    public static RetrofigManager getInstance(){
        if (retrofigManager == null){
            synchronized (RetrofigManager.class){
                if (retrofigManager == null){
                    retrofigManager = new RetrofigManager();
                }

            }
        }
        return retrofigManager;
    }
    public <T> T reCrest(Class<T> service){
        return mRetrofit.create(service);
    }


    private class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Request.Builder builder = request.newBuilder();
            builder.addHeader("userId" , "2982");
            builder.addHeader("sessionId" , "15623970705652982");

            Request request1 = builder.build();

            Response response = chain.proceed(request1);


            return response;
        }
    }
}
