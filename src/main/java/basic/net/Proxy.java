package basic.net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class Proxy {
    public static void main(String[] args) {
        try {
            checkProxy("14.155.115.146",9000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void checkProxy(String ip,int port) throws MalformedURLException {
        URL url = null;
        try {
            url = new URL("http://www.baidu.com");
        } catch (MalformedURLException e) {
            System.out.println("url invalidate");
        }
        InetSocketAddress addr = null;
        addr = new InetSocketAddress(ip, port);
        java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, addr); // http proxy
        InputStream in = null;
        try {
            URLConnection conn = url.openConnection(proxy);
            conn.setConnectTimeout(1000);
            in = conn.getInputStream();
        } catch (Exception e) {
            System.out.println("ip " + ip + " is not aviable");//异常IP
        }
        String s = convertStreamToString(in);
        System.out.println(s);
        // System.out.println(s);
        if (s.indexOf("others/baidu") > 0) {//有效IP
            System.out.println(ip + ":" + port + " is ok");
        }
    }

    public static String convertStreamToString(InputStream is) {
        if (is == null)
            return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }
}
