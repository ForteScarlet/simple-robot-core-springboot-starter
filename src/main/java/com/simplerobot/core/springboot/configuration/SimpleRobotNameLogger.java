package com.simplerobot.core.springboot.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class SimpleRobotNameLogger {

    private String applicationName;

    public SimpleRobotNameLogger(String applicationName){
        this.applicationName = applicationName;
    }

    public Logger getLogger(){
        return LoggerFactory.getLogger("simple-robot-app " + applicationName);
    }

}
