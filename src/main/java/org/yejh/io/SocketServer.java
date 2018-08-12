package org.yejh.io;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    public static void main(String[] args) {
        ServerSocket ss = null;
        Socket s = null;
        try {
            ss = new ServerSocket(8002);
            ExecutorService es = Executors.newFixedThreadPool(3);
            while (true) {
                s = ss.accept();
                SubClass subClass = new SubClass(s);
                es.execute(subClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (s != null)
                    s.close();
                if (ss != null)
                    ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class SubClass implements Runnable {
        private Socket socket;

        public SubClass(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            InputStream in = null;
            BufferedReader br = null;
            OutputStream out = null;
            PrintWriter pw = null;
            try {
                InetAddress iNetAddress = socket.getInetAddress();
                System.out.println(
                        "hostAddress: " + iNetAddress.getHostAddress() + "\t hostName: " + iNetAddress.getHostName());
                in = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(in));
                String message = null;
                while ((message = br.readLine()) != null) {
                    System.out.println("[server]" + new Date());
                    System.out.println("[server]from client: " + message);
                }
                socket.shutdownInput();

                out = socket.getOutputStream();
                pw = new PrintWriter(out);
                pw.println("[server] how are you? I am fine thank you!");
                pw.flush();
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
