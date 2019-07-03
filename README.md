# master-slave-datasource

MySQL 数据库读写分离

在 Spring Boot里，通过使用 Spring 的 AutoConfigure 机制，自动注入 DataSource， 实现读写分析。

事务都走 master 数据库

单条 SQL 语句根据语句内容判断是走主数据库，还是只读数据库
