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
    
    private ArrayList<Task> taskList = new ArrayList();
    private static LinkedBlockingQueue<Task> taskqueue = new LinkedBlockingQueue<Task>();
    private static TimeOutChecker toc = new TimeOutChecker();
    private static Thread t = null;
    private JobSchedule jobSchedule;
    
    protected RMIJobServiceImp(JobSchedule jobSchedule) throws RemoteException{
        super();
        this.jobSchedule = jobSchedule;
        toc.setSortedTask(jobSchedule.sortTask);
        toc.setTaskQueue(jobSchedule.unSortTask);
        toc.setSendTask(jobSchedule.sendTask);
        t=new Thread(toc);
        t.setPriority(1);
        t.start();
    }
    
    @Override
    public Task getTask(int idClient) throws RemoteException{
        Task task1, task2;
        task1 = jobSchedule.unSortTask.peek();
        if(task1!=null){
            jobSchedule.unSortTask.poll();
            return prepareSendTask(task1, idClient); // send Unsort Task 
        }
        task1 = jobSchedule.sortTask.peek();
        if(task1 == null){
            return null; //<--- client must wait for job
        }
        if(task1.getData2()!=null ){
            jobSchedule.sortTask.poll();
            return prepareSendTask(task1, idClient); //rare care implement not finsih yet
        }
        if( jobSchedule.sortTask.size()>=2){
            task2 = jobSchedule.sortTask.get(1);
            if(task2 != null){
                task1.joinTask(task2);
                jobSchedule.sortTask.poll();
                jobSchedule.sortTask.poll();
                return prepareSendTask(task1, idClient);
            }
        }
        isFinish();
        return null;
    }
    
    @Override
    public void sendResult(Task task) throws RemoteException{
        // not implement getTask empty
        System.out.println("\t receive task id "+task.getId()+" size "+task.getData1().size()+" from client id "+task.getIDClient());
        task.setHaveHolder(false);
        task.setStatus(true);
        if(jobSchedule.deleteSendTask(task.getId())!=null){
            jobSchedule.sortTask.add(task);
        }else{
            System.out.println("task "+task.getId()+" not in sendTask");
        }
    }
    
    private Task prepareSendTask(Task task, int idClient){
        task.setHaveHolder(true);
        task.genTimeStamp();
        jobSchedule.sendTask.add(task);
        task.setIDClient(idClient);
        System.out.println("Send task id "+task.getId()+" size "+task.getData1().size()+" to client id "+task.getIDClient());
        return task;
    }
    
    private boolean isFinish(){
        if(jobSchedule.unSortTask.size()==0 && jobSchedule.sortTask.size()==1 && jobSchedule.sendTask.size()==0){
            System.out.println("finish un"+jobSchedule.unSortTask.size()+" sort "+jobSchedule.sortTask.size()+
                    " send "+jobSchedule.sendTask.size());
            MergeSort.createFileResult(Const._PathFileResult, Const._Charset, jobSchedule.sortTask.get(0).getData1());
            return true;
        }
        return false;
    }
    
   
  
}
