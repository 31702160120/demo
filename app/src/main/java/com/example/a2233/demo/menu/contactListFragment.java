package com.example.a2233.demo.menu;
import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2233.demo.INfo.NewsInfo;
import com.example.a2233.demo.R;
import com.example.a2233.http;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by panchengjia on 2016/12/24.
 */
public class contactListFragment extends Fragment {
    private String name;
    private String user;
    private LinearLayout loading;
    private ListView lvNews;
    private TextView tv_name;
    private TextView tv_user;
    private Handler thread;
    private List<NewsInfo> newsInfos;
    private  NewsInfo newsInfo;
    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_contact_list_fragment,container,false);
        loading = (LinearLayout) view.findViewById( R.id.loading );
        lvNews = (ListView) view.findViewById(R.id.lv_news);
        thread = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1){
                    String json = (String)msg.obj;
                    newsInfos = JsonParse.getNewsInfo(json);
                    if(newsInfos==null){
                        Toast.makeText(getActivity(),"解析失败",Toast.LENGTH_SHORT).show();
                    }else {
                        loading.setVisibility(View.INVISIBLE);
                        lvNews.setAdapter(new json());
                    }
                }
            }
        };
        Contact();
        return view;
    }

    public void Contact(){
        new Thread(  ){
            @Override
            public void run() {
                http http = new http();
                http.On_httpyonhu( new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message mag = new Message();
                        mag.what = 0;
                        thread.sendMessage(mag);
                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final  String json = response.body().string();
                        Message mag = new Message();
                        mag.what = 1;
                        mag.obj = json;
                        thread.sendMessage( mag );
                    }
                });
            }
        }.start();
    }

    public static class JsonParse{
        public static List<NewsInfo> getNewsInfo(String json){
            Gson gson = new Gson();
            Type listType = new TypeToken<List<NewsInfo>>(){}.getType();
            List<NewsInfo> newsInfos = gson.fromJson(json,listType);
            return newsInfos;
        }
    }

    public class json extends BaseAdapter{

        @Override
        public int getCount() {
            return newsInfos.size();
        }
        public View getView(int position,View convertView,ViewGroup parent){
            View view = View.inflate(getActivity(),R.layout.layout,null);
            TextView tv_name = view.findViewById(R.id.tv_title);
            TextView tv_user = view.findViewById(R.id.tv_user);
            newsInfo =  newsInfos.get(position);
            tv_name.setText(newsInfo.getName());
            tv_user.setText(newsInfo.getUser());
            return view;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

}