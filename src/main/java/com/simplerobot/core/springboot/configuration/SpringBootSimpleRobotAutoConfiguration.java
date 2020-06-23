package com.simplerobot.core.springboot.configuration;

import com.forte.qqrobot.*;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.log.LogLevel;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.log.QQLogBack;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection", "unchecked"})
@Configuration
public class SpringBootSimpleRobotAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger("SimpleRobotAutoConfiguration " + FaceUtil.getFace());

    /**
     * 主要用来获取main方法中的args参数
     */
    @Autowired
    private ApplicationArguments arguments;

    @Autowired
    private BeanFactory beanFactory;

    @Bean
    public SimpleRobotContext getSimpleRobotContext(BaseApplication baseApplication, Application app, SpringbootQQLogBack logBack) {
        LOGGER.info("load application " + baseApplication.getClass() + " and app " + app.getApplicationClass());
        final QQLogBack springLogBack = logBack.getLogBack();
        if(QQLog.getLogBack() != springLogBack){
            QQLog.changeQQLogBack(springLogBack);
        }

        LogLevel minValue = LogLevel.DEBUG;
        for (LogLevel value : LogLevel.values()) {
            if(value.getLevel() < minValue.getLevel()){
                minValue = value;
            }
        }
        // 将日志等级设置为最低（越低输出量越多）
        QQLog.setGlobalLevel(minValue);

        final SimpleRobotContext context = baseApplication.runWithApplication(app, arguments == null ? new String[0] : arguments.getSourceArgs());
        final BaseConfiguration conf = baseApplication.getDependCenter().get(BaseConfiguration.class);
        if(conf.getScannerPackage().isEmpty()){
            LOGGER.warn("no scan package! please set 'simbot.core.scannerPackage'");
            LOGGER.warn("      ↓↓↓↓↓↓↓↓");
            LOGGER.warn("检测到未指定包扫描路径！可能有很大概率出现监听函数无法扫描加载的问题。建议在配置文件中追加配置项'simbot.core.scannerPackage'以指定监听函数的扫描路径。");
            LOGGER.warn("      ↑↑↑↑↑↑↑↑");
        }

        return context;
    }


    @Bean
    public SpringBootDependGetter getSpringBootDependGetter(){
        return new SpringBootDependGetter(beanFactory);
    }


    @Bean
    public SimpleRobotNameLogger getRobotNameLogger(){
        return new SimpleRobotNameLogger(FaceUtil.getFace());
    }


    /**
     * 获取一个springboot整合log
     */
    @Bean
    public SpringbootQQLogBack getSpringbootQQLogBack(SimpleRobotNameLogger logger){
        return new SpringbootQQLogBack(logger.getLogger());
    }

    /**
     * 向Springboot注入字符串消息转化器
     */
    @Bean
    @ConditionalOnBean(SimpleRobotContext.class)
    public MsgParser getMsgParser(SimpleRobotContext context) {
        return context.getMsgParser();
    }

    /**
     * 向Springboot注入监听消息触发器
     */
    @Bean
    @ConditionalOnBean(SimpleRobotContext.class)
    public MsgProcessor getMsgProcessor(SimpleRobotContext context) {
        return context.getMsgProcessor();
    }

    /**
     * 向Springboot注入bot管理器
     */
    @Bean
    @ConditionalOnBean(SimpleRobotContext.class)
    public BotManager getBotManager(SimpleRobotContext context) {
        return context.getBotManager();
    }

    /**
     * 向Springboot注入simpleRobot的依赖中心
     */
    @Bean
    @ConditionalOnBean(SimpleRobotContext.class)
    public DependCenter getDependCenter(SimpleRobotContext context) {
        return context.getDependCenter();
    }
    /**
     * 向Springboot注入SENDER
     */
    @Bean
    @ConditionalOnBean(SimpleRobotContext.class)
    public SenderSendList getSenderSendList(SimpleRobotContext context) {
        return context.SENDER;
    }
    /**
     * 向Springboot注入GETTER
     */
    @Bean
    @ConditionalOnBean(SimpleRobotContext.class)
    public SenderGetList getSenderGetList(SimpleRobotContext context) {
        return context.GETTER;
    }
    /**
     * 向Springboot注入SETTER
     */
    @Bean
    @ConditionalOnBean(SimpleRobotContext.class)
    public SenderSetList getSenderSetList(SimpleRobotContext context) {
        return context.SETTER;
    }



}
