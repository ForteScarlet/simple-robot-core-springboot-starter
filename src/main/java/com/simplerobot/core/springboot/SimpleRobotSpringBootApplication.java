package com.simplerobot.core.springboot;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 现在只需要使用{@link SpringBootApplication}即可。
 * @deprecated
 */
@Retention(RetentionPolicy.RUNTIME)    //注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE}) //接口、类、枚举、注解、方法
@SpringBootApplication
@ComponentScans({
        @ComponentScan({
                "com.simplerobot.core.springboot.configuration",
                "com.simplerobot.component.springboot.configuration"
        }),
})
@Deprecated
public @interface SimpleRobotSpringBootApplication {
        @AliasFor(annotation = SpringBootApplication.class)
        Class<?>[] exclude() default {};
        @AliasFor(annotation = SpringBootApplication.class)
        String[] excludeName() default {};
        @AliasFor(annotation = SpringBootApplication.class)
        String[] scanBasePackages() default {};
        @AliasFor(annotation = SpringBootApplication.class)
        Class<?>[] scanBasePackageClasses() default {};
        @AliasFor(annotation = SpringBootApplication.class)
        boolean proxyBeanMethods() default true;
}
