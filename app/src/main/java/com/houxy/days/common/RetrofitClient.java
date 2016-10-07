package com.houxy.days.common;

import android.text.TextUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.houxy.days.BuildConfig;
import com.houxy.days.C;
import com.houxy.days.DaysApplication;
import com.houxy.days.R;
import com.houxy.days.common.utils.NetUtil;
import com.houxy.days.common.utils.SPUtil;
import com.houxy.days.common.utils.ToastUtils;
import com.houxy.days.common.utils.rx.RxHelper;
import com.houxy.days.modules.welfare.bean.MeiZhi;
import com.houxy.days.modules.welfare.bean.Result;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Houxy on 2016/9/22.
 */

public class RetrofitClient {

    private static ApiInterface apiService = null;
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;

    /**
     * 初始化
     */
    public static void init() {
        initOkHttp();
        initRetrofit();
        apiService = retrofit.create(ApiInterface.class);
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static ApiInterface getApiService() {
        if (apiService == null) {
            throw new NullPointerException("get apiService must be called after init");
        }
        return apiService;
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            //设置为body可在log看到完整的json数据
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
            builder.networkInterceptors().add(new StethoInterceptor());
        }
        CookieJar mCookieJar = new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                if( null != cookies){
                    return cookies;
                }else {
                    return  new ArrayList<>();
                }
//                return cookies != null ? cookies : new ArrayList<>();
            }
        };

        Interceptor mTokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                if (TextUtils.isEmpty(SPUtil.getToken()) || !TextUtils.isEmpty(originalRequest.header(C.TOKEN_HEADER))) {
                    return chain.proceed(originalRequest);
                }
                Request authorised = originalRequest.newBuilder()
                        .header("Authorization", "Token " + SPUtil.getToken())
                        .build();
                return chain.proceed(authorised);
            }
        };

        // 缓存 http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(DaysApplication.cacheDir, C.CACHE_PATH);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkConnected(DaysApplication.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);

                if (NetUtil.isNetworkConnected(DaysApplication.getContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Content-Encoding", "gzip")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }

                return response;
            }
        } ;

        builder.addNetworkInterceptor(mTokenInterceptor);
        builder.cookieJar(mCookieJar);
        builder.cache(cache).addInterceptor(cacheInterceptor);
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private static void initRetrofit() {

//        String host = BuildConfig.DEBUG ? C.HOST_DEBUG : C.HOST;
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static void disposeFailureInfo(Throwable t) {

        if (t.toString().contains("GaiException")
                || t.toString().contains("SocketTimeoutException")
                || t.toString().contains("UnknownHostException")) {
            ToastUtils.showLong(R.string.no_network);
        } else if (t.toString().contains("ConnectException")) {
            ToastUtils.showLong(R.string.fail_connect);
        }
    }

    public Observable<Result<List<MeiZhi>>> getMeiZhi(int page){
        return apiService.getMeiZhi(page).compose(RxHelper.<Result<List<MeiZhi>>>rxSchedulerHelper());
    }

    public Observable<List<String>> getWelfareCount(){
        return apiService.getWelfareCount().compose(RxHelper.<Result<List<String>>>rxSchedulerHelper())
                .compose(RxHelper.<List<String>>handleResult());
    }

}
