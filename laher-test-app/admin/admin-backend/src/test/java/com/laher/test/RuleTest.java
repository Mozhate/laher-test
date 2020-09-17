package com.laher.test;

import com.alibaba.fastjson.JSONObject;
import com.laher.admin.AdminBackendApplication;
import com.laher.admin.entity.RuleInfo;
import com.laher.admin.entity.UserInfo;
import com.laher.admin.model.HelloRequest;
import com.laher.admin.service.HelloService;
import com.laher.admin.service.RuleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/17
 */
@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = AdminBackendApplication.class)
public class RuleTest {

    @Resource
    private RuleService ruleService;

    @Resource
    private KieHelper kieHelper;

    // @Resource
    private KieSession kieSession;

    /** db user 信息 **/
    private static List<UserInfo> dbUserInfoList = new ArrayList<>();

    /** db rule 信息 **/
    private static List<RuleInfo> dbRuleInfoList = new ArrayList<>();

    @Before
    public void before() {
        // 初始化
        System.out.println("*******初始化*******");
        dbRules();
        System.out.println("初始化规则成功：" + dbRuleInfoList.size());
        dbUsers();
        System.out.println("初始化用户成功：" + dbUserInfoList.size());
        // 将规则加入到drools生命周期
        loadRule();
        System.out.println("*******************");
    }

    /**
     */
    @Test
    public void run() {
        UserInfo userInfo = getUser(1);
        kieSession.insert(userInfo);
        int count = kieSession.fireAllRules();
        System.out.println("规则执行：" + count);
    }

    /**
     * 规则加入到drools生命周期
     */
    private void loadRule() {
        for (RuleInfo ruleInfo : dbRuleInfoList) {
            String namePath = MessageFormat.format("scene_{0}/rule_{1}.drl", ruleInfo.getScene(), ruleInfo.getId());
            kieHelper.addContent(ruleInfo.getRule(), namePath);
        }
        kieSession = kieHelper.build().newKieSession();
        // kieHelper.getKieContainer();
    }

    /**
     * 模拟数据库规则基础数据
     */
    private void dbRules() {
        // 场景1：冰雪场馆，要求身高大于160cm，年龄大于14岁，有冰雪门票，有冰雪套装
        String ruleStr = "import com.laher.admin.entity.UserInfo\n" + //
            "\n" + //
            "rule rule_1\n" + //
            "when\n" + //
            "    $u:UserInfo(height>=160,age>=14,tickets contains \"冰雪门票\",facades contains \"冰雪套装\")\n" + //
            "then\n" + //
            "    $u.setPass(true);\n" + //
            "    System.out.println(\"闸机放行,\"+$u.getName()+\" 通过\");\n" + //
            "end\n";
        RuleInfo rule1 = new RuleInfo(1, "冰雪场馆", "1", ruleStr);

        // 场景2：游泳场馆，要求身高大于120cm，年龄大于8岁，有游泳门票，有泳裤
        // RuleInfo rule2 = new RuleInfo(2, "游泳场馆", "2", "");

        dbRuleInfoList.add(rule1);
        // dbRuleInfoList.add(rule2);
    }

    /**
     * 模拟数据库用户基础数据
     * 
     * @return 数据结果
     */
    private void dbUsers() {
        // 冰雪
        UserInfo user1 = new UserInfo(1, "张三", 173, 25, "冰雪门票", "冰雪套装");
        UserInfo user2 = new UserInfo(2, "李四", 160, 14, "冰雪门票", "无套装");

        // 游泳
        UserInfo user3 = new UserInfo(3, "王五", 133, 18, "游泳门票", "泳裤");
        UserInfo user4 = new UserInfo(4, "赵六", 110, 12, "无门票", "泳裤");

        dbUserInfoList.add(user1);
        dbUserInfoList.add(user2);
        dbUserInfoList.add(user3);
        dbUserInfoList.add(user4);
    }

    /**
     * 根据id获取用户信息
     * 
     * @param id 编号
     * @return 结果
     */
    private UserInfo getUser(int id) {
        for (UserInfo userInfo : dbUserInfoList) {
            if (userInfo.getId() == id) {
                return userInfo;
            }
        }
        return null;
    }

    /**
     * 根据id获取规则信息
     * 
     * @param id 编号
     * @return 结果
     */
    private RuleInfo getRule(int id) {
        for (RuleInfo ruleInfo : dbRuleInfoList) {
            if (ruleInfo.getId() == id) {
                return ruleInfo;
            }
        }
        return null;
    }
}
