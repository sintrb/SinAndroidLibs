package com.sin.android.sinlibs.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Robin on 2017/12/28.
 */

public class FileUtils {
    final static String TAG = "FileUtils";
    final static int DEFUALT_BUFFER = 1024;
    final static int EOF = -1;

    /**
     * Unzip a ZIP file, keeping the directory structure.
     *
     * @param zipFile        A valid ZIP file.
     * @param destinationDir The destination directory. It will be created if it doesn't exist.
     * @return {@code true} if the ZIP file was successfully decompressed.
     */
    public static boolean unzip(File zipFile, File destinationDir) {
        ZipFile zip = null;
        try {
            destinationDir.mkdirs();
            zip = new ZipFile(zipFile);
            Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
            while (zipFileEntries.hasMoreElements()) {
                ZipEntry entry = zipFileEntries.nextElement();
                String entryName = entry.getName();
                File destFile = new File(destinationDir, entryName);
                File destinationParent = destFile.getParentFile();
                if (destinationParent != null && !destinationParent.exists()) {
                    destinationParent.mkdirs();
                }
                if (!entry.isDirectory()) {
                    BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
                    int currentByte;
                    byte data[] = new byte[DEFUALT_BUFFER];
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos, DEFUALT_BUFFER);
                    while ((currentByte = is.read(data, 0, DEFUALT_BUFFER)) != EOF) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (zip != null) {
                try {
                    zip.close();
                } catch (IOException ignored) {
                    ignored.printStackTrace();
                }
            }
        }
        return true;
    }


    //下载到本地后执行安装
    public static void installAPK(String filePath, Context context) {
        File file = new File(filePath);
        if (!file.exists()) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + file.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");

        //在服务中开启activity必须设置flag,后面解释
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
