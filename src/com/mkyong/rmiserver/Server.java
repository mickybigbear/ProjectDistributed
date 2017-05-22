package com.mkyong.rmiserver;

import java.rmi.Naming;
import com.mkyong.rmiinterface.Const;


public class Server{

    private static final long serialVersionUID = 1L;
    private static RMIJobServiceImp serviceJob;
    public static void main(String[] args) {
        init();
    }
    
    public static void init(){
        try {
            serviceJob = new RMIJobServiceImp();
            Naming.rebind("//"+Const._IP_Server+"/"+Const._RMI_Name_Service1, serviceJob);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }

    

    

    
}
