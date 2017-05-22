/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiserver;

import com.mkyong.rmiinterface.RMIService;
import com.mkyong.rmiinterface.Task;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Micky
 */
public class RmiServiceImp extends UnicastRemoteObject implements RMIService{
    
    private static Task task;
    private ArrayList<Task> taskList = new ArrayList();
    private static LinkedBlockingQueue<Task> taskqueue = new LinkedBlockingQueue<Task>();
    private static LinkedList<Task> sortedtask = new LinkedList<Task>();
    private static TimeOutChecker toc = new TimeOutChecker();
    private static Thread t = null;
    
    protected RmiServiceImp() throws RemoteException{
        super();
        toc.setSortedTask(sortedtask);
        toc.setTaskQueue(taskqueue);
        t=new Thread(toc);
        t.setPriority(1);
        t.start();
    }
    
    @Override
    public Task getTask() throws RemoteException {
        if(!(taskqueue.isEmpty())){
                task=taskqueue.remove();
                if(!(task.getStatus()||task.getHaveHolder())){
                    System.out.println("Send"+task.getId());
                        task.setHaveHolder(true);
                        task.genTimeStamp();
                        sortedtask.add(task);
                }
            }
            else task=null;
            toc.setSortedTask(sortedtask);
            toc.setTaskQueue(taskqueue);
            return task;
    }

    @Override
    public void sendResult(Task t) throws RemoteException {
        for(int i=0;i<sortedtask.size();i++){
            if(sortedtask.get(i).getId()==t.getId()){
                System.out.print("receiveSorted"+sortedtask.get(i).getId());
                t.setHaveHolder(false);
                t.setStatus(true);
                sortedtask.set(i, t);
            }
        }
    }
    
  
}
