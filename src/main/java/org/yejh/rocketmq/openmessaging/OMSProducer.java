package org.yejh.rocketmq.openmessaging;

import io.openmessaging.*;
import io.openmessaging.producer.Producer;
import io.openmessaging.producer.SendResult;

import java.nio.charset.Charset;

/**
 * The following example shows how to send message to RocketMQ broker in synchronous, asynchronous, or one-way transmissions.
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-11-18
 * @since 4.6.0
 */
public class OMSProducer {

    public static void main(String[] args) {
        final MessagingAccessPoint messagingAccessPoint = OMS.getMessagingAccessPoint("openmessaging:rocketmq://vm1.yejh.cn:9876,vmx.yejh.cn:9876/namespace");

        final Producer producer = messagingAccessPoint.createProducer();

        messagingAccessPoint.startup();
        System.out.printf("MessagingAccessPoint startup OK%n");

        {
            Message message = producer.createBytesMessage("OMS_HELLO_TOPIC", "OMS_HELLO_BODY".getBytes(Charset.forName("UTF-8")));
            SendResult sendResult = producer.send(message);
            System.out.printf("Send sync message OK, msgId: %s%n", sendResult.messageId());
        }

        {
            final Future<SendResult> result = producer.sendAsync(producer.createBytesMessage("OMS_HELLO_TOPIC", "OMS_HELLO_BODY".getBytes(Charset.forName("UTF-8"))));
            result.addListener(future -> System.out.printf("Send async message OK, msgId: %s%n", future.get().messageId()));
        }

        {
            producer.sendOneway(producer.createBytesMessage("OMS_HELLO_TOPIC", "OMS_HELLO_BODY".getBytes(Charset.forName("UTF-8"))));
            System.out.printf("Send oneway message OK%n");
        }

        producer.shutdown();
        messagingAccessPoint.shutdown();
    }
}
