package org.yejh;

import java.text.SimpleDateFormat;

public class ConstructorTest {
    public static void main(String[] args) throws Exception {
        Bean b1 = new Bean();
        System.out.println(b1);
        // --------------------------------------------------------------------------
        Bean b11 = new Bean(3, "hahahaha", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1992-01-01 06:50:00"));
        System.out.println(b11);
        // --------------------------------------------------------------------------
        Bean b2 = Bean.class.newInstance();
        System.out.println(b2);
        // --------------------------------------------------------------------------
        // Constructor<Bean> c = Bean.class.getConstructor(new
        // Class[]{Integer.class, String.class, Date.class});
        // Bean b3 = c.newInstance();
        // System.out.println(b3);
        // --------------------------------------------------------------------------
        // Bean b4 = (Bean) b11.clone();
        // System.out.println(b4);
        // --------------------------------------------------------------------------
    }
}
