/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiclient;

import com.mkyong.rmiinterface.MergeSort;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Micky
 */
public class JobClient {
    private ArrayList<String> job;
    private Worker[] worker = new Worker[2];
    
    
    public JobClient(){
        worker[0] = new Worker();
        worker[1] = new Worker();
    }
    
    public void clientStartJob(ArrayList<String> job){
        int end = job.size()-1;
        int middle = end/2;
        ArrayList<String> left = new ArrayList(job.subList(0, middle));
        ArrayList<String> right = new ArrayList(job.subList(middle+1, end));
         long tStart, tEnd;
        double elapsedSeconds;
        tStart = System.currentTimeMillis();
        
        worker[0].startJob(left, 0, left.size()-1);
        worker[1].startJob(right, 0, right.size()-1);
        try {
            worker[0].join();
            worker[1].join();
        } catch (InterruptedException ex) {
            Logger.getLogger(JobClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(job.size());
        job = MergeSort.DoMerge(left, right);
        System.out.println(job.size());
        tEnd = (System.currentTimeMillis() - tStart);
        elapsedSeconds = tEnd / 1000.0; 
        System.out.println("Merge time use"+elapsedSeconds+" sec");
        MergeSort.createFileResult("C:/Users/Micky/Documents/NetBeansProjects/ProjectMergeSort/test/Result.txt", "UTF-8", job);
        
    }
    
}
