package com.example.a2233.demo;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class http {

    public static void On_httpdenlu(String user, String password , Callback callback){
        OkHttpClient client = new OkHttpClient();

       RequestBody body = new FormBody.Builder()
                .add("user",user)
                .add("password",password)
                .build();

        Request request = new Request.Builder()
                .url("http://123.207.85.214/chat/login.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);

    }


    public static void On_httpzhuce(String user, String name ,String password, Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("user",user)
                .add("name",name)
                .add("password",password)
                .build();

        Request request = new Request.Builder()
                .url("http://123.207.85.214/chat/register.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);

    }

    public static void On_httpyonhu( Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url("http://123.207.85.214/chat/member.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);

    }

    public static void On_httpnews(String user, String chat, Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("user",user)
                .add("chat",chat)
                .build();

        Request request = new Request.Builder()
                .url("http://123.207.85.214/chat/chat1.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);

    }
}
