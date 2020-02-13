package com.tensquare.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Author: Durian
 * Date: 2020/2/12 20:44
 * Description:
 */
public class JasyptUtil
{

    public static void encryptor()
    {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐),自定义
        textEncryptor.setPassword("tensquare_salt");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("Yw43112819981016+0035");
        System.out.println("username:" + username);
        System.out.println("password:" + password);
    }

    public static void decryptor()
    {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐),自定义
        textEncryptor.setPassword("tensquare_salt");
        //解密
        String decrypt = textEncryptor.decrypt("9N0j84xejjkUxmfM+MiaYB0sEfMZWDqY");
        System.out.println("username decrypt:" + decrypt);
    }

    public static void main(String[] args)
    {
        encryptor();
    }
}
