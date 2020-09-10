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
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.conf.DeclarativeAgendaOption;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.rule.Rule;
import org.kie.api.definition.type.FactType;
import org.kie.api.definition.type.Position;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.internal.utils.KieService;
import org.kie.api.runtime.*;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.conf.TimedRuleExecutionFilter;
import org.kie.api.runtime.conf.TimedRuleExecutionOption;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Variable;
import org.kie.internal.command.CommandFactory;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author laher
 * @date 2020/9/7/007
 */
public class TestDemo {
    public static void main(String[] args) throws Exception {
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
        // new TestDemo().demo13();
        // new TestDemo().demo14();
        // new TestDemo().demo15();
        // new TestDemo().demo16();
        // new TestDemo().demo17();
        // new TestDemo().demo18();
        // new TestDemo().demo19();
        // new TestDemo().demo20();
        // new TestDemo().demo21();
        // new TestDemo().demo22();
        // new TestDemo().demo23();
        // new TestDemo().demo24();

        System.out.println("运行结束");
    }

    /**
     * 规则条件，when包含执行操作和必须满足的条件，如果when部分为空则条件结果为真</br>
     * 则then第一次fireAllRules()会执行内部操作</br>
     * 组合条件没有定义关键字（or not and）默认为and
     */

    /**
     * todo Phreak规则算法，Phreak传播面向集合。</br>
     * 当Drools引擎启动时，所有规则都被视为与可能触发规则的模式匹配数据断开链接
     */

    /**
     * time表达式定时器 todo 未完成 无法扫描赋值到Bean对象
     */
    private void demo24() throws IllegalAccessException, InstantiationException {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSessionConfiguration kieSessionConfiguration = kieServices.newKieSessionConfiguration();
        kieSessionConfiguration.setOption(TimedRuleExecutionOption.YES);
        KieSession kieSession = kieContainer.newKieSession("ksession1", kieSessionConfiguration);
        kieSession.setGlobal("fmt", new SimpleDateFormat("HH:mm:ss"));

        // 获取declare Bean
        FactType bean = kieSession.getKieBase().getFactType("com.laher.test.entity", "Bean");
        Object beanObj = bean.newInstance();
        bean.set(beanObj, "delay", "1s");
        // bean.set(beanObj, "period", 1);

        List<Command> ls = new ArrayList<>();
        ls.add(kieServices.getCommands().newInsert(bean));

        // 执行命令
        // kieSession.execute(kieServices.getCommands().newBatchExecution(ls));
        kieSession.execute(CommandFactory.newBatchExecution(ls));
        kieSession.fireAllRules();
    }

    /**
     * time计时器被动模式下执行
     */
    private void demo23() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSessionConfiguration kieSessionConfiguration = kieServices.newKieSessionConfiguration();
        // 修改为活跃模式
        // kieSessionConfiguration.setOption(TimedRuleExecutionOption.YES);
        // 配置可过滤自动执行哪些定时规则
        kieSessionConfiguration.setOption(new TimedRuleExecutionOption.FILTERED((Rule[] rules) -> {
            // true 不过滤且执行 false 过滤且不执行
            System.out.println(rules[0].getName());
            return rules[0].getName().equals("rule-timer-int");
            // return rules[0].getName().equals("rule-timer-int111111");
        }));
        KieSession kieSession = kieContainer.newKieSession("ksession1", kieSessionConfiguration);

