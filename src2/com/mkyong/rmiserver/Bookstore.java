package com.mkyong.rmiserver;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.mkyong.rmiinterface.Book;
import com.mkyong.rmiinterface.MergeSort;
import com.mkyong.rmiinterface.RMIInterface;

public class Bookstore extends UnicastRemoteObject implements RMIInterface {

    private static final long serialVersionUID = 1L;
    private List<Book> bookList;

    protected Bookstore(List<Book> list) throws RemoteException {
        super();
        this.bookList = list;
}

    //The client sends a Book object with the isbn information on it (note: it could be a string with the isbn too)
    //With this method the server searches in the List bookList for any book that has that isbn and returns the whole object
    private static List<Book> initializeList() {
        List<Book> list = new ArrayList<>();
        list.add(new Book("Head First Java, 2nd Edition", "978-0596009205", 31.41));
        list.add(new Book("Java In A Nutshell", "978-0596007737", 10.90));
        list.add(new Book("Java: The Complete Reference", "978-0071808552", 40.18));
        list.add(new Book("Head First Servlets and JSP", "978-0596516680", 35.41));
        list.add(new Book("Java Puzzlers: Traps, Pitfalls, and Corner Cases", "978-0321336781", 39.99));
        return list;
    }

    public static void main(String[] args) {
        long tStart, tEnd;
        double elapsedSeconds;
        ArrayList<String> dataList = new ArrayList<>() ;

        try {
            Naming.rebind("//localhost/MyBookstore", new Bookstore(initializeList()));
            System.err.println("Server ready sss");
            System.out.println("generating file");
//            MergeSort.genTextFile("C:\\xampp2\\text2.txt","UTF-8",5,7500000);
            System.out.println("read to array");
            MergeSort.CreateJobFromFile3(new java.io.File("C:\\xampp2\\text2.txt"),4);

        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }

    @Override
    public ArrayList getJob() throws RemoteException {
        ArrayList<String> a = new ArrayList();
        for (int i = 0; i <= 10000000; i++) {
            a.add("mickmick");
        }
        return a;
    }

    @Override
    public String sendIP(String s) throws RemoteException {
        return null;
    }

    @Override
    public String checkTimeOut(String s) throws RemoteException {
        return null;
    }

    @Override
    public Book findBook(Book book) throws RemoteException {
        Predicate<Book> predicate = x -> x.getIsbn().equals(book.getIsbn());
        return bookList.stream().filter(predicate).findFirst().get();

    }

    @Override
    public List<Book> allBooks() throws RemoteException {
        return bookList;
    }
}
