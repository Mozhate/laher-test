package com.laher.test.kie.entity;

/**
 * 奶酪模型
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/7
 */
public class Cheese {
    private String name;
    private String shop;
    private int price;

    public Cheese(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
