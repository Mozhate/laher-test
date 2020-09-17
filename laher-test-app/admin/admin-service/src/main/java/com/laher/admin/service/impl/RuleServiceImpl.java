package com.laher.admin.service.impl;

import com.laher.admin.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则服务
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/16
 */
@Service
public class RuleServiceImpl implements RuleService {

    /** 模拟数据库中的规则 **/
    private static List<String> rules = new ArrayList<>();

    static{

    }

}
