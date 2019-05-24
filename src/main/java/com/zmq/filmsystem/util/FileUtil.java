package com.zmq.filmsystem.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Description 图片上传工具类
 * @Version 1.0
 */
public class FileUtil {

    public static String IMAGE_DIR = "/Users/liuxiaoping/JavaDevelop/upload/";
    // 图片允许的后缀扩展名
    public static String[] IMAGE_FILE_EXTD = new String[] { "png", "bmp", "jpg", "jpeg","pdf" };

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static boolean isFileAllowed(String fileName) {
        for (String ext : IMAGE_FILE_EXTD) {
            if (ext.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
