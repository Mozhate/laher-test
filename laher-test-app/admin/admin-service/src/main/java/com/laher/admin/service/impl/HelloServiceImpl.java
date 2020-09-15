package com.laher.admin.service.impl;

import com.laher.admin.model.HelloRequest;
import com.laher.admin.service.HelloService;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/14
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Resource
    private KieSession kieSession;
    @Resource
    private KieHelper kieHelper;

    /**
     * 模拟数据库数据
     */
    private static String dbRule = "package rules\n" + "import com.laher.admin.model.HelloRequest\n" + "\n"
        + "rule \"dynamicSay\"\n" + "when\n" + "    $hello : HelloRequest(name=='laher')\n" + "then\n"
        + "    $hello.setResult($hello.getName()+\" hello!\");\n" + "end";

    /**
     * 输出结果
     *
     * @param helloRequest 数据
     * @return 结果
     */
    @Override
    public String say(HelloRequest helloRequest) {
        System.out.println("开始执行规则");
        kieSession.insert(helloRequest);
        int count = kieSession.fireAllRules();
        System.out.println("执行规则数：" + count);
        return helloRequest.getResult();
    }

    /**
     * 动态输出结果
     *
     * @param helloRequest 数据
     * @return 结果
     */
    @Override
    public String dynamicSay(HelloRequest helloRequest) {
        // 根据不同
        // 模拟数据库中获取规则
//        String dynamicStr = dynamicRule();
        // 获取规则后进行转换
        kieHelper.addContent(helloRequest.getRule(), ResourceType.DRL);
        // 加载
        kieHelper.build();
        /*KieBase kieBase = kieHelper.build();
        KieSession kieSession = kieBase.newKieSession();

        System.out.println("开始动态执行规则");
        kieSession.insert(helloRequest);
        int count = kieSession.fireAllRules();
        System.out.println("动态执行规则数：" + count);*/
        return helloRequest.getRule();
    }

    /**
     * 获取规则
     * 
     * @return 结果
     */
    @Override
    public String dynamicRule() {
        return dbRule;
    }

    /**
     * 设置规则
     *
     * @param rule 数据
     * @return 结果
     */
    @Override
    public String dynamicRule(String rule) {
        // 模拟db持久化
        dbRule = rule;
        // 刷新当前规则working
        KieBase kieBase = kieHelper.build();
        // 删除规则：包名,规则名
        kieBase.removeRule("rules","dynamicSay");
        System.out.println("规则刷新成功");
        return dbRule;
    }
}
