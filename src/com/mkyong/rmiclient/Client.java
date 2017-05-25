package com.mkyong.rmiclient;

import com.mkyong.rmiinterface.Const;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.mkyong.rmiinterface.MergeSort;
import com.mkyong.rmiinterface.RMIInterface;
import com.mkyong.rmiinterface.Task;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mkyong.rmiinterface.RMIJobService;
import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client {
	private static RMIJobService look_up;
        private static JobClient a;
        
                
	public static void main(String[] args) throws AWTException /*throws NotBoundException, MalformedURLException, RemoteException*/  {
                //look_up = (RMIJobService) Naming.lookup("//"+Const._IP_Server+"/"+Const._RMI_Name_Service1);
//                while(true){
//                    //askTask();
//                }

            
            

//             client.startClient();

        
//               while(true){
//                   try {
//                       Thread.sleep(5000);
//                   } catch (InterruptedException ex) {
//                       Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//                   }
//               }
                //Check the SystemTray is supported
        
	}
        
        private static void getJob(ArrayList list){
            //MergeSort.genTextFile("C:/Users/Micky/Documents/NetBeansProjects/ProjectMergeSort/test/Target.txt", "UTF-8", 10, 1000);
            //MergeSort.CreateJobFromFile("C:/Users/Micky/Documents/NetBeansProjects/ProjectMergeSort/test/Target.txt", list);
        }
        
        public static Task askTask() throws MalformedURLException, RemoteException, NotBoundException {
            boolean befree = true;
            Task task = null;
            while(befree){
                try {
                    task=look_up.getTask(Const._MY_ID);
                }
                catch(ConnectException e) {
                    try {
                        look_up = (RMIJobService) Naming.lookup("//"+Const._IP_Server+"/"+Const._RMI_Name_Service1);
                    }
                    catch(ConnectException ex) {
                    }
                }
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



