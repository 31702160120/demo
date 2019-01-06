package com.example.a2233.demo.INfo;

/**
 * Created by 2233 on 2019/1/4.
 */

public class weiInfo {
    private String name;
    private  String time;
    private  String chat;

    public  weiInfo( String name, String chat , String time){
       this.name=name;
       this.chat=chat;
       this.time=time;
   }

    public String getChat() {
        return chat;
    }

    public void setChat( String chat ) {
        this.chat = chat;
    }

    public String getTime() {
        return time;
    }

    public void setTime( String time ) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }


}
