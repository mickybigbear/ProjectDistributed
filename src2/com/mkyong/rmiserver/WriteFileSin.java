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
    private File filePath;
    private Thread t;

    public  WriteFileSin(File file, int start, int end, ArrayList dataList, String jobName, int part){
        this.dataList = dataList;
        this.jobName = jobName;
        this.filePath = file;
        this.startIndex=start;
        this.endIndex=end;
        this.threadName=Integer.toString(part);
        this.part = part;

    }
    @Override
    public void run() {
        try {
            System.out.println("writeing part "+this.part);
            splitFile(this.filePath,this.startIndex,this.endIndex,this.dataList,this.jobName,this.part);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<File> splitFile(File file, int start, int end, ArrayList dataList, String jobName, int part) throws IOException {
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
//                System.out.println(bytes);
            }
            files.add(newFile);
        }
        System.out.println("end write"+part);
        return files;
    }
}
