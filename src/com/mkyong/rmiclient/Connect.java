/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiclient;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Micky
 */
public class Connect extends Thread{
    
    
    public Connect(){
        
    }
    

    @Override
    public void run(){
       tryGetJob();
       notify();
    }
    
    public void tryGetJob(){
        boolean tryGet = true;
        int time = 0;
        try{
            while(tryGet){
                // call rmi get job
                Thread.sleep(500+time);
                if(time<=3000){time+= 500;}
                if(/* get job */true){tryGet = false;}   
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
                
        }
    }
}
