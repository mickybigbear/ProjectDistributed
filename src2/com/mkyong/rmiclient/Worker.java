/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiclient;

import com.mkyong.rmiinterface.MergeSort;
import java.util.ArrayList;

/**
 *
 * @author Micky
 */
public class Worker extends Thread{
    
    private ArrayList<String> job;
    private int start, end;
    public Worker(){
    }
    
    public void run(){
       MergeSort.DoMergeSort(job, start, end);
    }
    
    public void startJob(ArrayList<String> job, int start, int end){
        this.job = job;
        this.start = start;
        this.end = end;
        start();
    }
    
}
