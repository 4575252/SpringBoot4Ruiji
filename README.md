# 应用SpringBoot技术实战瑞吉外卖项目

瑞吉项目的资料来源于网络，共10个章节，除了外卖商城必备的业务功能，还完成了前后端分离、部署、mysql、redis、nginx等部件应用，是不可多得的实战，因此在本项目中，使用git管理，将10章的内容测试完毕后按tags的方式分别存储，方便后期使用和观察

## 项目整体介绍
* 功能模块：登录退出、员工管理、菜品分类及套餐管理、菜品管理、套餐管理、订单明细
* 架构设计：
  * 原型设计：axure
  * 功能设计：xmind
  * 数据模型：powerdesigner（猜测，也可能直接在mysql上开发）
  * 前端开发：vsc+node.js+vue+elementui(大概率),mock,webpack
  * 后端开发：idea、postman
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
* 测试入口：
  * 前端：http://localhost:8180/front/page/login.html
  * 后端：http://localhost:8180/backend/page/login.html


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
* 套餐新增、删除、分页查询
* 实践脚本还有阿里云短信的代码，不过企业认证估计是不好做，暂缓
* 套餐的停售、修改还未涉及

技术方面主要有：
* 套餐新增，增加dto，需同时加载套餐分类
* 套餐中需要加载、上传图片
* 分页查询中，应用dto，同时加载套餐分类名称
* 删除需判断菜品是否起售

测试
* 新增套餐
* 查询套餐分页
* 删除套餐（起售无法删除，报提醒）

### 章节6

本章主要工作有：
* 前端用户功能的mvc代码编写、两个技术类导入，maven更新
* 前端用户端登录测试

技术方面主要有：
* 短信发送工具类，仅供参考，需要阿里云sdk的maven坐标导入！
* 验证码处理工具类，仅供参考，实际没有使用

测试
* 采用手机号登录，首次登录数据表自动新增用户，再次登录不新增
* 未登录直接访问前端index界面会被踢到登录页

### 章节7

本章主要工作有：
* 前端：用户地址簿管理、菜品展示、购物车、下单
* 缺失：前端访问用户页有404错误，购物车不能做减法只能清空
* 修改：因前端展示需要，修改后端dishcontroller、setmealcontroller、

技术方面主要有：
* 短信发送工具类，仅供参考，需要阿里云sdk的maven坐标导入！
* 验证码处理工具类，仅供参考，实际没有使用
* mybatisplus的IdWorker生成订单id，然后同时填充主表、明细表!

测试
* 采用手机号登录，首次登录数据表自动新增用户，再次登录不新增
* 未登录直接访问前端index界面会被踢到登录页
* 菜品添加到购物，购物车提交下单，如遇地址簿为空则需要新建地址簿并设为默认
* 地址簿可以提前设置，在前端左上角！



### 章节8
本章主要工作有：
* 环境搭建
* 缓存短信验证码
* 缓存菜品信息
* SpringCache
* 缓存套餐数据

技术方面主要有：
* 引入springDataRedis组件，含maven的popm坐标、redsconfig配置类、yml配置信息
* 修改usercontroller，对发送短信、登录验证做redis的存取、删除操作。
* 修改dishcontroller，对list查询做缓存判断和加载，对save、update做更新清理逻辑
* 应用SpringCache框架，简化redisTemplate
  * 支持redis、ehCache、guavaCache
  * pom增加spring-boot-starter-cache 坐标
  * yml增加cache配置，如缓存时间
  * @EnableCaching，SpringBoot入口开启，开启缓存注解功能
  * @Cacheable， 在Controller的方法上加入，进行缓存操作
  * @CachePut
  * @CacheEvict, 作用在controller类的save、update方法上，做缓存清空
  * 返回的R对象要实现序列化接口


测试
* 测试前端的菜品栏目两次读取有缓存的效果，采用redisTemplate Bean。
* 测试套餐数据的缓存效果，采用SpringCache注解

收获：
* redis的key中如果有两个冒号，则将冒号前的字符作为分组名称，从another redis desktop manager中观察所得


### 章节9

本章主要工作有：
* MySQL主从复制（环境搭建其他文章已整理，这里不赘述）
* 项目实现读写分离(重点)
* Nginx安装、反向代理和负载均衡配置，其他文章已整理，这里不赘述

技术方面主要有：
* 引入Sharding-JDBC组件，
  * 支持常见的基于jdbc的orm框架，如jpa、hibernate、mybatis、Spring jdbc Template
  * 支持第三方连接池，如dbcp，c3p0，druid等等
  * 支持遵循sql92的数据库，如mysql、oracle、sqlserver、pgsql
  * 配置：
    * 引入pom坐标
    * 修改yml配置，运行bean定义覆盖


测试
* 测试登录、用户启用或禁用，在控制台日志中的变化，有打印数据源名称


