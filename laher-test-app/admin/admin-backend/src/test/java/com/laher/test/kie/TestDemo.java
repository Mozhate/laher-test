package com.laher.test.kie;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.DeclarativeAgendaOption;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.type.Position;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.conf.ClockTypeOption;

/**
 * @author laher
 * @date 2020/9/7/007
 */
public class TestDemo {
    public static void main(String[] args) {
        // demo1();
        demo2();

        System.out.println("运行结束");
    }

    /**
     * 编程方式创建
     */
    private static void demo2() {
        // 及时kmodule.xml中存在一样的name也不会引起报错
        // 以编程方式创建一个kmodule.xml并将其添加到KieFileSystem
        // 通过KieFileSystem配置kieBase、KieSession
        KieServices kieServices = getKieServices();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        // 创建Base
        KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel("KBase1")
                .setDefault(true)
                .setEventProcessingMode(EventProcessingOption.CLOUD)
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
                .setDeclarativeAgenda(DeclarativeAgendaOption.ENABLED);

        // 创建session
        KieSessionModel kieSessionModel1 = kieBaseModel1.newKieSessionModel("KSession2_2")
                .setDefault(true)
                .setType(KieSessionModel.KieSessionType.STATEFUL)
                .setClockType(ClockTypeOption.get("realtime"));

        // 创建
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.writeKModuleXML(kieModuleModel.toXML());
    }

    /**
     * 基本运行模式
     */
    private static void demo1() {
        KieContainer kieContainer = getKieContainer();

        // default = true的情况可不带参
        KieBase kieBase = kieContainer.getKieBase("KBase1");
        // 有状态类型
        KieSession kieSession1 = kieContainer.newKieSession("KSession2_1");
        // 无状态类型，若类型不一致会抛出异常
        StatelessKieSession kieSession2 = kieContainer.newStatelessKieSession("KSession2_2");
    }

    private static KieContainer getKieContainer() {
        return getKieServices().getKieClasspathContainer();
    }

    private static KieServices getKieServices() {
        return KieServices.Factory.get();
    }
}
