package com.yuan.dyhdm.entity;

import java.io.Serializable;

/**
 * @author user
 *         Created on 2017/2/14.
 */
public class QuotePrice implements Serializable {
    private static final long serialVersionUID = 1L;

    public String row_optionHouse = "功能间";
    public String row_half_price = "半包(元)";
    public String row_percent = "预算百分比";

    public String Name; // 功能间名称

    public String Price; // 价格

    public String Percent; // 预算百分比
}
