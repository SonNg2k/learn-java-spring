package com.sonng2k.messagingrabbitmq;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

// Create a RabbitMQ Message Receiver
// With any messaging-based application, you need to create a receiver that responds to published messages

// The Receiver is a POJO that defines a method for receiving messages. When you register it to receive
// messages, you can name it anything you want.

// For convenience, this POJO also has a CountDownLatch. This lets it signal that the message has been
// received. This is something you are not likely to implement in a production application.

@Component
public class Receiver {

    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
