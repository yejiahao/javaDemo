package org.design.visitor;

public class ConcreteEle1 implements IEle {
    @Override
    public void print() {
        System.out.println("I am ConcreteEle1");
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
