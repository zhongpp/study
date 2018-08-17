import org.omg.CORBA.Environment;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author pingpingZhong
 * Date: 2017/12/28
 * Time: 15:02
 */
public class Base64ImgUtil {
    static String imgStr = "";

    // 图片转化成base64字符串
    public static String GetImageStr() {
        String imgFile = "d://test.png";
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    // base64字符串转化成图片
    public static void GenerateImage(String imgStr) {
        // 对字节数组字符串进行Base64解码并生成图片
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成新的图片
            String imgFilePath = "d://222.png";
            out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
        } catch (Exception e) {

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        GenerateImage(imgStr);
    }
}
