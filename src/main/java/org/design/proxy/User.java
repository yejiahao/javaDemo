package org.design.proxy;

public class User implements IUser {

    @Override
    public String find() {
        System.out.println("into 'find' method");
        return "str";
    }

    @Override
    public void save() {
        System.out.println("into 'save' method");
    }
}
