package com.mkyong.rmiclient;

import com.mkyong.rmiinterface.Const;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.mkyong.rmiinterface.MergeSort;
import com.mkyong.rmiinterface.RMIService;
import com.mkyong.rmiinterface.Task;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
	private static RMIService look_up;

                
	public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException  {
                look_up = (RMIService) Naming.lookup("//"+Const._IP_Server+"/"+Const._RMI_Name_Service1);
                while(true){
                    askTask();
                }

//               MergeSort.genTextFile(Const._PathFileJob, Const._Charset, 10, 10000000);
//               MergeSort.CreateJobFromFile(Const._PathFileJob, new ArrayList());
//               while(true){
//                   try {
//                       Thread.sleep(5000);
//                   } catch (InterruptedException ex) {
//                       Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//                   }
//               }
//		JobClient bigJob = new JobClient();
//     
//                ArrayList<String> job = new ArrayList();
//                getJob(job);
//                System.out.println(job.size());
//                bigJob.clientStartJob(job);
           
	}
        
        private static void getJob(ArrayList list){
            MergeSort.genTextFile("C:/Users/Micky/Documents/NetBeansProjects/ProjectMergeSort/test/Target.txt", "UTF-8", 10, 10000000);
            MergeSort.CreateJobFromFile("C:/Users/Micky/Documents/NetBeansProjects/ProjectMergeSort/test/Target.txt", list);
        }
        
        public static Task askTask() throws MalformedURLException, RemoteException, NotBoundException {
            boolean befree = true;
            Task task = null;
            while(befree){
                task=look_up.getTask();
                if(task!=null){
                    befree=false;
                }else{
                    System.out.println("Don't have task.");
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("busy"+task.getId());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            look_up.sendResult(task);
            task=null;
            befree=true;
            System.out.println("befree");
            return task;
        }
        

}



