package com.zj.mq_test.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import com.rabbitmq.mq.model.AddUserMQ;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.rabbitmq.mq.RabbitMqConfig.USER_ADD;
import static com.rabbitmq.mq.RabbitMqConfig.USER_EXCHANGE;

@Service
public class MqService {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    public void sendAddUser(){
        System.out.println("开始广播 添加用户事件");
        AddUserMQ addUserMQ = new AddUserMQ();
        addUserMQ.setUserId("111");
        addUserMQ.setPhone("1234567890");
        addUserMQ.setUserName("张三");
        rabbitMessagingTemplate.convertAndSend(USER_EXCHANGE,USER_ADD,addUserMQ);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(USER_EXCHANGE),
            key = USER_ADD,
            value= @Queue(USER_ADD+".project1") //不同项目的接收服务最好单独使用一个队列，建议是routingKey.projectName
    ))
    public void recieveAddUserSuccess(AddUserMQ addUserMQ){
        System.out.println("recieveAddUserSuccess 开始接收添加用户事件");
        System.out.println(addUserMQ.toString());
    }


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(USER_EXCHANGE),
            key = USER_ADD,
            value= @Queue(USER_ADD+".project2")
    ))
    public void recieveAddUserError(AddUserMQ addUserMQ,Channel channel,Message message) throws IOException, InterruptedException {
        System.out.println("recieveAddUserError 开始接收添加用户事件");
        try{
            new Integer("aa");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            System.out.println("数据处理失败，发送nack请求，继续接收处理失败的数据");
            Thread.sleep(5000);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
