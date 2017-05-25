package com.mkyong.rmiinterface;

import java.io.Serializable;
import java.sql.Timestamp;

import java.util.ArrayList;

public class Task implements Serializable{
    private int id;
    private boolean sort;
    private boolean  haveholder;
    private ArrayList<String> data1;
    private ArrayList<String> data2;
    private Timestamp timestamp = null;
    private int idClient=0;

    public Task(int id,ArrayList<String> data){
        this.id=id;
        haveholder=false;
        sort=false;
        this.data1=data;
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
    
    public ArrayList<String> getData1(){
        return data1;
    }
    
    public void setData1(ArrayList<String> data){
        this.data1 = data;
    }
    
    public ArrayList<String> getData2(){
        return data2;
    }
    
    public void removeData(int index){
        if(index ==1){data1 = null;}
        else{data2 = null;}
        System.gc();
    }
    
    
    public void setData2(ArrayList<String> data){
        data2 = data;
    }
    
    public void genTimeStamp() {
        timestamp = new Timestamp(System.currentTimeMillis());
    }
    
    public Timestamp getTimeStamp() {
        return timestamp;
    }
    
    public boolean isEmpty(){
        if(data1!=null && data1.size()>0){
            return false;
        }
        System.out.print("");
        return true;
    }
    
    public void joinTask(Task t2){
        this.data2 = t2.data1;
    }
    
    public void setIDClient(int id){
        this.idClient = id;
    }
    
    public int getIDClient(){
        return this.idClient;
    }
}