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

/**
 *
 * @author Micky
 */
public class RmiServiceImp extends UnicastRemoteObject implements RMIService{
    
    private ArrayList<Task> taskList = new ArrayList();
    
    protected RmiServiceImp() throws RemoteException{
        super();
    }
    
    @Override
    public Task getTask() throws RemoteException {
        System.out.println("ass");
        Task task = null;
        for(int i=0;i<taskList.size();i++){
            if(!(taskList.get(i).getStatus()||taskList.get(i).getHaveHolder())){
                System.out.println("asdfghjk"+i);
                taskList.get(i).setHaveHolder(true);
                task=taskList.get(i);
                i=taskList.size();
            }
            else{
                task=null;
            }
        }
        return task;
    }

    @Override
    public void sendResult(Task t) throws RemoteException {
        t.setHaveHolder(false);
        t.setStatus(true);
        taskList.set(t.getId(), t);
    }
  
}
