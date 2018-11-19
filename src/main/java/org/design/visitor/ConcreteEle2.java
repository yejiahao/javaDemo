package org.design.visitor;

public class ConcreteEle2 implements IEle {
    @Override
    public void print() {
        System.out.println("I am ConcreteEle2");
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
