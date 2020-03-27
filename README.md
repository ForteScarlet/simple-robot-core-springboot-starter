# simple-robot Springboot快速启动器

[![](https://img.shields.io/badge/simple--robot-core-green)](https://github.com/ForteScarlet/simple-robot-core) [![img](https://camo.githubusercontent.com/f8464f5d605886b8369ab6daf28d7130a72fd80e/68747470733a2f2f696d672e736869656c64732e696f2f6d6176656e2d63656e7472616c2f762f696f2e6769746875622e466f727465536361726c65742f73696d706c652d726f626f742d636f7265)](https://search.maven.org/artifact/io.github.ForteScarlet/simple-robot-core) <br>[![](https://img.shields.io/badge/simple--robot-core--springboot--starter-green)](https://github.com/ForteScarlet/simple-robot-core-springboot-starter)[![](https://img.shields.io/maven-central/v/io.github.ForteScarlet.simple-robot/core-spring-boot-starter)](https://search.maven.org/artifact/io.github.ForteScarlet.simple-robot/component-cqhttp-spring-boot-starter)<br>[![](https://img.shields.io/badge/%E7%9C%8B%E4%BA%91%E6%96%87%E6%A1%A3-doc-green)](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc)  [![](https://img.shields.io/badge/QQ%E7%BE%A4-782930037-blue)](https://jq.qq.com/?_wv=1027&k=57ynqB1)  

# 使用

首先，在使用springboot启动器之前，你需要使用springboot的 **properties** 格式配置文件：`application.properties`, 并将simple-robot中的配置项写入此类配置文件中。
<br>
其次，一般拥有springboot启动器的组件所处的核心系版本，都是从`1.9.x`开始的。

##  组件启动器
我为一些特定的组件提供了其专属的启动器。
<br>
**to be continue...**

## 自定义启动器
如果你想要通过核心启动器来自定义一个启动器，那么你需要先选择导入核心启动器：
> https://search.maven.org/artifact/io.github.ForteScarlet.simple-robot/core-spring-boot-starter/

**Maven**
```xml
        <dependency>
            <groupId>io.github.ForteScarlet.simple-robot</groupId>
            <artifactId>core-spring-boot-starter</artifactId>
            <version>${version}</version>
        </dependency>
```
**Gradle**
```
implementation 'io.github.ForteScarlet.simple-robot:core-spring-boot-starter:${version}'
```

然后参考注解类`@SimpleRobotSpringBootApplication`内容。
`@SimpleRobotSpringBootApplication`内默认继承了springboot的启动类注解`@SpringbootApplication`, 
并且其中会去默认扫描一个组件的自动配置包路径：
- `com.simplerobot.core.springboot.configuration`

核心启动器需要某个组件启动器通过Spring向其提供一些启动所需的必要对象：
- `BaseApplication`的实现类
- 整合了springboot的beanFactory且标注了`@SimpleRobotApplication(resources = "/application.properties")`注解的App接口实例。

而核心会为组件启动器的构建所提供的bean有：
- `SpringbootQQLogBack`对象，用来初始化QQLog的logback，使其与springboot的日志相结合。


# 使用
如果你使用的是我编写的标准版的快速启动器，你只需要将原本Springboot启动器上的`@SpringbootApplication`注解替换为`@SimpleRobotSpringBootApplication`即可。
如果是第三方所提供的启动器，则遵循其定义的规则。

如果启动器完整且正常，你最终可以得到如下内容：
- 当前组件所实现的`BaseApplication`启动器实例，例如CQ HTTP组件的`CoolQHttpApplication`
- 当前组件所实现的，`BaseApplication`启动后所得到的组件实现的`SimpleRobotContext`实例，例如CQ HTTP组件的`CQHttpXContext`
- `SimpleRobotNameLogger`, 一个用来获取logger名称的类
- `SpringbootQQLogBack`，一个用来跟springboot的logger整合的类
- `MsgParser`，当前组件的字符串消息转化器，即将接收到的原始消息字符串转化为`MsgGet`对象的转化器
- `MsgProcessor`，当前组件的监听消息触发器，即传入一个`MsgGet`实例来触发监听消息
- `BotManager`，Bot管理器
- `DependCenter`，simple-robot内部的依赖管理器。在整合了Springboot之后便会主要依赖于Springboot的beanFactory。
- 组件所实现的SENDER(`SenderSendList`接口实例), 例如CQ HTTP组件内部的`CoolQHttpMsgSender`对象实例。
- 组件所实现的GETTER(`SenderGetList`接口实例), 例如CQ HTTP组件内部的`CoolQHttpMsgSender`对象实例。
- 组件所实现的SETTER(`SenderSetList`接口实例), 例如CQ HTTP组件内部的`CoolQHttpMsgSender`对象实例。


# 版本规则
对于所有的启动器版本命名规则定义如下: 
格式为`a.b`, 其中，**`a`** 为此启动器所对应的核心版本系，**`b`** 代表当前启动器的版本。
例如：
`1.9.0`, `1.9.1`

这两个版本中，`a`均为`1.9`，`b`为`0`和`1`，都代表其内部使用的是`1.9.x`的核心（一般来讲都是最后一个版本的核心版本。），只不过`1.9.1`相对于`1.9.0`来讲更新、修复、改善了一些东西。
