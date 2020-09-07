package com.laher.test.entity;

/**
 * @author laher
 * @date 2020/9/8/008
 */
public class Query {
    private Object q;

    @Override
    public String toString() {
        return "Query{" +
                "q=" + q +
                '}';
    }

    public Query() {}

    public Query(Object q) {
        this.q = q;
    }

    public Object getQ() {
        return q;
    }

    public void setQ(Object q) {
        this.q = q;
    }
}
