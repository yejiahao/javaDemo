package org.yejh.io;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class SocketClient {
    public static void main(String[] args) {
        Socket s = null;
        InputStream in = null;
        BufferedReader br = null;
        OutputStream out = null;
        PrintWriter pw = null;
        try {
            s = new Socket("127.0.0.1", 8002);
            out = s.getOutputStream();
            pw = new PrintWriter(out);
            pw.println("[client] I am a client, hello!");
            pw.flush();
            s.shutdownOutput();

            in = s.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            String message = null;
            while ((message = br.readLine()) != null) {
                System.out.println("[client]" + new Date());
                System.out.println("[client]from server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null)
                    pw.close();
                if (out != null)
                    out.close();
                if (br != null)
                    br.close();
                if (in != null)
                    in.close();
                if (s != null)
                    s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
