package com.laher.test.kie;

import com.laher.test.entity.*;
import com.laher.test.kie.entity.Cheese;
import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.DeclarativeAgendaOption;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.type.Position;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.internal.utils.KieService;
import org.kie.api.runtime.*;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.internal.command.CommandFactory;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laher
 * @date 2020/9/7/007
 */
public class TestDemo {
    public static void main(String[] args) {
        // demo1();
        // demo2();
        // demo3();
        // demo4();
        // demo5();
        // new TestDemo().demo6();
        // new TestDemo().demo7();
        // new TestDemo().demo8();
        // new TestDemo().demo9();
        // new TestDemo().demo10();
        // new TestDemo().demo11();
        // new TestDemo().demo12();
        new TestDemo().demo13();

        System.out.println("运行结束");
    }

    /**
     * 事实平等模式
     */
    private void demo13() {
        // identity默认：每次插入都会返回新的FactHandle对象，除非是相同对象
        // equality：使用HashMap来存储所有插入的事实，根据FactHandle插入事实的equals()方法，当插入对象与现有对象不相等时，Drools引擎才会返回新对象

        // 如下简单说明
        // identity：p1 p2是不同实例
        // equality：p1 p2是相同对象
        Person p1 = new Person("John", 45);
        Person p2 = new Person("John", 45);

        // identity
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession1");
        kieSession.insert(p1);
        kieSession.insert(p2);
        FactHandle factHandle1 = kieSession.getFactHandle(p1);
        FactHandle factHandle2 = kieSession.getFactHandle(p2);
        System.out.println(factHandle1.toExternalForm());
        System.out.println(factHandle2.toExternalForm());

        // equality
        KieBaseConfiguration kieBaseConfiguration = kieServices.newKieBaseConfiguration();
        kieBaseConfiguration.setOption(EqualityBehaviorOption.EQUALITY);
        KieBase kieBase = kieContainer.newKieBase(kieBaseConfiguration);
        KieSession eqKieSession = kieBase.newKieSession();
        FactHandle eqFactHandle1 = eqKieSession.getFactHandle(eqKieSession.insert(p1));
        FactHandle eqFactHandle2 = eqKieSession.getFactHandle(eqKieSession.insert(p2));
        System.out.println(eqFactHandle1.toExternalForm());
        System.out.println(eqFactHandle2.toExternalForm());
    }

