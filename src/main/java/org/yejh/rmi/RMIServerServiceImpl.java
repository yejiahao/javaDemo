package org.yejh.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class RMIServerServiceImpl extends UnicastRemoteObject implements RMIServerService {
    private static final long serialVersionUID = 1L;

    protected RMIServerServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String echo(String msg) {
        return "来自老师： " + msg;
    }

    @Override
    public String echo(String yourNo, byte[] yourName) {
        return "yourName";
    }

    @Override
    public Date getTime() {
        return new Date();
    }

}
