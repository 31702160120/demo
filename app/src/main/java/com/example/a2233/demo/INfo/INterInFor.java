package com.example.a2233.demo.INfo;

/**
 * Created by 2233 on 2019/1/6.
 */

public class INterInFor {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String name;
    private String chat;
    private String time;
    private  int type;


    public  INterInFor(String name , String chat,String time, int type){
        this.name=name;
        this.chat=chat;
        this.time=time;
        this.type=type;
    }

    public int getType() {
        return type;
    }

    public void setType( int type ) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime( String time ) {
        this.time = time;
    }

    public String getChat() {
        return chat;
    }

    public void setChat( String chat ) {
        this.chat = chat;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
}
