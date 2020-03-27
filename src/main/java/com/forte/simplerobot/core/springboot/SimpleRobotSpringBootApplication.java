package com.forte.simplerobot.core.springboot;


import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * enable auto config simple-robot with springboot
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@Retention(RetentionPolicy.RUNTIME)    //注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE}) //接口、类、枚举、注解、方法
@SpringBootApplication
@ComponentScans(value = {
        @ComponentScan,
        @ComponentScan({
                "com.forte.simplerobot.core.springboot.configuration",
                "com.forte.simplerobot.component.springboot.configuration"
        }),
        @ComponentScan(excludeFilters = { @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
})
public @interface SimpleRobotSpringBootApplication {

        //**************** springbootApplication的注解 ****************//


        /**
         * Exclude specific auto-configuration classes such that they will never be applied.
         * @return the classes to exclude
         */
        @AliasFor(annotation = SpringBootApplication.class)
        Class<?>[] exclude() default {};

        /**
         * Exclude specific auto-configuration class names such that they will never be
         * applied.
         * @return the class names to exclude
         * @since 1.3.0
         */
        @AliasFor(annotation = SpringBootApplication.class)
        String[] excludeName() default {};

        /**
         * Base packages to scan for annotated components. Use {@link #scanBasePackageClasses}
         * for a type-safe alternative to String-based package names.
         * <p>
         * <strong>Note:</strong> this setting is an alias for
         * {@link ComponentScan @ComponentScan} only. It has no effect on {@code @Entity}
         * scanning or Spring Data {@link Repository} scanning. For those you should add
         * {@link org.springframework.boot.autoconfigure.domain.EntityScan @EntityScan} and
         * {@code @Enable...Repositories} annotations.
         * @return base packages to scan
         * @since 1.3.0
         */
        @AliasFor(annotation = SpringBootApplication.class)
        String[] scanBasePackages() default {};

        /**
         * Type-safe alternative to {@link #scanBasePackages} for specifying the packages to
         * scan for annotated components. The package of each class specified will be scanned.
         * <p>
         * Consider creating a special no-op marker class or interface in each package that
         * serves no purpose other than being referenced by this attribute.
         * <p>
         * <strong>Note:</strong> this setting is an alias for
         * {@link ComponentScan @ComponentScan} only. It has no effect on {@code @Entity}
         * scanning or Spring Data {@link Repository} scanning. For those you should add
         * {@link org.springframework.boot.autoconfigure.domain.EntityScan @EntityScan} and
         * {@code @Enable...Repositories} annotations.
         * @return base packages to scan
         * @since 1.3.0
         */
        @AliasFor(annotation = SpringBootApplication.class)
        Class<?>[] scanBasePackageClasses() default {};

        /**
         * Specify whether {@link Bean @Bean} methods should get proxied in order to enforce
         * bean lifecycle behavior, e.g. to return shared singleton bean instances even in
         * case of direct {@code @Bean} method calls in user code. This feature requires
         * method interception, implemented through a runtime-generated CGLIB subclass which
         * comes with limitations such as the configuration class and its methods not being
         * allowed to declare {@code final}.
         * <p>
         * The default is {@code true}, allowing for 'inter-bean references' within the
         * configuration class as well as for external calls to this configuration's
         * {@code @Bean} methods, e.g. from another configuration class. If this is not needed
         * since each of this particular configuration's {@code @Bean} methods is
         * self-contained and designed as a plain factory method for container use, switch
         * this flag to {@code false} in order to avoid CGLIB subclass processing.
         * <p>
         * Turning off bean method interception effectively processes {@code @Bean} methods
         * individually like when declared on non-{@code @Configuration} classes, a.k.a.
         * "@Bean Lite Mode" (see {@link Bean @Bean's javadoc}). It is therefore behaviorally
         * equivalent to removing the {@code @Configuration} stereotype.
         * @since 2.2
         * @return whether to proxy {@code @Bean} methods
         */
        @AliasFor(annotation = SpringBootApplication.class)
        boolean proxyBeanMethods() default true;

}
