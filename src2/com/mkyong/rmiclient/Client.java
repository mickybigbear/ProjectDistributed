package com.mkyong.rmiclient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;

import com.mkyong.rmiinterface.Book;
import com.mkyong.rmiinterface.MergeSort;
import com.mkyong.rmiinterface.RMIInterface;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
	private static RMIInterface look_up;

                
	public static void main(String[] args) /*throws MalformedURLException, RemoteException, NotBoundException*/ {

                
		JobClient bigJob = new JobClient();
            try {
                ArrayList<String> job = new ArrayList();
                getJob(job);
                System.out.println(job.size());
                bigJob.clientStartJob(job);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
//                
//                
//		boolean findmore;
//		do{
//			String[] options = {"Show All", "Find a book", "Exit"};
//			int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Option dialog", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
//			
//			switch(choice){
//				case 0:
//					List<Book> list = look_up.allBooks();
//					StringBuilder message = new StringBuilder();
//					list.forEach(x -> {message.append(x.toString() + "\n");});
//					JOptionPane.showMessageDialog(null, new String(message));
//					break;
//				case 1:
//					String isbn = JOptionPane.showInputDialog("Type the isbn of the book you want to find.");
//					try{
//						Book response = look_up.findBook(new Book(isbn));
//						JOptionPane.showMessageDialog(null, "Title: " + response.getTitle() + "\n" + "Cost: $" + response.getCost(), response.getIsbn(), JOptionPane.INFORMATION_MESSAGE);
//					}catch(NoSuchElementException ex){
//						JOptionPane.showMessageDialog(null, "Not found");
//					}
//					break;
//				default:
//					System.exit(0);
//					break;
//			}
//			findmore = (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
//		}while(findmore);
	}
        
        private static void getJob(ArrayList list){
            MergeSort.genTextFile("C:/Users/Micky/Documents/NetBeansProjects/ProjectMergeSort/test/Target.txt", "UTF-8", 10, 10000000);
            MergeSort.CreateJobFromFile("C:/Users/Micky/Documents/NetBeansProjects/ProjectMergeSort/test/Target.txt", list);
        }
        

}



