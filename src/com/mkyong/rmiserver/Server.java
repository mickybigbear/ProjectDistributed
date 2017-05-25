package com.mkyong.rmiserver;

import com.mkyong.rmiclient.JobClient;
import com.mkyong.rmiclient.WindowClient;
import java.rmi.Naming;
import com.mkyong.rmiinterface.Const;
import com.mkyong.rmiinterface.MergeSort;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server{

    private static final long serialVersionUID = 1L;
    private static RMIJobServiceImp serviceJob;
    private static JobSchedule job;
    private static GUIServer ui;
    public static void main(String[] args) {
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ui =  new GUIServer();
                ui.setVisible(true);
            }
        });
        testMergeSort();
        try {
            serviceJob = new RMIJobServiceImp(job, ui);
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        ui.init(job);
    }
    
    public static void regisService(){
        
        try {
            Naming.rebind("//"+Const._IP_Server+"/"+Const._RMI_Name_Service1, serviceJob);
            System.out.println("Server ready");
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void unRegisService(){
        try {
            Naming.unbind(Const._RMI_Name_Service1);
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void testMergeSort(){

        System.out.println("Server generate text file....");
        ArrayList<String> task = new ArrayList();
        //MergeSort.genTextFile(Const._PathFileJob, Const._Charset, 10, 1000000); // 2^10
        System.out.println("Server load file to memory...");
        //MergeSort.CreateJobFromFile(Const._PathFileJob, task);
        System.out.println("do job schedule");
        for(int i=0;i<5000000;i++){
            task.add(MergeSort.getRandomString(6)); // big memory use
           //task.add(UUID.randomUUID().toString()); // huge of memory use
           //task.add("abcdefgjsdfsdfsdfs"); // very less memmory use
        }
        job = new JobSchedule(task);
        System.out.println("task size "+task.size());
    }
    
    
    
    

    

    

    
}
