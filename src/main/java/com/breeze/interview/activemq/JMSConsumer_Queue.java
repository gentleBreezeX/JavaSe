package com.breeze.interview.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer_Queue {

    private static final String DEFAULT_BROKER_BIND_URL = "tcp://192.168.233.128:61616";
    private static final String QUEUE_NAME = "JMSProducer_Queue";

    public static void main(String[] args) throws JMSException {

        //1. 获取ActiveMQConnectionFactory工厂，使用默认账号密码，编码将不再体现
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);
        //2. 获取连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3. 创建会话 第一个参数是否以事务的方式提交，第二个参数是默认的签收方式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4. 创建队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //5. 创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

         /*
            异步非阻塞方式(监听器onMessage())
            订阅者或接收者通过MessageConsumer的setMessageListener(MessageListener listener)注册一个消息监听器，
            当消息到达之后，系统自动调用监听器MessageListener的onMessage(Message message)方法。
        */
       messageConsumer.setMessageListener((message) -> {
            if (message instanceof TextMessage){
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("接收的消息是：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
           if (message instanceof MapMessage){
               MapMessage mapMessage = (MapMessage)message;
               try {
                   System.out.println("接收的消息是：" + mapMessage.getString("Map Queue"));
               } catch (JMSException e) {
                   e.printStackTrace();
               }
           }

       });

        System.out.println("======测试同步和异步");

        /*//1. 获取ActiveMQConnectionFactory工厂，使用默认账号密码，编码将不再体现
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);
        //2. 获取连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3. 创建会话 第一个参数是否以事务的方式提交，第二个参数是默认的签收方式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4. 创建队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //5. 创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        *//*
            同步阻塞方式receive() ，订阅者或接收者调用MessageConsumer的receive()方法来接收消息，
            receive()将一直阻塞
            receive(long timeout) 按照给定的时间阻塞，到时间自动退出
        *//*
        while (true) {
            //6. 接收消息
            TextMessage textMessage = (TextMessage)messageConsumer.receive();
            if (textMessage != null) {
                //7. 获取消息的内容并打印
                System.out.println("接收的消息是：" + textMessage.getText());
            }else {
                break;
            }
        }

        //8. 释放资源
        messageConsumer.close();
        session.close();
        connection.close();
        */


    }
}
