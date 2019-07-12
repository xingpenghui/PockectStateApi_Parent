package com.pockectstate.api.common.psenum;

/**
 *@Author feri
 *@Date Created in 2019/7/11 16:38
 */
public enum DeviceType {
    android(1),iosphone(2),pchtml(3),wechat(4);
    private int value;
    private DeviceType(int value){
        this.value=value;
    }
    public int getValue(){
        return value;
    }
}
