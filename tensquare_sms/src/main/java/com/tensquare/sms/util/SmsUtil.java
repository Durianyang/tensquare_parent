package com.tensquare.sms.util;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.tensquare.sms.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * pom.xml
 * <dependency>
 * <groupId>com.aliyun</groupId>
 * <artifactId>aliyun-java-sdk-core</artifactId>
 * <version>4.0.3</version>
 * </dependency>
 */
@Component
@EnableConfigurationProperties(AccessKey.class)
public class SmsUtil
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsService.class);

    private final AccessKey accessKey;

    @Autowired
    public SmsUtil(AccessKey accessKey)
    {
        this.accessKey = accessKey;
    }

    public void sendSms(String mobile, String code) throws ClientException
    {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey.getKeyId(),
                accessKey.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", accessKey.getRegionId());
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", accessKey.getSignName());
        request.putQueryParameter("TemplateCode", accessKey.getTemplateCode());
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        CommonResponse response = client.getCommonResponse(request);
        LOGGER.info(response.getData());
    }


}