        // 和demo22一样
        kieSession.setGlobal("fmt", new SimpleDateFormat("HH:mm:ss"));
        kieSession.insert(new Alarm("laherr"));
        System.out.println("start");
        kieSession.fireAllRules();
    }

    /**
     * timer计时器规则属性</br>
     * 
     */
    private void demo22() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession1");
        kieSession.setGlobal("fmt", new SimpleDateFormat("HH:mm:ss"));
        kieSession.insert(new Alarm("laherr"));
        System.out.println("start");
        // step1
        // 无效果-被动模式
        // kieSession.fireAllRules();

        // step2
        // Drools引擎使用计时器处理规则的方式，取决于Drools引擎是处于主动模式还是被动模式
        // 当触发重复规则结果，由计时器控制的规则将变为活动状态
        // 默认情况，Drools引擎将以被动模式运行并根据定义的计时器设置评估规则fireAllRules()
        // 否则Drools引擎将以活动模式启动并持续评估规则，直到用户或应用程序显式调用为止halt()。
        // 直到停止
        kieSession.fireUntilHalt();
    }

    /**
     * drl-Drools规则语言，可直接在.drl文本文件中定义的业务规则</br>
     * 元数据：</br>
     * 新的事实类型：可以根据需要在DRL文件中声明其他类型</br>
     * 数据类型的元数据：将格式中的元数据@key(value)与新的或现有的数据相关联</br>
     * 可与数据类型或者数据属性关联，元数据可以是数据属性未表示的任何类型的数据，并且在该数据类型的所有实例之间都是一致的
     */
    private void demo21() throws IllegalAccessException, InstantiationException {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession1");

        // 获取drl类型定义
        FactType factType = kieSession.getKieBase().getFactType("com.laher.test.entity", "PersonDemo");
        // 创建对象以及设置属性
        Object demo1 = factType.newInstance();
        factType.set(demo1, "name", "张三");
        factType.set(demo1, "dateOfBirth", new Date());

        /*// address是drl类型数据所以需要取出对象
        FactType demo1AddFactType = kieSession.getKieBase().getFactType("com.laher.test.entity", "Address");
        Object demo1Address = demo1AddFactType.newInstance();
        demo1AddFactType.set(demo1Address, "number", 100);
        demo1AddFactType.set(demo1Address, "city", "北京");
        // 设置address属性
        factType.set(demo1, "address", demo1AddFactType.getFactClass());*/
        factType.set(demo1, "address.number", 100);
        factType.set(demo1, "address.city", "北京");

        // dayOff是drl枚举属性
        /*FactType demo1WeekFactType = kieSession.getKieBase().getFactType("com.laher.test.entity", "DaysOfWeek");
        Object demo1Week = demo1WeekFactType.newInstance();
        demo1WeekFactType.set(demo1Week, "fullName", "Sunday");
        // 设置DaysOfWeek枚举属性
        factType.set(demo1, "dayOff", demo1WeekFactType);*/

        // 批量新增保存运行命令
        List<Command> ls = new ArrayList<>();
        ls.add(kieServices.getCommands().newInsert(factType));
        // ls.add(kieServices.getCommands().newInsert(demo1AddFactType));
        // ls.add(kieServices.getCommands().newInsert(demo1WeekFactType));

        // 执行
        kieSession.execute(kieServices.getCommands().newBatchExecution(ls));
    }

    /**
     * declare定义，通过rule规则进行过滤查询，简单封装
     */
    private void demo20() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession1");
        kieSession.insert(new Person("张三1", 20, "office", "desk"));
        kieSession.insert(new Person("张三2", 18, "kitchen", "pear"));
        kieSession.fireAllRules();

        // 不同query的查询方式
        // 执行查询likes=office
        QueryResults queryResultsRows =
            kieSession.getQueryResults("isContainedIn", new Object[] {Variable.v, "office"});
        for (QueryResultsRow row : queryResultsRows) {
            System.out.println(row.get("x") + "--" + row.get("y"));
        }
        System.out.println("----------------");

        // checkAge
        QueryResults queryResultsRows2 = kieSession.getQueryResults("checkAge");
        for (QueryResultsRow row : queryResultsRows2) {
            System.out.println(row.get("location"));
        }
        System.out.println("----------------");

        // checkAge2
        QueryResults results = kieSession.getQueryResults("checkAge2", 18);
        for (QueryResultsRow row : results) {
            System.out.println(row.get("location"));
        }
        System.out.println("----------------");

        // 输出结果：
        // Person{name='张三1', age=20, likes='office', address='desk'}
        // Person{name='张三2', age=18, likes='kitchen', address='pear'}
        // desk--office
        // ----------------
        // Location( thing=desk, location=office, age=20 )
        // ----------------
        // Location( thing=desk, location=office, age=20 )
        // Location( thing=pear, location=kitchen, age=18 )
        // ----------------
    }

    /**
     * declare定义，通过fact的命令和查询来进行过去查询,操作起来繁琐
     */
    private void demo19() throws IllegalAccessException, InstantiationException {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession1");

        FactType locationType = kieSession.getKieBase().getFactType("com.laher.test.entity", "Location");

        // a pear is in the kitchen
        final Object pear = locationType.newInstance();
        locationType.set(pear, "thing", "pear");
        locationType.set(pear, "location", "kitchen");

        // a desk is in the office
        final Object desk = locationType.newInstance();
        locationType.set(desk, "thing", "desk");
        locationType.set(desk, "location", "office");

        // create working memory objects
        final List<Command<?>> commands = new ArrayList<Command<?>>();
        // Location instances
        commands.add(kieServices.getCommands().newInsert(pear));
        commands.add(kieServices.getCommands().newInsert(desk));

        // 将所有通过命令方式运行
        // 获取执行结果
        // fire all rules
        /*final String queryAlias = "myQuery";
        commands.add(kieServices.getCommands().newQuery(queryAlias, "isContainedIn", new Object[] { Variable.v, "office" }));
        
        final ExecutionResults results = kieSession.execute(kieServices.getCommands().newBatchExecution(commands));
        final QueryResults qResults = (QueryResults) results.getValue(queryAlias);
        
        final List<String> ls = new ArrayList<String>();
        for (QueryResultsRow r : qResults) {
            ls.add((String) r.get("x"));
        }
        System.out.println(ls.get(0));*/

        // 命令执行一部分，查询query分离执行
        // 获取执行结果
        kieSession.execute(kieServices.getCommands().newBatchExecution(commands));
        QueryResults results = kieSession.getQueryResults("isContainedIn", new Object[] {Variable.v, "office"});
        List<List<String>> ls = new ArrayList<List<String>>();
        for (QueryResultsRow r : results) {
            ls.add(Arrays.asList(new String[] {(String)r.get("x"), (String)r.get("y")}));
        }
        System.out.println(ls.size());
        System.out.println(ls.get(0).get(0) + "-" + ls.get(0).get(1));
    }

    /**
     * 调用drl中的函数 function
     */
    private void demo18() {
        // 调用drl中的函数
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession1");
        kieSession.insert(new Query("2"));
        kieSession.fireAllRules();
        // 结果：
        // Query{q=2}
        // Hal14 say Hello 2!!
    }

    /**
     * 在引擎的工作内存中搜索与DRL文件中的规则相关的数据</br>
     * 数据传播模式 Lazy: (Default)数据是在规则执行时在批处理集合中传播的，而不是实时的，因为数据是由用户或应用程序单独插入的</br>
     * Immediate: 数据按照用户或应用程序插入事实的顺序立即传播</br>
     * Eager: 数据（在批处理集合中）延迟传播，但在规则执行之前传播
     */
    private void demo17() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession1");

        Query query1 = new Query(1);
        Query query2 = new Query("1");
        Query query3 = new Query("3");
        Query query4 = new Query("1");

        kieSession.insert(query1);
        kieSession.insert(query2);
        kieSession.insert(query3);

        kieSession.fireAllRules();

        // 特别注意：query4未执行，只是加入空间中
        kieSession.insert(query4);
        /**
         * query的名字是全局性的</br>
         * 功能是将运行过的规则数据进行存储（working memory），做二次查询操作
         */
        System.out.println("-------分割线--------");
        QueryResults result = kieSession.getQueryResults("query1");
        System.out.println("大小为：" + result.size());
        for (QueryResultsRow row : result) {
            Query query = (Query)row.get("$query");
            System.out.println("遍历结果：" + query.getQ());
        }
        /**
         * 结果</br>
         * Query{q=1}</br>
         * Query{q=1}</br>
         * Query{q=3}</br>
         * -------分割线--------</br>
         * 大小为：3</br>
         * 遍历结果：1</br>
         * 遍历结果：1</br>
         * 遍历结果：1</br>
         *
         * 只要被insert入drools工作空间，都会被进行筛选如：query4</br>
         *
         */
    }

    /**
     * activation-group活动组</br>
     * 同组中合法规则只执行一次，执行后规则将被删除
     */
    private void demo16() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession1");
        kieSession.getAgenda().getAgendaGroup("a-activation").setFocus();

        // 实例化对象
        Alarm alarm1 = new Alarm("laher");
        Alarm alarm2 = new Alarm("laherz");
        // kieSession.insert(alarm1);
        // kieSession.insert(alarm2);
        // kieSession.fireAllRules();
        /**
         * 上面结果：</br>
         * a to laher</br>
         * 第二个laherz没有执行输出命令，在一个fireAllRules规则中</br>
         * 同一个activation-group只允许被执行一次
         */

        // 运行以下请注释fireAllRules 和 insert

        kieSession.insert(alarm1);
        kieSession.fireAllRules();

        kieSession.insert(alarm1);
        kieSession.insert(alarm2);
        kieSession.fireAllRules();
        /**
         * 注释后，上面结果：<br/>
         * a to laher<br/>
         * b to laherz<br/>
         * 被删除后，第二个kieSession.insert(alarm1);是无效的</br>
         * 在整个fireAllRules环节中已经被移除
         */
    }

    /**
     * agenda-group议程组</br>
     * 1. 如果没有指定agenda-group 则默认把所有未指定agenda-group的 rules 都执行一遍</br>
     * 2. 如果指定了agenda-group 使用的时候必须指定该name才能被使用，默认是不能使用的</br>
     * 3. agenda-group name可以重复</br>
     * 4. agenda-group 用于区分rule</br>
     */
    private void demo15() {
        /**
         * 结果：<br/>
         * false say 100 a-agenda: MyFact{field1=false}<br/>
         * true say a-agenda : MyFact{field1=true}<br/>
         * true say : MyFact{field1=true}<br/>
         * 由结果可知，一次请求只对当前规则有效<br/>
         * 而规则内的update、insert会无视agenda-group规则<br/>
         * 触发合法的规则
         */
        MyFact fact = new MyFact(false, 3);

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession1");
        kieSession.getAgenda().getAgendaGroup("a-agenda").setFocus();
        kieSession.insert(fact);
        kieSession.fireAllRules();
    }

    /**
     * Drools规则引擎可能存在符合多个规则模式，通过策略和顺序来进行控制
     */
    private void demo14() {
        MyFact fact = new MyFact(false, 3);

        // 满足规则：
        // 多个规则同时满足，优先级别salience，其次文件顺序，只执行最符合的规则一次
        // 每个rule都有一个整数salience属性（默认0，支持正负），该属性确定执行顺序，越大优先级越高
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession1");
        kieSession.insert(fact);
        kieSession.fireAllRules();
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
        KieBase kieBase = kieContainer.newKieBase("kbase1", kieBaseConfiguration);
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
