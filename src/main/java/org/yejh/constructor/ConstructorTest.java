package org.yejh.constructor;

import java.lang.reflect.Constructor;

public class ConstructorTest {
    public static void main(String[] args) {
        Class<Bean> c = Bean.class;

        try {
            Constructor<Bean> constructor = c.getDeclaredConstructor();
            constructor.setAccessible(Boolean.TRUE);
            Bean bean1 = constructor.newInstance();
            System.out.println("bean1: " + bean1);

            System.out.println("-------------------------------------------------------------");

            constructor = c.getDeclaredConstructor(new Class[]{double.class, String.class});
            constructor.setAccessible(Boolean.TRUE);

            Bean bean2 = constructor.newInstance(new Object[]{3800, "Samsung Galaxy Note3"});
            System.out.println("bean2: " + bean2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Bean {
    private double price;
    private String brand;

    {
        System.out.println("default constructor block");
    }

    private Bean() {
        System.out.println("no signature constructor method");
    }

    private Bean(double price, String brand) {
        System.out.println("signature constructor method");
        this.price = price;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "price=" + price +
                ", brand='" + brand + '\'' +
                '}';
    }
}
