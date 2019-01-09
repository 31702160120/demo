package com.example.a2233;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a2233.demo.INfo.INterInFor;
import com.example.a2233.demo.INfo.weiInfo;
import com.example.a2233.demo.R;
import com.example.a2233.demo.menu.LiaBuJu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by panchengjia on 2016/12/24.
 */
public class InterFlow extends Fragment {
    private Button Send;
    private EditText ltext;
    private String user;
    private String chat;
    private String name;
    private Handler handler;
    private com.example.a2233.demo.INfo.weiInfo weiInfo;
    private List<weiInfo> weiInfos;
    private LinearLayout loading;
    private ListView lvNews;
    private String jchat;
    private RecyclerView msgRecyclerView;
    private LiaBuJu adapter;
    private List<INterInFor> msgList = new ArrayList<INterInFor>();

    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.activity_inter_flow, container, false );
         Send = view.findViewById( R.id.send );
        ltext =  view.findViewById(R.id.input_text);
        msgRecyclerView = view.findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new LiaBuJu(msgList);
        msgRecyclerView.setAdapter(adapter);
        user = ((Homepage) getContext()).dated5User();
        name = ((Homepage) getContext()).dateName();
        handler = new Handler() {
            @Override
            public void handleMessage( Message msg ) {
                if (msg.what == 1) {
                    msgRecyclerView.setAdapter(adapter);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                }else if(msg.what == 0){
                    Toast.makeText(getActivity(), "糟糕!网络好像出问题了", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Send.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick( View v ) {
                jchat = ltext.getText().toString().trim();
                if(jchat.equals("")){
                    Toast.makeText(getActivity(),"发送的信息不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                news();
                ltext.setText("");
            }
        } );
        return view;
    }


    public void news() {
        jchat = ltext.getText().toString().trim();
        this.chat = jchat;
        this.user = user;
        msgList.clear();
        new Thread() {
            @Override
            public void run() {
                http http = new http();
                http.On_httpnews( user, chat, new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e ) {
                        Message mag = new Message();
                        mag.what = 0;
                        handler.sendMessage( mag );
                    }
                    @Override
                    public void onResponse( Call call, Response response ) throws IOException {
                        final String json = response.body().string();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<weiInfo>>() {}.getType();
                        List<weiInfo> weiInfos = gson.fromJson( json, listType );
                        Collections.reverse( weiInfos );
                        for (weiInfo i : weiInfos ){
                            String tmp_name = i.getName();
                            if (!tmp_name.equals(name)){
                                msgList.add(new INterInFor(tmp_name,i.getChat(),i.getTime(),INterInFor.TYPE_RECEIVED));
                            } else {
                                msgList.add(new INterInFor(tmp_name,i.getChat(),i.getTime(),INterInFor.TYPE_SENT));
                            }
                        }
                        Message mag = new Message();
                        mag.what = 1;
                        handler.sendMessage( mag );
                    }
                } );
            }
        }.start();

    }

}