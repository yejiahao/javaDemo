package org.yejh.io;

import java.io.*;

public class FileRead {
    public static void main(String[] args) throws IOException {
        String fileName = "/task.txt";
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GB18030"))) {
            // bf = new BufferedReader(new FileReader(fileName));
            String lineMsg;
            while ((lineMsg = bf.readLine()) != null) {
                System.out.println(lineMsg);
            }
        }
    }
}
