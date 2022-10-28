package com.yuan.dyhdm.entity;

import com.yuan.dyhdm.interfaceP.ISubjectKT;
import com.yuan.dyhdm.utils.UtilsLog;

/**
 * created by liangxuedong on 2022/10/28
 */
public class UserProxy1 implements ISubjectKT {
    @Override
    public void buybuybuy(String mac) {
        UtilsLog.print("UserProxy1+buybuybuy"+mac);
    }

    @Override
    public void sale(String ipone) {
        UtilsLog.print("UserProxy1+sale"+ipone);
    }
}
