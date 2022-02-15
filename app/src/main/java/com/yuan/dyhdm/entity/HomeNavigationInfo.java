package com.yuan.dyhdm.entity;

import java.io.Serializable;

/**
 * created by liangxuedong on 2021/11/18
 */
public class HomeNavigationInfo implements Serializable {
    public String title;
    public Class cls;

    public HomeNavigationInfo(String title, Class cls) {
        this.title = title;
        this.cls = cls;
    }
}
