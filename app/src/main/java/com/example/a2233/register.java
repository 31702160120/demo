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
import java.text.BreakIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class register extends AppCompatActivity {
    private TextView zcuser;
    private  TextView zcname;
    private  TextView zcpassword;
    private Button zhuce;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_register);
        bian();
        handler =new Handler(){
            @Override
            public void handleMessage( Message msg ) {
               if(msg.what==1){
                   String status = (String) msg.obj;
                   if(status.equals("注册成功")){
                       Toast.makeText(register.this,"注册成功",Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(register.this,MainActivity.class);
                       startActivity(intent);
                   }
                   if(status.equals("用户名重复")){
                       Toast.makeText(register.this,"账号已存在",Toast.LENGTH_SHORT).show();
                   }
               }else if(msg.what==0){
                   Toast.makeText(register.this, "糟糕!网络好像出问题了", Toast.LENGTH_SHORT).show();
               }
            }
        };
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpZc();
            }
        });
    }

    public void bian(){
        zcuser = (TextView) findViewById(R.id.zc_user);
        zcname = (TextView) findViewById(R.id.zc_name);
        zcpassword = (TextView) findViewById(R.id.zc_password1);
        zhuce = (Button) findViewById(R.id.btn_zc);

    }


    public void httpZc(){
        final String user = zcuser.getText().toString().trim();
        final String name = zcname.getText().toString().trim();
        final String password = zcpassword.getText().toString().trim();
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(user);
        if(!m.matches()){
            Toast.makeText(register.this,"账户必须为数字",Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.equals("")){
            Toast.makeText(register.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (user.equals("")){
            Toast.makeText(register.this,"账号不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")){
            Toast.makeText(register.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(){
            @Override
            public void run() {
                http zc = new http();
                zc.On_httpzhuce(user, name, password, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message mag = new Message();
                        mag.what = 0;
                        handler.sendMessage( mag );
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final String zcJson = response.body().string();
                        Gson gson = new Gson();
                        Jsonzc add = gson.fromJson(zcJson,Jsonzc.class);
                        Message mag = new Message();
                        mag.obj=add.status;
                        mag.what = 1;
                        handler.sendMessage( mag );
                    }
                });
            }
        }.start();
    }

    public  class Jsonzc{
        private  String status;

        Jsonzc(String status){
            this.status = status;
        }


    }
}