### 章节10
本章主要工作有：
* 应用yapi解决前后端分离开发
  * 接口定义yapi，前端vsc调试yapi的mock数据，后端用swagger进行调试，再联调集成，发布测试
* swagger框架集成与应用

技术方面主要有：
* swagger技术(实际采用knife4j框架)
  * pom坐标
  * webmvcconfig配置器中开启swagger2、knife4j2的标签，并配置DocketBean和站点项目信息
  * 注解的使用
    * 实体类，用@ApiModel("员工"),@ApiModelProperty("员工姓名"),标注实体类和属性上，doc就会提现中文字段
    * 控制器，用@Api(tags = "员工管理")，@ApiOperation("登录接口")，
      @ApiImplicitParams({
      @ApiImplicitParam(name = "page", value = "页码", required = true),
      @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true),
      @ApiImplicitParam(name = "name", value = "用户名", required = false)}
      )，分别作用与类、方法及参数
* 安装yapi
  * 环境要求，nodejs（7.6+)、mongodb（2.6+）、git

测试
* 安装swagger2，测试doc文档是否正常显示，实体类和属性、控制器、方法、参数注解是否正常显示
* 安装yapi，正常登录，创建项目，导入swagger的json文件，正常显示！

> nodejs + mongodb 开源 api 接口文档管理
> 官网仓库：https://github.com/YMFE/yapi
> 官方文档：https://hellosean1025.github.io/yapi
> 环境要求： nodejs（7.6+)、mongodb（2.6+）、git
> 这里我们使用 YAPI + idea plugin: EasyYapi 的组合.
```shell
# 一、安装nodejs,不同版本差异很大，尽量不用最新

wget https://nodejs.org/dist/v9.8.0/node-v9.8.0-linux-x64.tar.xz
xz -d node-v9.8.0-linux-x64.tar.xz 
tar -xvf node-v9.8.0-linux-x64.tar -C /usr/local/
ln -s /usr/local/node-v9.8.0-linux-x64/bin/node /usr/local/bin/node
ln -s /usr/local/node-v9.8.0-linux-x64/bin/npm /usr/local/bin/npm
bash
node -v
npm -v

# 二、安装git
cat << EOF > /etc/yum.repos.d/CentOS-Base.repo
[base]
name=CentOS-$releasever - Base - mirrors.aliyun.com
failovermethod=priority
baseurl=http://mirrors.aliyun.com/centos/7/os/$basearch/
EOF
yum install git -y

# 三、安装mongodb
cat << EOF > /etc/yum.repos.d/mongodb-3.4.repo
[mongodb-org-3.4]
name=MongoDB Repository
baseurl=https://repo.mongodb.org/yum/redhat/7/mongodb-org/3.4/x86_64/
gpgcheck=1
enabled=1
gpgkey=https://www.mongodb.org/static/pgp/server-3.4.asc
EOF

yum install -y mongodb-org

sed -i "s/127.0.0.1/0.0.0.0/" /etc/mongod.conf # 开启mongodb远程访问
systemctl enable mongod
systemctl start mongod

#卸载mongodb，备用
systemctl disable mongod # 停止开机自启
service mongod stop      # 停止服务
sudo yum erase $(rpm -qa | grep mongodb-org)   # 删除安装包
sudo rm -r /var/log/mongodb     # 删除日志文件
sudo rm -r /var/lib/mongo       # 删除数据文件

# 四、安装yapi
npm install -g yapi-cli --registry https://registry.npm.taobao.org
/usr/local/node-v9.8.0-linux-x64/bin/yapi server
# 访问9090网页，输入必要信息，版本选择1.10.2, 安装完毕后，访问3000端口，可先提前修改邮箱提醒

node /root/my-yapi/vendors/server/app.js 

nohup node /root/my-yapi/vendors/server/app.js  &> /root/yapi.log &
# 升级
cd  {项目目录}
yapi ls //查看版本号列表
yapi update //升级到最新版本
yapi update -v v1.1.0 //升级到指定版本


# nginx 本地转发 3000 到 80/443 绑定域名：
map $scheme $yapi_proxy_port {
    "http" "3000";
    "https" "3443";
    default "3000";
}
server
    {
        listen 80;
        server_name doc.abc.com;
        index index.html index.htm;

        location / {
            proxy_pass              $scheme://127.0.0.1:$yapi_proxy_port;
            proxy_redirect          ~^$scheme://127.0.0.1:$yapi_proxy_port(.*)    $scheme://$server_name$1;
            proxy_set_header        Host             $http_host;
            proxy_set_header        X-Real-IP        $host;
            proxy_set_header        X-Forwarded-For  $proxy_add_x_forwarded_for;
        }
    }
```


测试
* 测试登录、用户启用或禁用，在控制台日志中的变化，有打印数据源名称