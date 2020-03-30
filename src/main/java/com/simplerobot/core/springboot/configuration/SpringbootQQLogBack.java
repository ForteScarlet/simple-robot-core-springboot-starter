package com.simplerobot.core.springboot.configuration;

import static com.forte.qqrobot.log.LogLevel.*;
import com.forte.qqrobot.log.QQLogBack;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class SpringbootQQLogBack {

    private Logger logger;

    private AtomicReference<QQLogBack> logBackSingleton = new AtomicReference<>(null);

    public SpringbootQQLogBack(Logger logger) {
        this.logger = logger;
    }

    public QQLogBack getLogBack() {
        QQLogBack logBack = logBackSingleton.updateAndGet((old) -> old != null ? old : (m, l, e) -> {
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
        });
        return logBack;
    }

}
