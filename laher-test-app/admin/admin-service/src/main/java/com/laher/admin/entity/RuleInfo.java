package com.laher.admin.entity;

import lombok.Data;

/**
 * 规则信息实体
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/16
 */
@Data
public class RuleInfo {
    /** 规则编号 **/
    private Integer id;
    /** 规则名称 **/
    private String name;
    /** 规则 **/
    private String rule;
    /** 场景 **/
    private String scene;

    public RuleInfo(Integer id, String name, String scene, String rule) {
        this.id = id;
        this.name = name;
        this.scene = scene;
        this.rule = rule;
    }

    public RuleInfo() {}
}
