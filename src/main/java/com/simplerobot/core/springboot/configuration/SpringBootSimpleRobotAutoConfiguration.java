package com.simplerobot.core.springboot.configuration;

import com.forte.qqrobot.*;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Configuration
public class SpringBootSimpleRobotAutoConfiguration {

    /**
     * 主要用来获取main方法中的args参数
     */
    @Autowired
    private ApplicationArguments arguments;

    @Bean
    public <
            CONFIG extends BaseConfiguration,
            SEND extends SenderSendList,
            SET extends SenderSetList,
            GET extends SenderGetList,
            CONTEXT extends SimpleRobotContext<SEND, SET, GET>
            >
    CONTEXT getSimpleRobotContext(BaseApplication<CONFIG, SEND, SET, GET, CONTEXT> baseApplication, Application<CONFIG> app) {
        return baseApplication.runWithApplication(app, arguments.getSourceArgs());
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



}
