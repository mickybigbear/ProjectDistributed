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
    
    protected RMIJobServiceImp() throws RemoteException{
        super();
        toc.setSortedTask(sortedtask);
        toc.setTaskQueue(taskqueue);
        t=new Thread(toc);
        t.setPriority(1);
        t.start();
    }
    
    @Override
    public Task getTask() throws RemoteException {
        Task task = jobSchedule.pullFirstTask();
        if(task!=null){
            task.setHaveHolder(true);
            task.genTimeStamp();
            jobSchedule.addFirstToListSend(task);
        }
        return task;
    }

    @Override
    public void sendResult(Task task) throws RemoteException {
        if(task.isEmpty()){
            return;
        }
        Task temp;
        task.setStatus(true);
        task.setHaveHolder(false);
    }
    
    private void init(){
        System.out.println("Server generate text file...");
        ArrayList<String> temp = new ArrayList();
        MergeSort.genTextFile(Const._PathFileJob, Const._Charset, 10, 1000000);
        System.out.println("do job schedule");
        jobSchedule = new JobSchedule(temp);
        temp = null;
    }
  
}
