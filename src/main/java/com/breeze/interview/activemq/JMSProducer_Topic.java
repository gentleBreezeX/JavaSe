package com.breeze.interview.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 要先开启消费者再启动生产者
 */
public class JMSProducer_Topic {

    private static final String DEFAULT_BROKER_BIND_URL = "tcp://192.168.233.128:61616";
    private static final String TOPIC_NAME = "JMSProducer_Topic";

    public static void main(String[] args) throws JMSException {
        //1. 通过ActiveMQConnectionFactory创建工厂，使用默认账号密码，编码将不再体现
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);
        //2. 获取连接并且开启
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3. 开启会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4. 创建主题
        Topic topic = session.createTopic(TOPIC_NAME);
        //5. 创建生产者
        MessageProducer messageProducer = session.createProducer(topic);

        for (int i = 1; i <= 6; i++) {
            TextMessage textMessage = session.createTextMessage("JMSProducer_Topic\t" + i);
            messageProducer.send(textMessage);
        }

        //6. 释放资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("********JMSProducer_Topic 发送完成 (*╹▽╹*)");

    }
}
