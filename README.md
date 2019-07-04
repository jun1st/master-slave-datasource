# master-slave-datasource

<a href="https://mvnrepository.com/artifact/com.github.jun1st/master-slave-datasource-starter" target="_blank">
    <img src="https://maven-badges.herokuapp.com/maven-central/com.baomidou/dynamic-datasource-spring-boot-starter/badge.svg" >
</a>
<a href="http://www.apache.org/licenses/LICENSE-2.0.html" target="_blank">
    <img src="http://img.shields.io/:license-apache-brightgreen.svg" >
</a>
<a>
    <img src="https://img.shields.io/badge/JDK-1.8+-green.svg" >
</a>
<a>
    <img src="https://img.shields.io/badge/SpringBoot-_2.0+-green.svg" >
</a>

开箱即用的读写分离插件，在 Spring Boot里，通过使用 Spring 的 AutoConfigure 机制，自动注入 DataSource， 实现读写分析。

事务都走 master 数据库
单条 SQL 语句根据语句内容判断是走主数据库，还是只读数据库

```
<dependency>
    <groupId>com.github.jun1st</groupId>
    <artifactId>master-slave-datasource-starter</artifactId>
    <version>0.4.1</version>
</dependency>
```

## 配置数据库

```
  spring:
    master-slave: true
    data-source:
      master-slave:
      mybatis: true
      hikari:
        connection-init-sql: SET NAMES 'utf8mb4'
      master:
        url: jdbc:mysql://localhost:3306/database-master
        username: root
        password: p@ssw0rd
        driver-class-name: com.mysql.jdbc.Driver
      slave:
        one:
          url: jdbc:mysql://localhost:3306/database-slave1
          username: root
          password: p@ssw0rd
          driver-class-name: com.mysql.jdbc.Driver
        two:
          url: jdbc:mysql://localhost:3306/database-slave2
          username: root
          password: p@ssw0rd
          driver-class-name: com.mysql.jdbc.Driver
```
