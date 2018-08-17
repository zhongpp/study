package com.zpp.util;

import java.io.File;
import java.net.URL;

/**
 * @author pingpingZhong
 *         Date: 2017/6/8
 *         Time: 9:12
 */
public class PropertyUtil {
    public static File guessPropFile(Class cls, String propFile) {
        File f = null;
        try {
            ClassLoader loader = cls.getClassLoader();

            URL url = loader.getResource(propFile);
            if (url != null) {
                f = new File(url.getPath());
                if ((f != null) && (f.exists()) && (f.isFile())) {
                    return f;
                }
            }

            Package pack = cls.getPackage();
            if (pack != null) {
                String packName = pack.getName();
                String path = "";
                if (packName.indexOf(".") < 0) {
                    path = packName + "/";
                } else {
                    int start = 0;
                    int end = 0;
                    end = packName.indexOf(".");
                    while (end != -1) {
                        path = path + packName.substring(start, end) + "/";
                        start = end + 1;
                        end = packName.indexOf(".", start);
                    }
                    path = path + packName.substring(start) + "/";
                }
                url = loader.getResource(path + propFile);
                if (url != null) {
                    f = new File(url.getPath());
                    if ((f != null) && (f.exists()) && (f.isFile())) {
                        return f;
                    }
                }
            }

            String curDir = System.getProperty("user.dir");
            f = new File(curDir, propFile);
            if ((f != null) && (f.exists()) && (f.isFile())) {
                return f;
            }

            String classpath = System.getProperty("java.class.path");
            String[] cps = classpath.split(System.getProperty("path.separator"));
            for (int i = 0; i < cps.length; i++) {
                f = new File(cps[i], propFile);
                if ((f != null) && (f.exists()) && (f.isFile())) {
                    break;
                }
                f = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            f = null;
        }
        return f;
    }

    public static void main(String[] args) {
        String classpath = System.getProperty("java.class.path");
        System.out.println(classpath);
        guessPropFile(PropertyUtil.class,"nashorn1.js");
    }

}