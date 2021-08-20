//package com.taoweiji.kvstorage;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class FileMetadata {
//
//    private final Metadata metadata;
//    private File file;
//
//    public FileMetadata(Metadata metadata) {
//        this.metadata = metadata;
//    }
//
//    public void write(String text) {
//
//    }
//
//    public String readString() {
//        return null;
//    }
//
//    public void write(InputStream is) {
//
//    }
//
//    public boolean exists() {
//        return file.exists();
//    }
//
//    public long length() {
//        return file.length();
//    }
//
//
//    public InputStream getInputStream() throws FileNotFoundException {
//        return new FileInputStream(file);
//    }
//
//    public OutputStream getOutputStream() throws FileNotFoundException {
//        return new FileOutputStream(file);
//    }
//}