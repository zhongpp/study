package com.zpp.util;

import java.io.File;
import java.net.URL;


/**
 * @author pingpingZhong
 *         Date: 2017/6/7
 *         Time: 16:32
 */
public class FileUtil {

    public static void main(String[] args) throws Exception {
        System.out.println(findFile(FileUtil.class, "nashorn10.j8s"));
    }

    public static File findFile(Class clazz, String filename) throws Exception {
        if (clazz == null) {
            throw new Exception("class can't null");
        }
        URL url = clazz.getResource(clazz.getSimpleName()+".class");
        File file = new File(url.toURI());
        do {
            String path = file.getParent();
            file = new File(path);
            if (path == null || path.endsWith("build")) {
                break;
            }
        } while (file.isDirectory());

        return findFileByFilename(file, filename);
    }

    public static File findFileByFilename(File f, String filename) {
        if (f != null) {
            if (f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {
                    for (int i = 0; i < fileArray.length; i++) {
                        //递归调用
                        File file = findFileByFilename(fileArray[i], filename);
                        if (file != null && file.isFile() && file.getName().equals(filename)) {
                            return file;
                        }
                    }
                }
            } else {
                if (f.getName().equals(filename)) {
                    return f;
                }
            }
        }
        return null;
    }
}
