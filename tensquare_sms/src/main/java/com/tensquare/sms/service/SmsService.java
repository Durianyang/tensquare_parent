package com.tensquare.sms.service;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author: Durian
 * Date: 2020/2/12 17:03
 * Description: 短信发送
 */
@Component
@RabbitListener(queues = "sms")
public class SmsService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsService.class);

    private final SmsUtil smsUtil;
    @Autowired
    public SmsService(SmsUtil smsUtil)
    {
        this.smsUtil = smsUtil;
    }

    @RabbitHandler
    public void sendSms(Map<String, String> message)
    {
        LOGGER.info("手机号:" + message.get("mobile"));
        LOGGER.info("验证码:" + message.get("code"));
        try
        {
            smsUtil.sendSms(message.get("mobile"), message.get("code"));
        } catch (ClientException e)
        {
            LOGGER.error("短信验证码发送失败:" + message.get("mobile") + "_" + message.get("code"));
        }
    }
}
