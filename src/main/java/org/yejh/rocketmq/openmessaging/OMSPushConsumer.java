package org.yejh.rocketmq.openmessaging;

import io.openmessaging.Message;
import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.OMS;
import io.openmessaging.consumer.MessageListener;
import io.openmessaging.consumer.PushConsumer;
import io.openmessaging.rocketmq.domain.NonStandardKeys;

/**
 * Attaches OMS PushConsumer to a specified queue and consumes messages by {@link MessageListener}.
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-11-18
 * @since 4.6.0
 */
public class OMSPushConsumer {

    public static void main(String[] args) {
        final MessagingAccessPoint messagingAccessPoint = OMS.getMessagingAccessPoint("openmessaging:rocketmq://vm1.yejh.cn:9876,vmx.yejh.cn:9876/namespace");

        final PushConsumer consumer = messagingAccessPoint.createPushConsumer(OMS.newKeyValue().put(NonStandardKeys.CONSUMER_GROUP, "OMS_CONSUMER"));

        messagingAccessPoint.startup();
        System.out.printf("MessagingAccessPoint startup OK%n");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consumer.shutdown();
            messagingAccessPoint.shutdown();
        }));

        consumer.attachQueue("OMS_HELLO_TOPIC", (message, context) -> {
            System.out.printf("Received one message: %s%n", message.sysHeaders().getString(Message.BuiltinKeys.MESSAGE_ID));
            context.ack();
        });
    }
}
