package com.breeze.interview.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 要先开启生产者再启动消费者
 */
public class JMSProducer_Queue {

    private static final String DEFAULT_BROKER_BIND_URL = "tcp://192.168.233.128:61616";
    private static final String QUEUE_NAME = "JMSProducer_Queue";

    public static void main(String[] args) throws JMSException {

        //1. 获取ActiveMQConnectionFactory工厂,使用默认账号密码，编码将不再体现
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);
        //2. 获取连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //3. 启动连接
        connection.start();
        //4. 创建会话，有两个参数，第一个是是否以事务的方式提交，第二个参数是默认的牵手方式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. 创建队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //6. 创建生产者
        MessageProducer messageProducer = session.createProducer(queue);
        for (int i = 1; i <= 6; i++) {
            //7. 创建消息
            TextMessage textMessage = session.createTextMessage("JMSProducer_Queue\t" + i);
            //8. 发送消息
            messageProducer.send(textMessage);
        }

        MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString("Map Queue", "测试");
        messageProducer.send(mapMessage);

        //9. 释放资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("=========Queue Message send is ok (*╹▽╹*)");
    }
}
