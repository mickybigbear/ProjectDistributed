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
            jobSchedule.sendTask.add(task1);
            System.out.println("send task1 : "+jobSchedule.sendTask.size());
            return task1;
        }
        else if((task1 = jobSchedule.sortTask.poll())!=null){
            if(task1.getData2()!=null){ return task1; }
            else if((task2 = jobSchedule.sortTask.poll())==null){
                return null;
            }else{
                jobSchedule.sendTask.add(task1);
                task1.joinTask(task2);
                //System.out.println("send task2 : "+jobSchedule.sendTask.size());
                System.out.println("task2 size "+task2.getData1().size());
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
        System.out.print("resive task id: "+task.getId()+" size"+task.getData1().size()+"\n");
        if(jobSchedule.deleteSendTask(task.getId())==null){
            //System.out.println("task "+task.getId()+" not in sendTask");
        }
               
        System.out.println("Unsort task : "+jobSchedule.unSortTask.size()+
                "   sort task : "+jobSchedule.sortTask.size()+
                "   send task : "+jobSchedule.sendTask.size());
        if(jobSchedule.unSortTask.size()==0 && jobSchedule.sortTask.size()==1 && jobSchedule.sendTask.size()==0){
            System.out.println("finish");
            MergeSort.createFileResult(Const._PathFileResult, Const._Charset, jobSchedule.sortTask.get(0).getData1());
        }
    }
    
   
  
}
