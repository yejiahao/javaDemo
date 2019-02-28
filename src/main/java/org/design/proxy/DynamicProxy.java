package org.design.proxy;

import java.lang.reflect.Proxy;

public class DynamicProxy {
    private User target;

    public DynamicProxy(User target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    String methodName = method.getName();
                    System.out.printf("begin '%s' method%n", methodName);
                    Object invoke = method.invoke(target, args);
                    System.out.printf("result '%s' method: %s%n", methodName, invoke);
                    System.out.printf("end '%s' method%n", methodName);
                    System.out.println("-------------------------------");
                    return invoke;
                });
    }

    public static void main(String[] args) {
        DynamicProxy dynamicProxy = new DynamicProxy(new User());
        IUser proxy = (IUser) dynamicProxy.getProxyInstance();
        proxy.find();
        proxy.save();
    }
}
