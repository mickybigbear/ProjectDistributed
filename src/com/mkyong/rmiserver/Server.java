package com.mkyong.rmiserver;

import java.rmi.Naming;
import com.mkyong.rmiinterface.Const;


public class Server{

    private static final long serialVersionUID = 1L;
   
    public static void main(String[] args) {
        long tStart, tEnd;
        double elapsedSeconds;
        
        try {
            Naming.rebind("//"+Const._IP_Server+"/"+Const._RMI_Name_Service1, new RmiServiceImp());
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }

    

    

    
}
