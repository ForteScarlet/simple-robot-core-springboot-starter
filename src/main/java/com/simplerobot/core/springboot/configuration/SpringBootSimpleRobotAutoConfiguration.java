package com.simplerobot.core.springboot.configuration;

import com.forte.qqrobot.*;
import com.forte.qqrobot.anno.depend.Beans;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Configuration
public class SpringBootSimpleRobotAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger("SimpleRobotAutoConfiguration");

    /**
     * 主要用来获取main方法中的args参数
     */
    @Autowired(required = false)
    private ApplicationArguments arguments;

    @Autowired
    private BeanFactory beanFactory;

    @Bean
//    @ConditionalOnBean({BaseApplication.class, Application.class})
    public <
            CONFIG extends BaseConfiguration,
            SEND extends SenderSendList,
            SET extends SenderSetList,
            GET extends SenderGetList,
            CONTEXT extends SimpleRobotContext<SEND, SET, GET>
            >
    CONTEXT getSimpleRobotContext(BaseApplication<CONFIG, SEND, SET, GET, CONTEXT> baseApplication, Application<CONFIG> app, SpringbootQQLogBack logBack) {
        LOGGER.info("load application " + baseApplication.getClass() + " and app " + app.getApplicationClass());
        final QQLogBack springLogBack = logBack.getLogBack();
        if(QQLog.getLogBack() != springLogBack){
            QQLog.changeQQLogBack(springLogBack);
        }
        final CONTEXT context = baseApplication.runWithApplication(app, arguments == null ? new String[0] : arguments.getSourceArgs());
        LogLevel minValue = LogLevel.DEBUG;
        for (LogLevel value : LogLevel.values()) {
            if(value.getLevel() < minValue.getLevel()){
                minValue = value;
            }
        }
        // 将日志等级设置为最低（越低输出量越多），且组件启动器此时应当已经将日志处理交由spring的日志管理
        QQLog.setGlobalLevel(minValue);
        return context;
    }

//    @Bean
//    @ConditionalOnMissingBean({BaseApplication.class, Application.class})
//    public SimpleRobotContext getSimpleRobotContextOnMissAll(SpringbootQQLogBack logBack) throws IOException {
//        LOGGER.info("auto load application and app.");
//        final QQLogBack springLogBack = logBack.getLogBack();
//        if(QQLog.getLogBack() != springLogBack){
//            QQLog.changeQQLogBack(springLogBack);
//        }
//        final SimpleRobotContext context = BaseApplication.runAuto(BaseApp.class, arguments == null ? new String[0] : arguments.getSourceArgs());
//        LogLevel minValue = LogLevel.DEBUG;
//        for (LogLevel value : LogLevel.values()) {
//            if(value.getLevel() < minValue.getLevel()){
//                minValue = value;
//            }
//        }
//        // 将日志等级设置为最低（越低输出量越多），且组件启动器此时应当已经将日志处理交由spring的日志管理
//        QQLog.setGlobalLevel(minValue);
//        return context;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean({BaseApplication.class})
//    public SimpleRobotContext getSimpleRobotContextOnMissBaseApplication(Application app, SpringbootQQLogBack logBack) throws IOException {
//        LOGGER.info("auto load application, load app " + app.getApplicationClass());
//        final QQLogBack springLogBack = logBack.getLogBack();
//        if(QQLog.getLogBack() != springLogBack){
//            QQLog.changeQQLogBack(springLogBack);
//        }
//        final SimpleRobotContext context = BaseApplication.runAuto(app.getApplicationClass(), arguments == null ? new String[0] : arguments.getSourceArgs());
//        LogLevel minValue = LogLevel.DEBUG;
//        for (LogLevel value : LogLevel.values()) {
//            if(value.getLevel() < minValue.getLevel()){
//                minValue = value;
//            }
//        }
//        // 将日志等级设置为最低（越低输出量越多），且组件启动器此时应当已经将日志处理交由spring的日志管理
//        QQLog.setGlobalLevel(minValue);
//        return context;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean({Application.class})
//    public SimpleRobotContext getSimpleRobotContextOnMissApplication(BaseApplication application, SpringbootQQLogBack logBack) throws IOException {
//        LOGGER.info("auto load app, load application " + application.getClass());
//        final QQLogBack springLogBack = logBack.getLogBack();
//        if(QQLog.getLogBack() != springLogBack){
//            QQLog.changeQQLogBack(springLogBack);
//        }
//        final SimpleRobotContext context = application.runWithApplication(new BaseApp(), arguments == null ? new String[0] : arguments.getSourceArgs());
//        LogLevel minValue = LogLevel.DEBUG;
//        for (LogLevel value : LogLevel.values()) {
//            if(value.getLevel() < minValue.getLevel()){
//                minValue = value;
//            }
//        }
//        // 将日志等级设置为最低（越低输出量越多），且组件启动器此时应当已经将日志处理交由spring的日志管理
//        QQLog.setGlobalLevel(minValue);
//        return context;
//    }



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
    public MsgParser getMsgParser(SimpleRobotContext context) {
        return context.getMsgParser();
    }

    /**
     * 向Springboot注入监听消息触发器
     */
    @Bean
    public MsgProcessor getMsgProcessor(SimpleRobotContext context) {
        return context.getMsgProcessor();
    }

    /**
     * 向Springboot注入bot管理器
     */
    @Bean
    public BotManager getBotManager(SimpleRobotContext context) {
        return context.getBotManager();
    }

    /**
     * 向Springboot注入simpleRobot的依赖中心
     */
    @Bean
    public DependCenter getDependCenter(SimpleRobotContext context) {
        return context.getDependCenter();
    }
    /**
     * 向Springboot注入SENDER
     */
    @Bean
    public SenderSendList getSenderSendList(SimpleRobotContext context) {
        return context.SENDER;
    }
    /**
     * 向Springboot注入GETTER
     */
    @Bean
    public SenderGetList getSenderGetList(SimpleRobotContext context) {
        return context.GETTER;
    }
    /**
     * 向Springboot注入SETTER
     */
    @Bean
    public SenderSetList getSenderSetList(SimpleRobotContext context) {
        return context.SETTER;
    }



}
