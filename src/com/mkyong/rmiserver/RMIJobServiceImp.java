/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiserver;

import com.mkyong.rmiinterface.Const;
import com.mkyong.rmiinterface.MergeSort;
import com.mkyong.rmiinterface.Task;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import com.mkyong.rmiinterface.RMIJobService;

/**
 *
 * @author Micky
 */
public class RMIJobServiceImp extends UnicastRemoteObject implements RMIJobService{
    
    private static Task task;
    private ArrayList<Task> taskList = new ArrayList();
    private static LinkedBlockingQueue<Task> taskqueue = new LinkedBlockingQueue<Task>();
    private static LinkedList<Task> sortedtask = new LinkedList<Task>();
    private static TimeOutChecker toc = new TimeOutChecker();
    private static Thread t = null;
    private JobSchedule jobSchedule;
    
    protected RMIJobServiceImp(JobSchedule jobSchedule) throws RemoteException{
        super();
        this.jobSchedule = jobSchedule;
//        toc.setSortedTask(sortedtask);
//        toc.setTaskQueue(taskqueue);
//        t=new Thread(toc);
//        t.setPriority(1);
//        t.start();
    }
    
    @Override
    public Task getTask() throws RemoteException {
        Task task1,task2;
        task1 = jobSchedule.unSortTask.poll();
        if((task1)!=null){
            return task1;
        }
        else if((task1 = jobSchedule.sortTask.poll())!=null){
            if(task1.getData2()!=null){ return task1; }
            else if((task2 = jobSchedule.sortTask.poll())==null){
                return null;
            }else{
                task1.joinTask(task2);
                return task1;
            }
        }
        else {return null;}
    }

    @Override
    public void sendResult(Task task) throws RemoteException {
        if(task.isEmpty()){
            return;
        }
        task.setHaveHolder(false);
        task.setStatus(true);
        jobSchedule.sortTask.add(task);
        System.out.println("Unsort task : "+jobSchedule.unSortTask.size()+
                " sort task : "+jobSchedule.sortTask.size()+
                " send task : "+jobSchedule.sendTask.size());
    }
    
    private void init(){
        
    }
  
}
