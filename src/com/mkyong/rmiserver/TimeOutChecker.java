/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiserver;

import com.mkyong.rmiinterface.Task;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author falcon
 */
public class TimeOutChecker implements Runnable {
    private LinkedList<Task> unSortTask;
    private LinkedList<Task> sortTask;
    private LinkedList<Task> sendTask;
    private long diffTime = 0;
    private boolean x=true;
    
 
    @Override
    public void run() {
        Task task;
        while(x){
            for(int i=0;i<sendTask.size();i++){
                task = sendTask.get(i);
                diffTime=(System.currentTimeMillis())-(task.getTimeStamp().getTime());
                if((diffTime>1000*60*1)&&(task.getHaveHolder())){
                    System.out.println("timeout"+sendTask.get(i).getId());
                    if(task.isSort()){
                        sortTask.add(sendTask.remove(i));
                    }else{unSortTask.add(sendTask.remove(i));}
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TimeOutChecker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setSortedTask(LinkedList<Task> sortedtask){
        this.sortTask=sortedtask;
    }
    
     public void setSendTask(LinkedList<Task> sendTask){
        this.sendTask=sendTask;
    }
    
    public LinkedList<Task> getSortedTask(){
        return sortTask;
    }
    
    public void setTaskQueue(LinkedList<Task> taskqueue){
        this.unSortTask=taskqueue;
    }
    
    public void setStatus(boolean status,int id){
        for(int i=1;i<sortTask.size();i++){
            if(sortTask.get(i).getId()==id){
                sortTask.get(i).setStatus(status);
            }
        }
    }
}
