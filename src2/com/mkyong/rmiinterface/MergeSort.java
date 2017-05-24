/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiinterface;

import com.mkyong.rmiserver.WriteFileSin;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mkyong.rmiserver.WriteFileSin.splitFile;


/**
 *
 * @author Micky
 */
public class  MergeSort {
    
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
   
    
    public void oldMerge(ArrayList<String> list, int low, int middle, int high){
            int end_low = middle;
            int start_high = middle+1;
            while ((low <= end_low) && (start_high <= high)) {
                if ( list.get(low).compareTo(list.get(start_high)) < 0 ){  //list[low] < list[start_high]) {
                    low++;
                } else {
                    String Temp = list.get(start_high);//list[start_high];
                    for (int k = start_high - 1; k >= low; k--) {
                        //list[k + 1] = list[k];
                        list.set(k+1, list.get(k));
                    }
                    //list[low] = Temp;
                    list.set(low, Temp);
                    low++;
                    end_low++;
                    start_high++;
                }
            }
        }
    
    public static void DoMergeSort(ArrayList<String> list, int low, int high){
        List<String> target= list.subList(low, high);
        Collections.sort(list);
    }
    
    public static void DoMerge2(ArrayList<String> list, int low, int middle, int high){
        int end_low = middle;
            int start_high = middle+1;
            while ((low <= end_low) && (start_high <= high)) {
                if ( list.get(low).compareTo(list.get(start_high)) < 0 ){  //list[low] < list[start_high]) {
                    low++;
                } else {
                    String Temp = list.get(start_high);//list[start_high];
                    for (int k = start_high - 1; k >= low; k--) {
                        //list[k + 1] = list[k];
                        list.set(k+1, list.get(k));
                    }
                    //list[low] = Temp;
                    list.set(low, Temp);
                    low++;
                    end_low++;
                    start_high++;
                }
            }
    }
    
    public static ArrayList<String> DoMerge(ArrayList<String> listL, ArrayList<String> listR) {
        int statL = 0;
        int endL = listL.size()-1;
        int startR = 0;
        int endR = listR.size()-1;
        ArrayList<String> result = new ArrayList();
        while(statL<=endL && startR<= endR){
            if(listL.get(statL).compareTo(listR.get(startR)) <= 0){
                result.add(listL.get(statL++));
            }
            else{
                result.add(listR.get(startR++));
            }
        }
        while(statL<=endL){
            result.add(listL.get(statL++));
        }
        while(startR<=endR){
            result.add(listR.get(startR++));
        }
        return result;
    }
    
    public static void genTextFile2(String pathFile, LinkedList<String> list){
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            try {
                inputStream = new FileInputStream(pathFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MergeSort.class.getName()).log(Level.SEVERE, null, ex);
            }
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // System.out.println(line);
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                try {
                    throw sc.ioException();
                } catch (IOException ex) {
                    Logger.getLogger(MergeSort.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(MergeSort.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sc != null) {
                sc.close();
            }
}
    }
    
    public static void CreateJobFromFile(String pathFile, ArrayList<String> list){
        String line;
        try(BufferedReader br = new BufferedReader(new FileReader(pathFile))){
            while( (line = br.readLine())!=null ){
                list.add(line);
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MergeSort.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MergeSort.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void CreateJobFromFile2(File pathFile, int sizeOfFileInMB, ArrayList list){
        String line;
        System.out.println("create job from file");
        String eof = System.lineSeparator();
        int sizeOfChunk = 1024 * 1024 * sizeOfFileInMB;
        int fileSize = 0;
        int startIndex=0;
        int endIndex;
        int part = 1;
        try(BufferedReader br = new BufferedReader(new FileReader(pathFile))){
                String name = pathFile.getName();
                while( (line = br.readLine())!=null ){
                    list.add(line);
                    byte[] bytes = (line + eof).getBytes(StandardCharsets.UTF_8);
                    if (fileSize + bytes.length > sizeOfChunk) {
                        fileSize = 0 ;
                        endIndex = list.size()-1;
                        System.out.println("round "+part);
//                        splitFile(pathFile,startIndex,endIndex,list,"testJob",part);

//                        Runnable a = new WriteFileSin(pathFile,startIndex,endIndex,list,"JobA",part);
//                        Thread thread1 = new Thread(a);
//                        System.out.println("Starting write file part" +  part );
//                        thread1.start();
                        int finalStartIndex = startIndex;
                        int finalPart = part;
                        int finalendIndex = endIndex;
                        new Thread(() -> {
                            try {
                                splitFile(pathFile, finalStartIndex,finalendIndex,list,"testJob", finalPart);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                        java.lang.Thread.activeCount();
                        part++;
                        startIndex = endIndex;
                    }
                    fileSize += bytes.length;
                }
                System.out.println(list.get(list.size()-1));
                br.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MergeSort.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MergeSort.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    public static void CreateJobFromFile3(File pathFile, int sizeOfFileInMB, ArrayList list){
        String line;
        System.out.println("create job from file");
//        String eof = System.lineSeparator();
        int sizeOfChunk = 1024 * 1024 * sizeOfFileInMB;
        int fileSize = 0;
        int startIndex=0;
        int endIndex;
        int part = 1;
        try(BufferedReader br = new BufferedReader(new FileReader(pathFile))){
            String name = pathFile.getName();
            while( (line = br.readLine())!=null ){
                list.add(line);
                byte[] bytes = (line).getBytes(StandardCharsets.UTF_8);
                if (fileSize + bytes.length > sizeOfChunk) {
                    fileSize = 0 ;
                    endIndex = list.size()-1;
                    System.out.println("round "+part);
//                        splitFile(pathFile,startIndex,endIndex,list,"testJob",part);

//                        Runnable a = new WriteFileSin(pathFile,startIndex,endIndex,list,"JobA",part);
//                        Thread thread1 = new Thread(a);
//                        System.out.println("Starting write file part" +  part );
//                        thread1.start();
                    int finalStartIndex = startIndex;
                    int finalPart = part;
                    int finalendIndex = endIndex;
                    new Thread(() -> {
                        try {
                            splitFile(pathFile, finalStartIndex,finalendIndex,list,"testJob", finalPart);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    java.lang.Thread.activeCount();
                    part++;
                    startIndex = endIndex;
                }
                fileSize += bytes.length;
            }
            System.out.println(list.get(list.size()-1));
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MergeSort.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MergeSort.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void createFileResult(String textPath ,String textCharset,ArrayList<String> list){
        Charset charset = Charset.forName(textCharset);
        Path path = Paths.get(textPath); 
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            for(int i=0;i<=list.size()-1;i++){
                writer.write(list.get(i));
                writer.newLine();
            }
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } 
    }
    
    public static void genTextFile(String textPath, String textCharset,int len, int num){

        Charset charset = Charset.forName(textCharset);
        Path path = Paths.get(textPath); 
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            for(long i=0;i<=num;i++){
                writer.write((getRandomString(len)));
                writer.newLine();
            }
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } 
    }
    
    private static String getRandomString(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ){
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
            
        }
        return sb.toString()+"\t";
    }


}


         

