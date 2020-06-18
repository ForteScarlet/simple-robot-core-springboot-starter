package com.simplerobot.core.springboot.configuration;

import com.forte.qqrobot.Application;
import com.forte.qqrobot.BaseConfiguration;
import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Deprecated
@SimpleRobotApplication(resources = "/application.properties")
public class BaseApp implements Application {
    private DependGetter dependGetter;

    public BaseApp(SpringBootDependGetter dependGetter){
        this.dependGetter = dependGetter;
    }
    @Override
    public void before(BaseConfiguration configuration) {
        configuration.setDependGetter(dependGetter);
    }
    @Override
    public void after(CQCodeUtil cqCodeUtil, MsgSender sender) { }
}
