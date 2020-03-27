package com.simplerobot.core.springboot.configuration;

import com.forte.qqrobot.log.QQLogBack;
import org.slf4j.Logger;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class SpringbootQQLogBack {

    private Logger logger;

    public SpringbootQQLogBack(Logger logger) {
        this.logger = logger;
    }

    public QQLogBack getLogBack() {
        return (m, l, e) -> {
            switch (l) {
                case DEBUG:
                    if (e == null) {
                        logger.debug(m);
                    } else {
                        logger.debug(m, e);
                    }
                    break;
                case WARNING:
                    if (e == null) {
                        logger.warn(m);
                    } else {
                        logger.warn(m, e);
                    }
                    break;
                case ERROR:
                    if (e == null) {
                        logger.error(m);
                    } else {
                        logger.error(m, e);
                    }
                    break;
                    // info & success
                default:
                    if (e == null) {
                        logger.info(m);
                    } else {
                        logger.info(m, e);
                    }
                    break;
            }
            return false;
        };

    }

}
