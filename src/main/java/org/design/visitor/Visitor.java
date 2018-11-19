package org.design.visitor;

public class Visitor {
    public void visit(ConcreteEle1 ce1) {
        ce1.print();
    }

    public void visit(ConcreteEle2 ce2) {
        ce2.print();
    }

}
