/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiserver;

import com.mkyong.rmiinterface.Task;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Micky
 */
public class JobSchedule {
    public LinkedList<Task> bigTask;
    public LinkedList<Task> sendTask;
    
    public JobSchedule(ArrayList<String> jobs){
       doJobSchedule(jobs);
    }
    
    private void doJobSchedule(ArrayList<String> jobs){
        int start, end, size = jobs.size(),range = size/128;
        for(int i=0;i<100;i++){
            start = i*range; 
            end = (start + range)-1;
            if(end>size-1){
                end = (size-1)-end;
            }
            bigTask.add(new Task(i, new ArrayList<String>(jobs.subList(start, end))));
        }
    }
    
    public Task pullFirstTask(){
        return bigTask.pollFirst();
    }
    
    public void addFirstToListSend(Task task){
        if(task!=null){
            sendTask.add(task);
        }
    }
    
    public void addLastToListJob(Task task){
        bigTask.addFirst(task);
    }
    
    public void serachSendTask(int id){
        for(int i=0;i<=send)
    }
    
    
    
    
    
    
    
    
}
