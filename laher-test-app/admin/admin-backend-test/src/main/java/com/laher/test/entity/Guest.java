package com.laher.test.entity;

import lombok.Data;

/**
 * 客人
 * 
 * @author laher
 * @date 2020/9/27/027
 */
@Data
public class Guest {
    /** 姓名 **/
    private String name;
    /** 购物金额 **/
    private Integer money;
    /** 实际支付金额 **/
    private Integer totalMoney;

    public Guest(String name, Integer money) {
        this.name = name;
        this.money = money;
    }

    /**
     * 购买减少金额
     * 
     * @param reduceMoney 减少金额
     */
    public void buy(Integer reduceMoney) {
        totalMoney = money - reduceMoney;
        System.out.println("姓名：" + name + "，购物金额：" + money + "，优惠金额：" + reduceMoney + "，实际支付金额：" + totalMoney);
    }

    /**
     * 购买
     */
    public void buy() {
        totalMoney = money;
        System.out.println("姓名：" + name + "，购物金额：" + money + "，实际支付金额：" + totalMoney);
    }
}
