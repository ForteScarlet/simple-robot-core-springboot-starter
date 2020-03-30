# simple-robot Springboot快速启动器

[![](https://img.shields.io/badge/simple--robot-core-green)](https://github.com/ForteScarlet/simple-robot-core) [![img](https://camo.githubusercontent.com/f8464f5d605886b8369ab6daf28d7130a72fd80e/68747470733a2f2f696d672e736869656c64732e696f2f6d6176656e2d63656e7472616c2f762f696f2e6769746875622e466f727465536361726c65742f73696d706c652d726f626f742d636f7265)](https://search.maven.org/artifact/io.github.ForteScarlet/simple-robot-core) <br>[![](https://img.shields.io/badge/simple--robot-core--springboot--starter-green)](https://github.com/ForteScarlet/simple-robot-core-springboot-starter) [![](https://img.shields.io/maven-central/v/io.github.ForteScarlet.simple-robot/core-spring-boot-starter)](https://search.maven.org/artifact/io.github.ForteScarlet.simple-robot/component-cqhttp-spring-boot-starter) [![codebeat badge](https://codebeat.co/badges/b591dd37-ae2d-4296-aefe-3869767f3836)](https://codebeat.co/projects/github-com-fortescarlet-simple-robot-core-springboot-starter-master) <br>[![](https://img.shields.io/badge/%E7%9C%8B%E4%BA%91%E6%96%87%E6%A1%A3-doc-green)](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc)  [![](https://img.shields.io/badge/QQ%E7%BE%A4-782930037-blue)](https://jq.qq.com/?_wv=1027&k=57ynqB1)  

<br>

# 项目地址

[Github](https://github.com/ForteScarlet/simple-robot-core-springboot-starter) or [Gitee](https://gitee.com/ForteScarlet/simple-robot-core-springboot-starter)

# 使用

首先，在使用springboot启动器之前，你需要使用springboot的 **properties** 格式配置文件：`application.properties`, 并将simple-robot中的配置项写入此类配置文件中。
<br>
其次，一般拥有springboot启动器的组件所处的核心系版本，都是从核心的`1.9.x`开始的。

##  组件启动器
我为一些特定的组件提供了其专属的启动器。
一般来讲，所有官方（即我）推出的启动器都只需要如上文所说的，
将配置文件更换为properties格式 ~~并将`@SpringbootApplication`注解替换为`@SimpleRobotSpringbootApplication`即可。~~

**从`1.9.1`开始，不再需要任何自定义注解。直接使用`@SpringbootApplication`启动即可。除非你要使用tomcat或者undertow之类的服务器来代替原本的内置http服务器，否则无需书写额外代码，直接编写监听器即可。**

<br>
以下我将展示已经提供了官方启动器的项目地址，以供参考。
<br>

### CQ HTTP 组件Springboot启动器
https://github.com/ForteScarlet/simple-robot-component-cqhttp-springboot-starter

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

~~然后参考注解类`@SimpleRobotSpringBootApplication`内容。~~
~~`@SimpleRobotSpringBootApplication`内默认继承了springboot的启动类注解`@SpringbootApplication`, 
并且其中会去默认扫描一个组件的自动配置包路径：~~
~~- `com.simplerobot.core.springboot.configuration`~~

`1.9.1`之后使用spring.factory进行自动配置，将不会为组件扫描特定的包路径了。

核心启动器需要某个组件启动器通过Spring向其**提供一些启动所需的必要对象**：
- `BaseApplication`的实现类
- 整合了springboot的beanFactory且标注了`@SimpleRobotApplication(resources = "/application.properties")`注解的App接口实例。

而核心会为组件启动器的构建所提供的bean有：
- `SpringBootDependGetter`对象，整合了Springboot的beanfactory的DependGetter。
- `SpringbootQQLogBack`对象，用来初始化QQLog的logback，使其与springboot的日志相结合。


# 使用
如果你使用的是我编写的标准版的快速启动器，~~你只需要将原本Springboot启动器上的`@SpringbootApplication`注解替换为`@SimpleRobotSpringBootApplication`即可。~~

`1.9,1`后无需变更注解，导入官方的快速启动器坐标后即可直接使用。（记得删除旧的config代码，防止依赖冲突。）

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

# 注意事项
## 1 启动器中的核心版本系与当前核心版本系
一般来讲，启动器所提供的核心版本系总是会比当前的核心版本系要低一个级别。例如，当前核心是`1.10.4`版本，即`1.10.x`系列，则最高版本的启动器一般会是`1.9.x`系列。

为什么呢？因为启动器一般来讲是需要保证其中所使用的核心为最新版本的，而当前所处的核心版本系中是无法保证最新版本的版本号的。

## 2 快速启动器中的springboot版本
截至到当前启动器版本为止，快速启动器中所包含的Springboot项目依赖的坐标与版本如下：
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>2.2.5.RELEASE</version>
        </dependency>
```
如有需要请自行覆盖并更替其版本。

## 3 快速启动器的日志整合
官方的组件快速启动器中，`QQLog`的日志输出已经被重定向到了Springboot所使用的`slf4j`日志中，其中日志等级的对应关系为：

| QQLog的日志等级 | slf4j的日志等级 |
| --------------- | --------------- |
| debug           | debug           |
| info            | info            |
| success         | info            |
| warning         | warn            |
| error           | error           |

整合后，日志所使用的`name`为`simple-robot-app + 随机颜表情`的名称。其中，`simple-robot-app`后面是有一个空格的。

如果想要关闭颜表情，你可以在项目启动**之前**调用`FaceUtil.close()` 

