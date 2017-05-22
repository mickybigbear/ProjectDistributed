/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiclient;

import com.mkyong.rmiinterface.Const;
import com.mkyong.rmiinterface.MergeSort;
import com.mkyong.rmiinterface.RMIJobService;
import com.mkyong.rmiinterface.Task;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Micky
 */
public class JobClient extends Thread{
    private Task task;
    private Worker[] worker = new Worker[2];
    private static RMIJobService look_up;
    private boolean exit = true;
    
    @Override
    public void run(){
        while(!exit){
            task = reqJob();
            if(task==null){
                waitForJob(1000);
                continue;
            }
            clientStartJob(task);
            sendResult(task);
        }
    }
    
    
    public JobClient(){
        worker[0] = new Worker();
        worker[1] = new Worker();
        getService();
    }
    
    public void clientStartJob(Task task){
        ArrayList<String> data_1 = task.getData1();
        ArrayList<String> data_2 = task.getData2();
        if(task.isSort()){
            data_1 = MergeSort.DoMerge(data_1, data_2);
            task.removeData(2);
            return;
        }
        else{
            int end = data_1.size()-1;
            int middle = end/2;
            ArrayList<String> left = new ArrayList(data_1.subList(0, middle));
            ArrayList<String> right = new ArrayList(data_1.subList(middle+1, end));
            long tStart, tEnd;
            double elapsedSeconds;
            tStart = System.currentTimeMillis();
            worker[0] = new Worker();
            worker[1] = new Worker();

            worker[0].startJob(left, 0, left.size()-1);
            worker[1].startJob(right, 0, right.size()-1);
            try {
                worker[0].join();
                worker[1].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(JobClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            data_1 = MergeSort.DoMerge(left, right);
            task.setStatus(true);
        }
    }
    
    public void waitForJob(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(JobClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Task reqJob() {
        try {
            return getService().getTask();
        } catch (RemoteException ex) {
            Logger.getLogger(JobClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void sendResult(Task task){
        try {
            getService().sendResult(task);
        } catch (RemoteException ex) {
            Logger.getLogger(JobClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private RMIJobService getService(){
        if(look_up ==null){
            try {
                look_up = (RMIJobService) Naming.lookup("//"+Const._IP_Server+"/"+Const._RMI_Name_Service1);
            } catch (NotBoundException ex) {
                Logger.getLogger(JobClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(JobClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(JobClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return look_up;
    }
    
    public void startClient(){
        this.start();
        exit = false;
    }
    
    public void exit(){
        exit = true;
    }
    
}
