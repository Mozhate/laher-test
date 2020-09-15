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
        String dynamicStr = dynamicRuleData(helloRequest);
        // 获取规则后进行转换
        kieHelper.addContent(dynamicStr, ResourceType.DRL);
        KieBase kieBase = kieHelper.build();
        KieSession kieSession = kieBase.newKieSession();

        System.out.println("开始动态执行规则");
        kieSession.insert(helloRequest);
        int count = kieSession.fireAllRules();
        System.out.println("动态执行规则数：" + count);
        return helloRequest.getResult();
    }

    /**
     * 获取规则
     * 
     * @return 结果
     */
    private String dynamicRuleData(HelloRequest helloRequest) {
        return "package rules\n" +
                "import com.laher.admin.model.HelloRequest\n" +
                "\n" +
                "rule \"dynamic say\"\n" +
                "when\n" +
                "    $hello : HelloRequest(name=='"+helloRequest.getName()+"')\n" +
                "then\n" +
                "    $hello.setResult($hello.getName()+\" dynamic say hello!\");\n" +
                "end";
    }
}