    /**
     * kieSession池化技术<br/>
     * 在具有大量KIE运行时数据和高系统活动的用例中，会频繁地创建和处理 KIESession</br>
     * KIE会话的高周转率并不总是很耗时，但是当周转率重复数百万次时，该过程可能成为瓶颈，需要大量的清理工作
     */
    private void demo12() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSessionsPool kieSessionsPool = kieContainer.newKieSessionsPool(10);
        KieSession kieSession = kieSessionsPool.newKieSession();
        // 通过池化技术获取kieSession
    }

    /**
     * 有状态-火灾模拟
     */
    private void demo11() {
        KieServices kieServices = KieServices.Factory.get();
        // 找到类路径上所有规则文件
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        // 实例化有状态的KieSession并输入数据
        KieSession kieSession = kieContainer.newKieSession("ksession1");

        String[] names = new String[] {"kitchen", "bedroom", "office", "livingroom"};
        Map<String, Room> name2room = new HashMap<String, Room>();

        for (String name : names) {
            Room room = new Room(name);
            name2room.put(name, room);
            kieSession.insert(room);
            Sprinkler sprinkler = new Sprinkler(room);
            kieSession.insert(sprinkler);
        }

        Fire kitchenFire = new Fire(name2room.get("kitchen"));
        Fire officeFire = new Fire(name2room.get("office"));
        FactHandle kitchenFireHandle = kieSession.insert(kitchenFire);
        FactHandle officeFireHandle = kieSession.insert(officeFire);
        kieSession.fireAllRules();

        kieSession.delete(kitchenFireHandle);
        kieSession.delete(officeFireHandle);

        kieSession.fireAllRules();
    }

    /**
     * 无状态-驾照规则模拟
     */
    private void demo10() {
        // 创建驾照参与对象
        Applicant applicant1 = new Applicant("laher1", 15);
        Applicant applicant2 = new Applicant("laher2", 20);

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        // kieServices.getKieClasspathContainer();

        // // 有状态
        // KieSession kieSession = kieContainer.newKieSession("ksession7");
        //
        // kieSession.insert(applicant1);
        // kieSession.insert(applicant2);
        //
        // kieSession.fireAllRules();
        //
        // System.out.println(applicant1.toString());
        // System.out.println(applicant2.toString());

        // 无状态
        StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession("ksession7");
        statelessKieSession.execute(applicant1);
        statelessKieSession.execute(applicant2);

        System.out.println(applicant1.toString());
        System.out.println(applicant2.toString());

        // 有状态和无状态的区别
        // 无状态会话，不使用推理
        // 推理的简单理解：当触发A规则同时满足B规则且执行，多个规则触发。
        // 有状态的kieSession是使用推理，随时间对事实进行迭代更改的会话
        // 在有状态的KIE会话中，来自KIE会话的先前调用（先前的会话状态）的数据将在会话调用之间保留，而在无状态的KIE会话中，该数据将被丢弃。
    }

    /**
     * Drools引擎是Drools中的规则引擎。Drools引擎存储，处理和评估数据以执行您定义的业务规则或决策模型<br/>
     * Drools引擎的基本功能是将传入的数据或事实与规则条件进行匹配，并确定是否以及如何执行规则<br/>
     * 规则：您定义的业务规则或DMN决策。所有规则必须至少包含触发该规则的条件以及该规则指示的操作<br/>
     * 事实：在Drools引擎中输入或更改的数据，Drools引擎将其匹配到规则条件以执行适用的规则<br/>
     * 生产内存：在Drools引擎中存储规则的位置<br/>
     * 工作记忆：事实存储在Drools引擎中的位置<br/>
     * 议程：已注册激活规则并对其进行排序（如果适用）以准备执行的位置<br/>
     * 
     */

    /**
     * 多个不同目录drl的资源，通过package来关联
     */
    private void demo9() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession6");

        // 入参list
        List<Integer> ls = new ArrayList<>();
        kieSession.setGlobal("list", ls);
        kieSession.insert(1);
        kieSession.fireAllRules();

        // 允许ksession6（packages="org.some.*"）下的所有规则
        // 结果 [2, 1]
        System.out.println(ls.toString());
    }

    /**
     * KieBase可以包含来自另一个KieBase <br/>
     * pom中依赖其他的jar，能够触发依赖关系 <br/>
     * 本次演示在当前项目中触发，不创建新项目
     */
    private void demo8() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession2");
        kieSession.setGlobal("out", System.out);

        kieSession.insert(new Message("Dave", "Hello, HAL. Do you read me, HAL?"));
        kieSession.fireAllRules();

        kieSession.insert(new Message("Dave", "Open the pod bay doors, HAL."));
        kieSession.fireAllRules();
    }

    /**
     * 通过配置中获取session
     */
    private void demo7() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();

        KieSession kieSession = kieContainer.newKieSession("ksession1");
        kieSession.setGlobal("out", System.out);
        kieSession.insert(new Message("laher1", "Hello, HAL. Do you read me, HAL?"));
        kieSession.fireAllRules();
    }

    /**
     * System.out static下drools会报错 开始
     */
    private void demo6() {
        KieSession kieSession = getKieContainer().newKieSession();
        // Hal1.drl 拟定两个规则
        // 设置全局对象
        kieSession.setGlobal("out", System.out);
        // 保存对象，只符合rule 1规则，所以输出名称和消息
        kieSession.insert(new Message("laher", "帅气迷人"));
        // 保存对象，首先符合规则1输出名称和消息，在符合规则2（text内容一样）
        // 则添加一个message对象，而该对象符合规则1，则输出名称和消息
        kieSession.insert(new Message("laher1", "Hello, HAL. Do you read me, HAL?"));
        // 启动所有规则
        kieSession.fireAllRules();
    }

    /**
     * 全局命令
     */
    private static void demo5() {
        KieBase kieBase = getKieContainer().getKieBase();
        StatelessKieSession statelessKieSession = kieBase.newStatelessKieSession();
        // 单独执行命令
        // ExecutionResults bresults =
        // statelessKieSession.execute(CommandFactory.newSetGlobal("", new Cheese("stilton"), true));
        // Cheese cheese = bresults.getValue("stilton");

        // 批量执行命令
        List cmds = new ArrayList();
        cmds.add(CommandFactory.newStartProcess("process cheeses"));
        cmds.add(CommandFactory.newQuery("cheeses", ""));
        ExecutionResults bresults = statelessKieSession.execute(CommandFactory.newBatchExecution(cmds));
        Cheese stilton = (Cheese)bresults.getValue("stilton");
        QueryResults qresults = (QueryResults)bresults.getValue("cheeses");
    }

    /**
     * 加载事件
     */
    private static void demo4() {
        getKieContainer().newKieSession().addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                super.afterMatchFired(event);
                System.out.println("到这里：" + event);
            }
        });
    }

    /**
     * releaseId 创建，在KieContainer上注册并且启动KieScanner
     */
    private static void demo3() {
        KieServices kieServices = getKieServices();
        ReleaseId releaseId = kieServices.newReleaseId("org.acme", "myartifact", "1.0-SNAPSHOT");
        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
        // 扫描器
        KieScanner kieScanner = kieServices.newKieScanner(kieContainer);

        // 每10秒启动KieScanner轮询Maven存储库
        kieScanner.start(1000L);
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
        KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel("KBase1").setDefault(true)
            .setEventProcessingMode(EventProcessingOption.CLOUD).setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
            .setDeclarativeAgenda(DeclarativeAgendaOption.ENABLED);

        // 创建session
        KieSessionModel kieSessionModel1 = kieBaseModel1.newKieSessionModel("KSession2_2").setDefault(true)
            .setType(KieSessionModel.KieSessionType.STATEFUL).setClockType(ClockTypeOption.get("realtime"));

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