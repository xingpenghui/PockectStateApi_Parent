package com.pockectstate.api.common.test;

import com.pockectstate.api.common.util.JuHeSms_Util;
import com.pockectstate.api.common.util.Random_Util;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 *@Author feri
 *@Date Created in 2019/7/9 15:15
 */
public class Sms_Test {
    @Test
    public void t1() throws UnsupportedEncodingException {
        int code=Random_Util.createNum(6);
        System.out.println(JuHeSms_Util.sendMsg("18337190745",code));
        System.out.println(code);
    }
}
