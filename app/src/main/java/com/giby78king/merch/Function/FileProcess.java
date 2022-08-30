package com.giby78king.merch.Function;

import android.os.Environment;

public class FileProcess {
    String envPath = Environment.getExternalStorageDirectory().toString();
    String devicePath = "/Pictures/FirstMile/";

    public String GetPath(String module) {
        java.io.File folderPath = new java.io.File(envPath + devicePath + module);

        if (!folderPath.exists()) {
            folderPath.mkdirs();
        }
        return folderPath.getPath();
    }
    public Boolean CheckFileExist(String module,String fileName) {
        java.io.File jpgPath = new java.io.File(envPath + devicePath + module+"/"+fileName+".jpg");
        java.io.File pngPath = new java.io.File(envPath + devicePath + module+"/"+fileName+".png");
        return (jpgPath.exists() || pngPath.exists());
    }
    public String GetFileType(String module,String fileName) {
        java.io.File jpgPath = new java.io.File(envPath + devicePath + module+"/"+fileName+".jpg");
        java.io.File pngPath = new java.io.File(envPath + devicePath + module+"/"+fileName+".png");
        String type = "";

        if(jpgPath.exists())
        {
            type = ".jpg";
        }
        if(pngPath.exists())
        {
            type = ".png";
        }

        return type;
    }
}