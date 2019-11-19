package org.yejh.rocketmq.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * RocketMQ provides ordered messages using FIFO order.
 * The following example demonstrates sending/recieving of globally and partitionally ordered message.
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-11-15
 * @since 4.6.0
 */
public class OrderedProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        MQProducer producer = new DefaultMQProducer("example_group_name");
        // Launch the instance.
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 100; i++) {
            int orderId = i % 10;
            // Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTestjjj", tags[i % tags.length], "KEY" + i, ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg, (mqs, msg1, arg) -> {
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }, orderId);

            System.out.printf("%s%n", sendResult);
        }
        // server shutdown
        producer.shutdown();
    }
}
