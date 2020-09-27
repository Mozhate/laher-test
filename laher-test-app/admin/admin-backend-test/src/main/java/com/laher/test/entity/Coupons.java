package com.laher.test.entity;

import lombok.Data;

/**
 * 代金券
 * 
 * @author laher
 * @date 2020/9/27/027
 */
@Data
public class Coupons {
    /** 满足金额 **/
    private Integer money;
    /** 减少金额 **/
    private Integer reduceMoney;

    public Coupons(Integer money, Integer reduceMoney) {
        this.money = money;
        this.reduceMoney = reduceMoney;
    }
}
