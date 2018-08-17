package nio.demo;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 待改进
 * Created by zhongpp
 * on 2018/3/31.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 9092);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String request;
        while (true) {
            request = bufferedReader.readLine();
            socket.getOutputStream().write(request.getBytes());
            byte[] response = new byte[1024];
            socket.getInputStream().read(response);
            System.out.println("收到服务器响应：" + new String(response));
        }
    }
}
