package com.laher.test.entity;

/**
 * @author laher
 * @date 2020/9/10/010
 */
public class Student {
    private String name;
    private Plan plan;

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
