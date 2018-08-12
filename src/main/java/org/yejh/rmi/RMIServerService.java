package org.yejh.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface RMIServerService extends Remote {
    String echo(String msg) throws RemoteException;

    String echo(String yourNo, byte[] yourName) throws RemoteException;

    Date getTime() throws RemoteException;
}
