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
    
    public ArrayList<String> jobs;
    public LinkedList<Task> unSortTask;
    public LinkedList<Task> sortTask;
    public LinkedList<Task> sendTask;
    
    public JobSchedule(ArrayList<String> jobs){
       this.jobs = jobs;
       unSortTask = new LinkedList();
       sortTask = new LinkedList();
       sendTask = new LinkedList();
       doJobSchedule();
    }
    
    private void doJobSchedule(){
        int start, end, size = jobs.size(),range = size/128;
        ArrayList<String> temp;
        for(int i=0;i<=127;i++){
            start = i*range; 
            end = (start + range);
            if(end>size-1){
                end = (size-1);
            }
            temp = new ArrayList<String>(jobs.subList(start, end));
            unSortTask.add(new Task(i, temp));
        }
    }
    
    public Task deleteSendTask(int id){
        Task t;
        for(int i=0;i<=sendTask.size()-1;i++){
            if( (t = sendTask.get(i)).getId() == id){
                return sendTask.remove(i);
            }
        }
        return null;
    }
    
    
    
    
    
    
    
    
    
    
    
}
