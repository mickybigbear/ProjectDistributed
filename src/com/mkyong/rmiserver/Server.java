package com.mkyong.rmiserver;

import java.rmi.Naming;
import com.mkyong.rmiinterface.Const;
import com.mkyong.rmiinterface.MergeSort;
import java.util.ArrayList;


public class Server{

    private static final long serialVersionUID = 1L;
    private static RMIJobServiceImp serviceJob;
    private static JobSchedule job;
    public static void main(String[] args) {
        testMergeSort();
        init();
    }
    
    public static void init(){
        try {
            serviceJob = new RMIJobServiceImp(job);
            Naming.rebind("//"+Const._IP_Server+"/"+Const._RMI_Name_Service1, serviceJob);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
    
    public static void testMergeSort(){

        System.out.println("Server generate text file...");
        ArrayList<String> task = new ArrayList();
        MergeSort.genTextFile(Const._PathFileJob, Const._Charset, 10, 1048576); // 2^10
        MergeSort.CreateJobFromFile(Const._PathFileJob, task);
        System.out.println("do job schedule");
        job = new JobSchedule(task);
    }

    

    

    
}
