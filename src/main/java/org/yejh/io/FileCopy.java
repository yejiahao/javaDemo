package org.yejh.io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileCopy {
    private FileCopy() {
        throw new AssertionError();
    }

    public static void copyFileByIO(String source, String destination) throws IOException {
        System.out.println("------- copyFileByIO start --------");
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024 * 4];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            out.flush();
        }
        System.out.println("------- copyFileByIO end   --------");
    }

    public static void copyFileByChannel(String source, String destination) throws IOException {
        System.out.println("------- copyFileByChannel start --------");
        try (FileChannel in = new FileInputStream(source).getChannel();
             FileChannel out = new FileOutputStream(destination).getChannel()) {
            long size = in.size();
            System.out.println("[copyFileByChannel] input size: " + size);
            out.transferFrom(in, 0, size);
        }
        System.out.println("------- copyFileByChannel end   --------");
    }

    public static void copyFileByFiles(String source, String destination) throws IOException {
        System.out.println("------- copyFileByFiles start --------");
        Path sourcePath = new File(source).toPath();
        Path destinationPath = new File(destination).toPath();
        System.out.println("[copyFileByFiles] sourcePath: " + sourcePath);
        System.out.println("[copyFileByFiles] destinationPath: " + destinationPath);
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("------- copyFileByFiles end   --------");
    }

    public static void main(String[] args) throws IOException {
        String src = "/src_file.docx";
        FileCopy.copyFileByIO(src, "/io_file.docx");
        FileCopy.copyFileByChannel(src, "/channel_file.docx");
        FileCopy.copyFileByFiles(src, "/files_file.docx");
    }
}
