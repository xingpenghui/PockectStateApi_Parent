package com.pockectstate.api.common.test;

import com.pockectstate.api.common.util.EncryptionUtil;
import org.junit.Test;

/**
 *@Author feri
 *@Date Created in 2019/7/9 16:08
 */
public class Aes_Test {
    @Test
    public void t1(){
        //生成秘钥
        String key=EncryptionUtil.createAESKEY();
        System.out.println(key);
        //加密
        String pass="123456";
        String mq=EncryptionUtil.AESEnc(key,pass);
        System.out.println("AES密文："+mq);
        //解密
        System.out.println("AES明文："+EncryptionUtil.AESDec(key,mq));
    }
}
