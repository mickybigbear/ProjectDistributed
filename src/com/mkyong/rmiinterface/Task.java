package com.mkyong.rmiinterface;

import java.io.Serializable;

public class Task implements Serializable{
    private int id;
    private boolean status;
    private boolean  haveholder;
    private String data;
    

    public Task(int id,String data){
        this.id=id;
        haveholder=false;
        status=false;
        this.data=data;
    }
    public int getId(){
        return id;
    }
    public boolean getStatus(){
        return status;
    }
    public void setStatus(boolean status){
        this.status=status;
    }
    public boolean getHaveHolder(){
        return haveholder;
    }
    public void setHaveHolder(boolean haveholder){
        this.haveholder=haveholder;
    }
    public String getData(){
        return data;
    }
    public void setData(String data){
        this.data=data;
    }
}