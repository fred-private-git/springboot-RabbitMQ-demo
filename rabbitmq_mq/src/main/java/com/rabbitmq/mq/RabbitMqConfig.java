package com.rabbitmq.mq;

public class RabbitMqConfig {

    /**
     * 交换机名称 最好每个服务单独用一个
     */
    public static final String USER_EXCHANGE = "USER_EXCHANGE";

    /**
     * routingKey 业务标示
     */
    public static final String USER_ADD = "user.add";

    public static final String USER_UPDATE = "user.update";

    public static final String USER_DELETE = "user.delete";

}
