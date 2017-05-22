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
/**
 *
 * @author falcon
 */
public class TimeOutChecker implements Runnable {
    private LinkedList<Task> sortedtask = new LinkedList<Task>();
    private LinkedBlockingQueue<Task> taskqueue = new LinkedBlockingQueue<Task>();
    private long diffTime = 0;
    private boolean x=true;
    @Override
    public void run() {
        while(x){
            System.out.print("");
            for(int i=0;i<sortedtask.size();i++){
                diffTime=(new Date().getTime())-(sortedtask.get(i).getTimeStamp().getTime());
                System.out.print("");
                if((diffTime>10000)&&(sortedtask.get(i).getHaveHolder())){
                    taskqueue.add(sortedtask.remove(i));
                }
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setSortedTask(LinkedList<Task> sortedtask){
        this.sortedtask=sortedtask;
    }
    public LinkedList<Task> getSortedTask(){
        return sortedtask;
    }
    public void setTaskQueue(LinkedBlockingQueue<Task> taskqueue){
        this.taskqueue=taskqueue;
    }
    public void setStatus(boolean status,int id){
        for(int i=1;i<sortedtask.size();i++){
            if(sortedtask.get(i).getId()==id){
                sortedtask.get(i).setStatus(status);
            }
        }
    }
}
