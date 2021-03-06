package com.laher.test.entity;

/**
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/7
 */
public class Alarm {
    private String name;

    public Alarm(String name) {
        this.name = name;
    }

    public Alarm() {}

    @Override
    public String toString() {
        return "Alarm{" + "name='" + name + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
