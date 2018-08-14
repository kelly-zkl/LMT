package com.wisec.scanner.utils;

import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 简单封装http请求
 * Created by qwe on 2018/5/15.
 */

public class HttpUtils {

    public static final String HTTP_URL = "http://119.23.232.135:49191/HuiAnApi/device/verify";

    public void get() {
        OkHttpClient client = new OkHttpClient();
        //构造Request对象
        // 采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder()
                .get()
                .url("http://www.jianshu.com/u/9df45b87cfdf")
                .build();
        Call call = client.newCall(request);
        //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseStr = response.body().string();

            }
        });
    }

    public void postParameter(View view) {
        OkHttpClient client = new OkHttpClient();
        //构建FormBody，传入要提交的参数
        FormBody formBody = new FormBody.Builder()
                .add("username", "initObject")
                .add("password", "initObject")
                .build();
        final Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();

            }
        });
    }
}
