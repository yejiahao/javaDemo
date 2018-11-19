package org.design.visitor;

public interface IEle {
    void print();

    void accept(Visitor v);
}
