/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.rmiserver;

import com.mkyong.rmiinterface.MergeSort;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Micky
 */
public class  MergeSortSin {
    
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static void DoMergeSort(ArrayList<String> list, int low, int high){
        List<String> target= list.subList(low, high);
        Collections.sort(list);
    }
    
    public static void DoMerge(ArrayList<String> list, int low, int middle, int high){
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

    public static void createJobFromFile2(String pathFile, ArrayList<String> list){
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            File path = new File(pathFile);
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
//                list.add(line);
//                 System.out.println(line);
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
//                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
    }
    public static List<File> splitFile2(File file, int sizeOfFileInMB,int start, int end, ArrayList dataList) throws IOException {
        int counter = 1;
        List<File> files = new ArrayList<File>();
        int sizeOfChunk = 1024 * 1024 * sizeOfFileInMB;
        String eof = System.lineSeparator();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String name = file.getName();
            String line = br.readLine();
            while (line != null) {
                File newFile = new File(file.getParent(), name + "."
                        + String.format("%03d", counter++));
                try (OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile))) {
                    int fileSize = 0;
                    while (line != null) {
                        byte[] bytes = (line + eof).getBytes(Charset.defaultCharset());
                        dataList.add(line);
                        if (fileSize + bytes.length > sizeOfChunk)
                            break;
                        out.write(bytes);
                        fileSize += bytes.length;
                        line = br.readLine();
                    }
                }
                files.add(newFile);
            }
        }
        return files;
    }
}