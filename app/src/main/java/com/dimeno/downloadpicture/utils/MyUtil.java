package com.dimeno.downloadpicture.utils;

import android.text.TextUtils;

import java.io.File;

public class MyUtil {

    /**
     * 用空字符串替换点目录路径的无效特殊字符
     * */
    public static String replaceIllegalFileName(String fileName){
        if(!TextUtils.isEmpty(fileName)) {
            char[] chars = fileName.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char aChar : chars) {
                String valueOf = String.valueOf(aChar);
                if(" ".equals(valueOf) || "/".equals(valueOf) || ":".equals(valueOf) || "*".equals(valueOf) || "?".equals(valueOf) || "<".equals(valueOf) || ">".equals(valueOf) || "|".equals(valueOf) || "\\".equals(valueOf)){
                    sb.append("");
                }else {
                    sb.append(aChar);
                }
            }
            return sb.toString();
        }else {
            return fileName;
        }
    }

    public static String getPhoto(String path) {
        File f = new File(path);//PccContants.PICTURE_PATH
        if (!f.exists()) {
            f.mkdirs();
        }
        return "";
    }
}
