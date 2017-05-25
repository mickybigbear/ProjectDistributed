package com.mkyong.rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RMIInterface extends Remote{
	public Book findBook(Book b) throws RemoteException;
	public List<Book> allBooks() throws RemoteException;
        public ArrayList getJob() throws RemoteException;

    String sendIP(String s) throws RemoteException;

    String checkTimeOut(String s) throws RemoteException;
}