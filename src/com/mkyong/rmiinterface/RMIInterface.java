package com.mkyong.rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import com.mkyong.rmiinterface.Task;

public interface RMIInterface extends Remote{
        public ArrayList getJob() throws RemoteException;
        public void sendResult(Task t) throws RemoteException;
        
}