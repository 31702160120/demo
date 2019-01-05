package com.example.a2233.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bian();

        denlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                den();

            }
        });

        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText( MainActivity.this,"测试",Toast.LENGTH_SHORT ).show();
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

    public void den(){
        final String user = dluser.getText().toString();
        String password = dlpassword.getText().toString();
        http http = new http();

        if (user.equals("")){
            Toast.makeText(MainActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals("")){
            Toast.makeText(MainActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        http.On_httpdenlu(user, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this,"链接失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.d("post", "onResponse: json="+json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        JsonDen denl = gson.fromJson(json,JsonDen.class);
                        if (denl.status.equals("登陆成功")){
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Homepage.class);
                            intent.putExtra("name",denl.name);
                            intent.putExtra("user",user);
                            intent.putExtra("md5user",denl.user);
                            startActivity(intent);

                        }
                        if(denl.status.equals("登陆失败")){
                            Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
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

