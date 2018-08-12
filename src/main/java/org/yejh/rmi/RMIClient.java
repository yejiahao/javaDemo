package org.yejh.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RMIClient {
    public static void main(String[] args) {
        try {
            String url = "rmi://127.0.0.1:1099/";
            RMIServerService service = (RMIServerService) Naming.lookup(url + "firstRMI");
            String msg = service.echo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            System.out.println("1: " + msg);
            String name = service.echo("20111003457", "叶嘉豪".getBytes());
            System.out.println("2: " + name);
            Date date = service.getTime();
            System.out.println("3: " + date);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
