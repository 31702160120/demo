package com.example.a2233;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;

import com.example.a2233.demo.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView dluser;
    private  TextView dlpassword;
    private Button denlu;
    private  Button zhuce;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        bian();
        handler = new Handler( ){
            @Override
            public void handleMessage( Message msg ) {
                if (msg.what==1){
                    String json = (String) msg.obj;
                    Gson gson = new Gson();
                    JsonDen denl = gson.fromJson(json,JsonDen.class);
                    if (denl.status.equals("登陆成功")){
                        final String user = dluser.getText().toString();
                        Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,Homepage.class);
                        intent.putExtra("name",denl.name);
                        intent.putExtra("user", user);
                        intent.putExtra("md5user",denl.user);
                        startActivity(intent);
                        MainActivity.this.finish();

                    }
                    if(denl.status.equals("登陆失败")){
                        Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                    }
                }else if(msg.what==0){
                    Toast.makeText(MainActivity.this, "糟糕!网络好像出问题了", Toast.LENGTH_SHORT).show();
                }
            }
        };

        denlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xian();

            }
        });

        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText( MainActivity.this,"欢迎注册",Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent(MainActivity.this,register.class);
                startActivity(intent);
            }
        });
    }

    public void bian(){
        dluser = (TextView) findViewById(R.id.et_name);
        dlpassword = (TextView) findViewById(R.id.et_password);
        denlu = (Button) findViewById(R.id.button);
        zhuce = (Button) findViewById(R.id.button2);
    }

    public  void xian(){
        final String user = dluser.getText().toString();
        final String password = dlpassword.getText().toString();
        if (user.equals("")){
            Toast.makeText(MainActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals("")){
            Toast.makeText(MainActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(){
            @Override
            public void run() {
                http ht = new http();
                ht.On_httpdenlu(user, password, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message mag = new Message();
                        mag.what = 0;
                        handler.sendMessage( mag );
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String json = response.body().string();
                        Message mag = new Message();
                        mag.obj=json;
                        mag.what = 1;
                        handler.sendMessage( mag );

                    }
                });
            }
        }.start();
    }

    public class JsonDen{
        private String user;
        private  String name;
        private  String status;

        JsonDen(String user,String name,String status){
            this.user = user;
            this.name = name;
            this.status = status;
        }


    }
}

