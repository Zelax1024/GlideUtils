package com.zelax.glidelibrary.progress;

import android.text.TextUtils;
import android.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by Zelax on 2021/2/4.
 */
public class ProgressManager {

    private static final Map<String, OnProgressListener> listenersMap = Collections.synchronizedMap(new HashMap<>());
    private static OkHttpClient okHttpClient;

    private ProgressManager() {
    }

    public static OkHttpClient getOkHttpClient() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(chain -> {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        return response.newBuilder()
                                .body(new ProgressResponseBody(request.url().toString(), LISTENER, response.body()))
                                .build();
                    })
                    .build();
        }
        return okHttpClient;
    }

    private static final ProgressResponseBody.InternalProgressListener LISTENER = (url, bytesRead, totalBytes) -> {
        OnProgressListener onProgressListener = getProgressListener(url);
        if (onProgressListener != null) {
            int percentage = (int) ((bytesRead * 1f / totalBytes) * 100f);
            boolean isComplete = percentage >= 100;
            onProgressListener.onProgress(isComplete, percentage, bytesRead, totalBytes);
            if (isComplete) {
                removeListener(url);
            }
        }
    };

    public static void addListener(String url, OnProgressListener listener) {
        if (!TextUtils.isEmpty(url) && listener != null) {
            listenersMap.put(url, listener);
            listener.onProgress(false, 1, 0, 0);
            Log.i("GlideUtils", "添加监听：" + url);
        }
    }

    public static void removeListener(String url) {
        if (!TextUtils.isEmpty(url)) {
            listenersMap.remove(url);
            Log.i("GlideUtils", "移除监听：" + url);
        }
    }

    public static OnProgressListener getProgressListener(String url) {
        if (TextUtils.isEmpty(url) || listenersMap.size() == 0) {
            return null;
        }

        return listenersMap.get(url);
    }
}
