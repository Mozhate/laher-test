package com.laher.admin.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息实体
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/17
 */
@Data
public class UserInfo {
    /** 编号 **/
    private int id;
    /** 姓名 **/
    private String name;
    /** 身高cm **/
    private int height;
    /** 年龄 **/
    private int age;
    /** 门票 **/
    private List<String> tickets = new ArrayList<>();
    /** 服装 **/
    private List<String> facades = new ArrayList<>();
    /** 是否通过 **/
    private boolean pass;

    public UserInfo(int id, String name, int height, int age, String ticket, String facade) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.age = age;
        this.tickets.add(ticket);
        this.facades.add(facade);
    }

    public UserInfo() {}

    @Override
    public String toString() {
        return "UserInfo{" + "id=" + id + ", name='" + name + ", height=" + height + ", age=" + age + ", tickets="
            + tickets + ", facades=" + facades + ", pass=" + pass + '}';
    }
}
