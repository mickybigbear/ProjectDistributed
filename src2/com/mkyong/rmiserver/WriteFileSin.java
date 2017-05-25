package com.mkyong.rmiserver;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naki_ on 5/22/2017.
 */
public class WriteFileSin implements Runnable {
    private ArrayList dataList;
    private String jobName;
    private String threadName;
    private int startIndex;
    private int endIndex;
    private int part;
    private int numberOfFile;
    private File filePath;
    private Thread t;

    public WriteFileSin(File file, int start, int end, ArrayList dataList, String jobName, int part) {
        this.dataList = dataList;
        this.jobName = jobName;
        this.filePath = file;
        this.startIndex = start;
        this.endIndex = end;
        this.threadName = Integer.toString(part);
        this.part = part;


    }
    public WriteFileSin(File file, int numberOfFile) {
        this.numberOfFile = numberOfFile;
        this.filePath = file;
    }

    @Override
    public void run() {
        try {
            System.out.println("writing part " + this.part);
//            splitFile(this.filePath, this.startIndex, this.endIndex, this.dataList, this.jobName, this.part);
            splitFile2(this.filePath,this.numberOfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<File> splitFile(File file, int start, int end, ArrayList dataList, String jobName, int part) throws IOException {
        String eof = System.lineSeparator();
        String write;
        List<File> files = new ArrayList<File>();
        File newFile = new File(file.getParent(), jobName + "."
                + String.format("%03d", part));
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile))) {
            for (int i = start; i <= end; i++) {
                write = (dataList.get(i) + eof);
                byte[] bytes = (write).getBytes(Charset.defaultCharset());
                out.write(bytes);
//                System.out.println(bytes);
            }
            files.add(newFile);
        }
        System.out.println("end write" + part);
        return files;
    }

    public static void splitFile(File f, int numberOfFile) throws IOException {
        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
        //you can change it to 0 if you want 000, 001, ...

        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {//try-with-resources to ensure closing stream
            String name = f.getName();
            double tmpSize = (f.length() / numberOfFile);
            println((Double.toString(tmpSize)));
            int sizeOfFiles = (int) tmpSize;
            println(Integer.toString((int) f.length()));
            println(Integer.toString(numberOfFile));
            System.out.println(sizeOfFiles);
            byte[] buffer = new byte[sizeOfFiles];
            int tmp = 0;
            while ((tmp = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                File newFile = new File(f.getParent(), name + "."
                        + String.format("%03d", partCounter++));
                println(name + "." + String.format("%03d", partCounter));
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    if (tmp == numberOfFile) {

                    }
                    out.write(buffer, 0, tmp);//tmp is chunk size
                }
            }
        }
    }
    public static List<File> splitFile2(File file, int numberOfFile) throws IOException {
        int counter = 1;
        int sizeRemain = (int) file.length();
        List<File> files = new ArrayList<File>();
        long sizeOfChunk = (file.length()/numberOfFile);
        println(String.valueOf(sizeRemain));
        println(String.valueOf(file.length()));
        println(Long.toString(sizeOfChunk)+"   t");
//        println(String.valueOf());
        long remain = file.length()%numberOfFile;
        String eof = System.lineSeparator();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String name = file.getName();
            String line = br.readLine();
            while (line != null) {
                File newFile = new File(file.getParent(), name + "." + String.format("%03d", counter++));
                try (OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile))) {
                    int fileSize = 0;
                    while (line != null) {
                        byte[] bytes = (line + eof).getBytes(Charset.defaultCharset());
                        if (fileSize + bytes.length > sizeOfChunk) {
                            sizeRemain = sizeRemain-fileSize;
                            println(String.valueOf("size remain =" +sizeRemain));
                            println(String.valueOf("size mod ="+remain));
                            println(String.valueOf(fileSize));
                            if(sizeRemain==remain){
                                println("last part");
                            }
                            else{
                                break;
                            }

                        }
                        out.write(bytes);
                        fileSize += bytes.length;

//                        println(String.valueOf(fileSize));
                        line = br.readLine();
                        if(sizeRemain==remain){
                            break;
                        }
                    }
                }
                files.add(newFile);
            }
        }
        return files;
    }
    public static void println(String s){
        System.out.println(s);
    }
}
