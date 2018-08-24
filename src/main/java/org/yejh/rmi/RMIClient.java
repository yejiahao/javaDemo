package org.yejh.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;

public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        String url = "rmi://127.0.0.1:1099/";
        RMIServerService service = (RMIServerService) Naming.lookup(url + "firstRMI");
        String msg = service.echo("同学们好");
        System.out.println("echo1: " + msg);
        String name = service.echo("20111003457", "叶嘉豪".getBytes());
        System.out.println("echo2: " + name);
        Date date = service.getTime();
        System.out.println("getTime: " + date);
    }
}
