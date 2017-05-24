/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Micky
 */
public interface RMIJobService extends Remote {
    
    public Task getTask(int idClient) throws RemoteException;
    public void sendResult(Task t) throws RemoteException;
}
