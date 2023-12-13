package com.tml.core.rabbitmq;

import com.tml.common.exception.BaseException;
import lombok.Data;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/8 18:40
 */
@Configuration
@Data
@Component
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.pre-command}")
    private String preCommand;

    @Value("${spring.rabbitmq.exchange-type}")
    private String exchangeType;

    @Bean
    public Queue textQueue(){
        return new Queue(preCommand+".text");
    }

    @Bean
    public Queue imgQueue(){
        return new Queue(preCommand+".image");
    }

    @Bean
    public Exchange defaultExchange(){
        String abstractExchange = this.preCommand+"."+exchangeType;
        switch (exchangeType){
            case "topic":
                return new TopicExchange(abstractExchange);
            case "default":
                return new DirectExchange(abstractExchange);
            default:
                throw new BaseException("rabbitmq交换机配置错误,请检查配置文件后重新运行");
        }
    }

}
