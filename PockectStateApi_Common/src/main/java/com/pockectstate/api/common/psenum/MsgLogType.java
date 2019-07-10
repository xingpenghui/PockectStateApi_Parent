package com.pockectstate.api.common.psenum;

/**
 *@Author feri
 *@Date Created in 2019/7/10 14:33
 * 标记是消息项目的操作日志的类型
 */
public enum MsgLogType {
    validatecode(1),getbackpass(2);
    private int index;
    private MsgLogType(int index){
        this.index=index;
    }

    public int getIndex() {
        return index;
    }
}
