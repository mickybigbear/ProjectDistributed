package com.mkyong.rmiinterface;

import java.io.Serializable;
import java.sql.Timestamp;

import java.util.ArrayList;

public class Task implements Serializable{
    private int id;
    private boolean sort;
    private boolean  haveholder;
    private ArrayList<String> data;
    private Timestamp timestamp = null;
    

    public Task(int id,ArrayList<String> data){
        this.id=id;
        haveholder=false;
        sort=false;
        this.data=data;
    }
    public int getId(){
        return id;
    }
    public boolean isSort(){
        return sort;
    }
    public void setStatus(boolean status){
        this.sort=status;
    }
    public boolean getHaveHolder(){
        return haveholder;
    }
    public void setHaveHolder(boolean haveholder){
        this.haveholder=haveholder;
    }
    public ArrayList<String> getData(){
        return data;
    }
    public void setData(ArrayList<String> data){
        this.data=data;
    }
    public void genTimeStamp() {
        timestamp = new Timestamp(System.currentTimeMillis());
    }
    public Timestamp getTimeStamp() {
        return timestamp;
    }
    
    public boolean isEmpty(){
        if(data!=null && data.size()>0){
            return false;
        }
        return true;
    }
}