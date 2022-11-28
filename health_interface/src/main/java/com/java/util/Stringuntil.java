package com.java.util;

import java.util.UUID;

public class Stringuntil {
    public static String getNewName(String oldName){
        //获取扩展名
        String ext = oldName.substring(oldName.lastIndexOf("."));
        //通过UUID生成新的文件名
        String name = UUID.randomUUID().toString();
        name = name.replaceAll("-", "");
        return name+ext;
    }
}
