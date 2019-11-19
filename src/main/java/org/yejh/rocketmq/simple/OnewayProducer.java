package org.yejh.rocketmq.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * One-way transmission is used for cases requiring moderate reliability, such as log collection.
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-11-15
 * @since 4.6.0
 */
public class OnewayProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("vmx.yejh.cn:9876");
        // Launch the instance.
        producer.start();
        for (int i = 0; i < 100; i++) {
            // Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);
        }
        // Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
