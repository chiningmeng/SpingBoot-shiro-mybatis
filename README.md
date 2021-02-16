# SpingBoot-shiro-mybatis

### 目前功能

#### 登录
1. 登录
2. 登出
3. 查询当前用户信息
   
#### 禁言
1. 设置禁言
2. 查看禁言列表（顺便更新禁言列表）


## application.yml
~~~
server:
  port: 8080
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver    
    url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
    username: root    
    #password: root
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: tk.mybatis.springboot.model
mapper:
  mappers:
    - tk.mybatis.springboot.util.MyMapper
  not-empty: false
  identity: MYSQL

swagger:
  enable: true

~~~
