package com.simplerobot.core.springboot.configuration;

import com.forte.qqrobot.depend.DependGetter;
import org.springframework.beans.factory.BeanFactory;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class SpringBootDependGetter implements DependGetter {

    private BeanFactory beanFactory;

    public SpringBootDependGetter(BeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    /**
     * 通过Class对象获取实例
     */
    @Override
    public <T> T get(Class<T> clazz) {
        try {
            return beanFactory.getBean(clazz);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 通过名称和类型获取指定类型的对象实例
     */
    @Override
    public <T> T get(String name, Class<T> type) {
        try {
            return beanFactory.getBean(name, type);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 仅通过名称获取对象实例
     */
    @Override
    public Object get(String name) {
        try {
            return beanFactory.getBean(name);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public <T> T constant(String name, Class<T> type) {
        return null;
    }

    @Override
    public Object constant(String name) {
        return null;
    }
}
