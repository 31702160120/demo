package com.example.a2233.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by panchengjia on 2016/12/24.
 */
public class mine extends Fragment {
    private String user,name;
    private Button tui;
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mine,container,false);
        TextView tv_par = view.findViewById(R.id.party);
        TextView tv_num = view.findViewById(R.id.number);
        Button tui = view.findViewById(R.id.btn_tui);
        tv_par.setText(name);
        tv_num.setText(user);
        out(tui);
        return view;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        user = ((Homepage)context).dateUser();
//        name = ((Homepage)context).dateName();
//    }

    public void out(Button tui){
        tui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getActivity(),"请登录",Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}

