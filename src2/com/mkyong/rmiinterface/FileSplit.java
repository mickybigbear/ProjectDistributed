package com.mkyong.rmiinterface;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naki_ on 5/20/2017.
 */
class FileSplit {
    public static void splitFile(File f,int numberOfFile) throws IOException {
        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
        //you can change it to 0 if you want 000, 001, ...

        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {//try-with-resources to ensure closing stream
            String name = f.getName();
            double tmpSize = (f.length()/numberOfFile);
            println((Double.toString(tmpSize)));
            int sizeOfFiles = (int)tmpSize;
            println(Integer.toString((int)f.length()));
            println(Integer.toString(numberOfFile));
            System.out.println(sizeOfFiles);
            byte[] buffer = new byte[sizeOfFiles];
            int tmp = 0;
            while ((tmp = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                File newFile = new File(f.getParent(), name + "."
                        + String.format("%03d", partCounter++));
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    if(tmp == numberOfFile){

                    }
                    out.write(buffer, 0, tmp);//tmp is chunk size
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        splitFile(new File("C:\\xampp2\\sampletext.txt"),11);
    }
    public static void println(String s){
         System.out.println(s);
    }
    public static List<File> splitFile2(File file, int sizeOfFileInMB,ArrayList dataList) throws IOException {
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

    public static List<File> splitFile3(File file,int start, int end, ArrayList dataList,String jobName,int part) throws IOException {
        String eof = System.lineSeparator();
        String write;
        List<File> files = new ArrayList<File>();
        File newFile = new File(file.getParent(), jobName+ "."
                + String.format("%03d", part));
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile))) {
            for(int i =start;i<=end;i++){
                write = (dataList.get(i)+eof);
                byte[] bytes = (write + eof).getBytes(Charset.defaultCharset());
                out.write(bytes);
            }
            files.add(newFile);
        }
        return files;
    }

    public static List<File> splitFile4(File file, int sizeOfFileInMB) throws IOException {
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

