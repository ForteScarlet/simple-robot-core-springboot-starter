package com.simplerobot.core.springboot.configuration;

import com.forte.config.ConfigurationHelper;
import com.forte.config.InjectableConfig;
import com.forte.qqrobot.ConfigProperties;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.function.BiConsumer;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class EnvironmentConfigProperties extends ConfigProperties {
    private final ConfigProperties configProperties;
    private final Environment environment;

    public EnvironmentConfigProperties(ConfigProperties configProperties, Environment environment) {
        this.configProperties = configProperties;
        this.environment = environment;
    }


    /**
     * 根据{@link com.forte.config.Conf}的注解信息以及配置文件中的内容将配置信息注入到此类中。
     * 手动指定Class类对象
     */
    @Override
    public <T> void injectToConfig(T config, Class<T> configType) {
        InjectableConfig<T> injectableConfig = ConfigurationHelper.toInjectable(configType);
        injectableConfig.inject(config, this);
    }

    @Override
    public String getProperty(String key) {
        final String get = configProperties.getProperty(key);
        if (get != null) {
            return get;
        }
        return environment.getProperty(key);
    }


    /**
     * override this load method
     */
    @Override
    public void load(Reader reader) throws IOException {
        configProperties.load(reader);
    }

    /**
     * override this load method
     */
    @Override
    public void load(InputStream inputStream) throws IOException {
        configProperties.load(inputStream);
    }

    /**
     * override this load method
     */
    @Override
    public void load(InputStream inputStream, String charset) throws IOException {
        configProperties.load(inputStream, charset);
    }

    /**
     * key-value foreach
     */
    @Override
    public void foreach(BiConsumer<String, String> foreachConsumer) {
        configProperties.foreach(foreachConsumer);
    }


}
