package org.yejh.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicketThread {
    public static void main(String[] args) {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("火车票" + i);
        }

        Thread addTicket = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("add-id: " + Thread.currentThread().getId());
                    list.add("火车票" + new Random().nextInt(100));
                }
            }
        };

        Thread removeTicket = new Thread() {
            @Override
            public void run() {
                while (true) {
                    for (String ticket : list) {
                        System.out.println("remove-id: " + Thread.currentThread().getId());
                        list.remove(ticket);
                    }
                }
            }
        };

        // addTicket.start();
        // removeTicket.start();

        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println("testSyn-id: " + Thread.currentThread().getId() + " | " + list.remove(0));
                    }
                }
            }.start();
        }

    }

}
