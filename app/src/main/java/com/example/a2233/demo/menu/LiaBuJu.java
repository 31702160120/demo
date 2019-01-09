package com.example.a2233.demo.menu;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a2233.demo.INfo.INterInFor;
import com.example.a2233.demo.R;

import java.util.List;

/**
 * Created by 2233 on 2019/1/6.
 */

public class LiaBuJu extends RecyclerView.Adapter<LiaBuJu.ViewHolder> {
    private List<INterInFor> mMsgList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout leftLayout;
        RelativeLayout rightLayout;
        TextView time;
        TextView leftMsg,leftName;
        TextView rightMsg,rightName;
        public ViewHolder( View view ) {
            super( view );
            leftLayout = (RelativeLayout) view.findViewById( R.id.left_layout);
            rightLayout = (RelativeLayout) view.findViewById(R.id.right_layout);
            time  = (TextView) view.findViewById(R.id.tv_send_time);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            leftName = (TextView) view.findViewById(R.id.left_name);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
            rightName = (TextView) view.findViewById(R.id.right_name);
        }
    }
    public LiaBuJu(List<INterInFor> msgList) {
        mMsgList = msgList;
    }

    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout3, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position ) {
        INterInFor iNterInFor = mMsgList.get(position);
        holder.time.setText(iNterInFor.getTime());
        if(iNterInFor.getType() == iNterInFor.TYPE_RECEIVED){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(iNterInFor.getChat());
            holder.leftName.setText(iNterInFor.getName());
        }else if(iNterInFor.getType()== iNterInFor.TYPE_SENT){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightName.setText(iNterInFor.getName());
            holder.rightMsg.setText(iNterInFor.getChat());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
    }

