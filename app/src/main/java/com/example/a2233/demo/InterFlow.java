package com.example.a2233.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
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
    private weiInfo weiInfo;
    private List<weiInfo> weiInfos;
    private LinearLayout loading;
    private ListView lvNews;
    private String jchat;

    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.activity_inter_flow, container, false );
        Button Send = view.findViewById( R.id.send );
        ltext = (EditText) view.findViewById( R.id.text );
        lvNews = (ListView) view.findViewById( R.id.list );
        handler = new Handler() {
            @Override
            public void handleMessage( Message msg ) {
                if (msg.what == 1) {
                    String json = (String) msg.obj;
                    weiInfos = Jsonwei.getweiInfo( json );
                    if (weiInfos == null) {
                        Toast.makeText( getActivity(), "解析失败", Toast.LENGTH_SHORT ).show();
                    } else {
                        json d = new json();
                        lvNews.setAdapter( d );
                        lvNews.post( new Runnable() {
                            @Override
                            public void run() {
                                json json = new json();
                                lvNews.setSelection( json.getCount() - 1 );
                                ltext.setText( "" );
//                                lvNews.smoothScrollToPosition(json.getCount());
                            }
                        } );
                    }
                }
            }
        };
        Send.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick( View v ) {
                news();

            }
        } );

        return view;
    }

    public void onAttach( Context context ) {
        super.onAttach( context );
        user = ((Homepage) context).dated5User();
    }

    public void news() {
        jchat = ltext.getText().toString().trim();
        this.chat = jchat;
        this.user = user;
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
                        Message mag = new Message();
                        mag.what = 1;
                        mag.obj = json;
                        handler.sendMessage( mag );
                        Log.v( "数据", json );
                    }
                } );
            }
        }.start();

    }

    public static class Jsonwei {
        public static List<weiInfo> getweiInfo( String json ) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<weiInfo>>() {
            }.getType();
            List<weiInfo> weiInfos = gson.fromJson( json, listType );
            Collections.reverse( weiInfos );
            return weiInfos;
        }
    }

    public class json extends BaseAdapter {

        @Override
        public int getCount() {
            return weiInfos.size();
        }

        public View getView( int position, View convertView, ViewGroup parent ) {
            View view = View.inflate( getActivity(), R.layout.layout2, null );
            TextView x_name = view.findViewById( R.id.x_title );
            TextView x_chat = view.findViewById( R.id.x_chat );
            TextView x_time = view.findViewById( R.id.x_type );
            weiInfo = weiInfos.get( position );
            x_name.setText( weiInfo.getName() );
            x_chat.setText( weiInfo.getChat() );
            x_time.setText( weiInfo.gettime() );
            return view;
        }

        @Override
        public Object getItem( int position ) {
            return null;
        }

        @Override
        public long getItemId( int position ) {
            return 0;
        }

    }
}