# SpingBoot-shiro-mybatis
后端代码




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
