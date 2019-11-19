package org.yejh.rocketmq.openmessaging;

import io.openmessaging.Message;
import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.OMS;
import io.openmessaging.consumer.PullConsumer;
import io.openmessaging.rocketmq.domain.NonStandardKeys;

import java.util.Objects;

/**
 * Use OMS PullConsumer to poll messages from a specified queue.
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-11-18
 * @since 4.6.0
 */
public class OMSPullConsumer {

    public static void main(String[] args) {
        final MessagingAccessPoint messagingAccessPoint = OMS.getMessagingAccessPoint("openmessaging:rocketmq://vm1.yejh.cn:9876,vmx.yejh.cn:9876/namespace");

        final PullConsumer consumer = messagingAccessPoint.createPullConsumer(OMS.newKeyValue().put(NonStandardKeys.CONSUMER_GROUP, "OMS_CONSUMER"));

        messagingAccessPoint.startup();
        System.out.printf("MessagingAccessPoint startup OK%n");

        consumer.startup();
        System.out.printf("Consumer startup OK%n");

        Message message = consumer.receive();

        if (Objects.nonNull(message)) {
            String msgId = message.sysHeaders().getString(Message.BuiltinKeys.MESSAGE_ID);
            System.out.printf("Received one message: %s%n", msgId);
            consumer.ack(msgId);
        }

        consumer.shutdown();
        messagingAccessPoint.shutdown();
    }
}
