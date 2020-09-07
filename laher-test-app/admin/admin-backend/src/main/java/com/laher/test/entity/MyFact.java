package com.laher.test.entity;

/**
 * @author laher
 * @date 2020/9/7/007
 */
public class MyFact {
    private boolean field1;
    private int field2;

    public MyFact(boolean field1, int field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public int getField2() {
        return field2;
    }

    public void setField2(int field2) {
        this.field2 = field2;
    }

    public boolean isField1() {
        return field1;
    }

    public void setField1(boolean field1) {
        this.field1 = field1;
    }

    @Override
    public String toString() {
        return "MyFact{" + "field1=" + field1 + '}';
    }
}
