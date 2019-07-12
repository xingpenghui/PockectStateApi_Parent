package com.pockectstate.api.common.test;

import com.pockectstate.api.common.util.IdGenerator;
import com.pockectstate.api.common.util.Jwt_Util;
import org.junit.Test;

/**
 *@Author feri
 *@Date Created in 2019/7/11 15:21
 */
public class JWT_Test {
    @Test
    public void t1(){
        String m="18515990152";
        IdGenerator idGenerator=new IdGenerator();
        String pass=Jwt_Util.createJWT(idGenerator.nextId()+"",30,m);
        System.out.println("JWT："+pass);
        System.out.println("JWT解析："+Jwt_Util.parseJWT(pass));
    }
}
