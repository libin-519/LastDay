package com.example.libin.yingyuebofangqi.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by libin on 2016/4/19.
 */
public class FileUtil {
    public static List<File> getList(File fileDir){
        List<File> music=new ArrayList<>();
        //根据所有的文件夹遍历文件中的MP3音乐文件
        if (fileDir.isDirectory()){
            File[] files = fileDir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    //以MP3结尾并且文件大于300k
                    return pathname.getName().toLowerCase().endsWith(".mp3")
                            && pathname.getTotalSpace() > 300 * 1024;
                }
            });
            int count=files.length;
            for (int i = 0; i  < count; i++) {
                music.add(files[i]);
            }
        }
        return music;
    }
}
