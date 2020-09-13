package com.laher.test.entity;

/**
 * 驾驶执照申请的数据模型
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/7
 */
public class Applicant {
    private String name;
    private int age;
    private boolean valid = true;

    @Override
    public String toString() {
        return "Applicant{" + "name='" + name + ", age=" + age + ", valid=" + valid + '}';
    }

    public Applicant(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
