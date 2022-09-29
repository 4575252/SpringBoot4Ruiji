# 应用SpringBoot技术实战瑞吉外卖项目

瑞吉项目的资料来源于网络，共10个章节，除了外卖商城必备的业务功能，还完成了前后端分离、部署、mysql、redis、nginx等部件应用，是不可多得的实战，因此在本项目中，使用git管理，将10章的内容测试完毕后按tags的方式分别存储，方便后期使用和观察

## 项目整体介绍
* 功能模块：登录退出、员工管理、菜品分类及套餐管理、菜品管理、套餐管理、订单明细
* 架构设计：
  * 原型设计：axure
  * 功能设计：xmind
  * 数据模型：powerdesigner（猜测，也可能直接在mysql上开发）
  * 前端开发：vsc+node.js+vue+elementui(大概率)
  * 接口开发：yapi前后交互、swagger后端文档
  * 部署调试：finalshell、junit
* 技术组件：
  * 前端：H5、Vue.js、ElementUI
  * 网关：Nginx（静态资源缓存、反向代理、负载均衡）
  * 后端：SpringBoot、Spring、SpringMVC、SpringSession、lombok、Swagger
  * 持久：Mysql、MybatisPlus、Redis
  * 工具：git、maven、junit、Yapi
* 基础环境：6台，已提前准备，可参考其他文章
  * mysql主从分离，192.168.20.145/146，root/123456
  * redis，192.168.20.147,123456
  * nginx，192.168.20.141:80
  * tomcat双机,192.168.20.142/143:8080


## 10章节记录

### 章节1
项目目标：
* 完成项目创建
* 导入数据库
* 导入前端资源和【部分】后端资源
* 完成必要的后端功能代码，实现登录、退出功能，并通过测试
* 代码提交至github、并打上TAG DAY01

#### 1.1、创建工程、导入前端资源
* 通过idea创建springboot工程，复制前端资源到resources中
* 修改pom，导入坐标
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <scope>compile</scope>
</dependency>

<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.2</version>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.20</version>
</dependency>

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.76</version>
</dependency>

<dependency>
    <groupId>commons-lang</groupId>
    <artifactId>commons-lang</artifactId>
    <version>2.6</version>
</dependency>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.23</version>
</dependency>
```

* 导入配置文件application.yml
```YML
server:
  port: 8080
spring:
  application:
    name: reggie_take_out
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://192.168.20.145:3306/ruiji?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
      username: 192.168.20.145
      password: 123456
mybatis-plus:
  configuration:
    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: ASSIGN_ID
```

* 导入前端资源backend、front到resources目录
* 创建配置类WebMvcConfig，这样才能访问非默认的static、public、resources目录
```java
package com.iyyxx.springboot4ruiji.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @className: WebMvcConfig
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 14:35:53
 **/
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /*** 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}

```

* 访问测试
启动springboot，访问http://localhost:8180/backend/index.html   OK！

#### 1.2、mysql配置

**主节点**，配置同步库、同步账号【单机环境可跳过】
```sh
[root@mysql1 ~]# cat /etc/my.cnf       
[mysqld]
datadir=/var/lib/mysql
socket=/tmp/mysql.sock
symbolic-links=0
log-bin=mysql-bin   #[必须]启用二进制日志
server-id=145      #[必须]服务器唯一ID
binlog_do_db=test       #[可选]同步的数据库，支持多行
binlog_do_db=ruiji      #[可选]同步的数据库，支持多行

[mysqld_safe]
log-error=/var/log/mariadb/mariadb.log
pid-file=/var/run/mariadb/mariadb.pid
!includedir /etc/my.cnf.d

# 配置完成后，重启加载
service mysql restart

# 第三步：登录Mysql数据库，执行下面SQL， 增加同步账户
GRANT REPLICATION SLAVE ON *.* to 'xiaoming'@'%' identified by '123456';

# 第四步：登录Mysql数据库，执行下面SQL，记录下结果中File和Position的值
show master status;
```

**从库**，配置不同id、同步账号并启动同步【单机环境可跳过】
```sh
# 第一步：修改Mysql数据库的配置文件/etc/my.cnf
vim /etc/my.cnf
[mysqld]
server-id=146 #[必须]服务器唯一ID

# 第二步：重启Mysql服务
service mysqld restart

# 第三步：登录Mysql数据库，执行下面SQL， 尾部的logfile、position要根据实际填写！！！
change master to master_host='192.168.20.145',master_user='xiaoming',master_password='123456',master_log_file='mysql-bin.000001',master_log_pos=441;

start slave;
# 第四步：登录Mysql数据库，执行下面SQL，查看从数据库的状态
show slave status;
```

**导入数据库**
通过navicat，在主库创建ruiji数据库，注意字符集合排序规则，然后导入，再到从库验证，OK即可，相对简单不赘述


**特殊处理**
```shell
# 端口被占用， win10会保留一部分端口，下方指令可以查询
netsh interface ipv4 show excludedportrange protocol=tcp
```


#### 1.3、后端代码处理
**在config同级目录中，创建这里目录**
* entity，实体层
* mapper，dao层
* service，服务层
* service/impl，服务具体实现层
* controller，控制层
* common, 公共处理类

**导入实体表**
* entity.Employee，可以从资料包中拷贝，用到了lombok、mybatisplus注解
* common.R，从资料包中导入
* 手敲mapper、service、controller
```java
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}

public interface EmployeeService extends IService<Employee> {
}

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
}

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
  @Autowired
  private EmployeeService employeeService;

  /***
   * 员工登录
   * @param request
   * @param employee
   * @return
   * */
  @PostMapping("/login")
  public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
    //1、将页面提交的密码password进行md5加密处理
    String password = employee.getPassword();
    password = DigestUtils.md5DigestAsHex(password.getBytes());
    //2、根据页面提交的用户名username查询数据库
    LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Employee::getUsername, employee.getUsername());
    Employee emp = employeeService.getOne(queryWrapper);
    //3、如果没有查询到则返回登录失败结果
    if (emp == null) {
      return R.error("登录失败");
    }
    //4、密码比对，如果不一致则返回登录失败结果
    if (!emp.getPassword().equals(password)) {
      return R.error("登录失败");
    }
    //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
    if (emp.getStatus() == 0) {
      return R.error("账号已禁用");
    }
    //6、登录成功，将员工id存入Session并返回登录成功结果
    request.getSession().setAttribute("employee", emp.getId());
    return R.success(emp);
  }

  /***
   * 员工退出
   * @param request
   * @return
   */
  @PostMapping("/logout")
  public R<String> logout(HttpServletRequest request) {
    //清理Session中保存的当前登录员工的id
    request.getSession().removeAttribute("employee");
    return R.success("退出成功");
  }
}
```

* 登录、退出验证
访问 `http://localhost:8180/backend/page/login/login.html`，用默认admin，123456密码登录验证



