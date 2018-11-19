package org.design.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VisitorTest {
    public static void main(String[] args) {
        List<IEle> list = new ArrayList<>(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            if (random.nextInt(10) > 4) {
                list.add(new ConcreteEle1());
            } else {
                list.add(new ConcreteEle2());
            }
        }

        for (IEle ele : list) {
            ele.accept(new Visitor());
        }
    }
}
