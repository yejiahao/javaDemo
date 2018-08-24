package org.yejh.constructor;

import java.io.*;
import java.lang.reflect.Constructor;

import static java.lang.System.out;

public class ConstructorTest {
    public static void main(String[] args) throws ReflectiveOperationException, IOException, CloneNotSupportedException {
        Bean bean1 = new Bean();
        out.printf("[new 关键字]bean1: %s%n%n", bean1);

        Constructor<Bean> constructor = Bean.class.getDeclaredConstructor();
        constructor.setAccessible(Boolean.TRUE);
        Bean bean21 = constructor.newInstance();
        out.printf("[无参反射]bean21: %s%n%n", bean21);

        constructor = Bean.class.getDeclaredConstructor(new Class[]{String.class, double.class});
        constructor.setAccessible(Boolean.TRUE);
        Bean bean22 = constructor.newInstance(new Object[]{"Samsung Galaxy Note 8", 7300});
        out.printf("[带参反射]bean22: %s%n%n", bean22);

        Bean bean3 = (Bean) bean1.clone();
        out.printf("[克隆]bean3: %s%n%n", bean3);

        Bean bean4 = bean1;
        bean4.setPrice(9500);
        bean4.setBrand("Iphone X");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/constructorTest.txt"));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/constructorTest.txt"))) {
            oos.writeObject(bean4);
            out.printf("[反序列化]bean4: %s%n%n", ois.readObject());
        }
    }
}

class Bean implements Cloneable, Serializable {
    private String brand;
    private transient double price;

    {
        out.println("default constructor block");
    }

    public Bean() {
        out.println("no signature constructor method");
    }

    private Bean(String brand, double price) {
        out.println("signature constructor method");
        this.brand = brand;
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Bean{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