### 章节2
本章主要工作有：
* 新增员工
* 员工分页查询
* 启用、禁用员工账号
* 员工信息编辑

技术方面主要有：
* 增加全局异常处理器
* 增加mybatisplus分页处理器
* 增加对象转换器,并重新webmvcconfig的方法，对传回前端的日期、大数字做转换
* 增加过滤器，对没有正常登录的用户踢回登录页

测试
* 新增员工测试
* 非法访问测试（过滤器）
* 禁用账号测试是否无法登陆
* 修改用户资料测试
* 分页查询


### 章节3

本章主要工作有：
* 分类管理模块开发（新增、删除、修改、分页查询）,菜品分类、套餐分类做一起，用类型做区分
* 菜品、套餐的实体类导入，配置mapper、service，为了分类删除时校验是否有关联！

技术方面主要有：
* 公共字段自动填充（创建修改的人员及时间）
  * 实体类的字段用@TableField并配置策略
  * 编写元数据对象处理器，实现MetaObjectHandler
  * 创建一个BaseContext工具类，用来存取用户id，应用ThreadLocal技术，在过滤器中获取，在无session的环境中提供，如上面的MetaObjectHandler
* 扩展自定义异常处理类
  * 抛出数据库异常，如重复性检查

测试
* 分类的添加、修改、分页测试
* 分类的删除测试（对存量的关联数据进行删除将报错！新增的分类可直接删除）


### 章节4

本章主要工作有：
* 文件上传下载
* 菜品新增、修改、分页查询
* 菜品口味表

技术方面主要有：
* 文件上传下载
  * 前端
    * 依赖表单的post提交方式
    * 表单的enctype为multipatt/form-data
    * 表单中添加file空间
    * 注：本次实验采用的elementui的upload组件,提供upload.html
  * 后端
    * 采用apache的commons-fileuplaoda和commons-io两个组件
    * spring的controller用MultipartFile参数接收即可！
* 菜品提交需要同时增加口味表
  * 因此增加dto类型
  * 还需要事务控制注解，毕竟同时操作两张表
  * springboot启动类还需要开启事务控制的注解
* 菜品page需要关联菜品分类获取分类名称，因此多了dto对象，扩展了service
* 菜品修改需要关联菜品口味信息，因此多了dto对象，扩展了service
* 菜品新增、修改需要分类信息，因此多了dto对象，扩展了service

测试
* 菜品页list
* 菜品新增
* 菜品修改
* demo页的上传测试


### 章节5

本章主要工作有：
* 文件上传下载
* 菜品新增、修改、删除、分页查询

技术方面主要有：
*

测试
*



### 章节6

本章主要工作有：
*

技术方面主要有：
*

测试
*

#### 2.1、

### 章节7

#### 2.1、

### 章节8

#### 2.1、

### 章节9

#### 2.1、

### 章节10

#### 2.1、

### 章节11

#### 2.1、