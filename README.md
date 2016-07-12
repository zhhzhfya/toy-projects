### Toy-projects

1、系统首先加载toy-base，加入分布式集群

2、再次启动toy-base模块

3、再次启动toy-respontory

4、再次启动toy-dev

5、启动toy-web

![Alt text](https://github.com/zhhzhfya/toy-projects/blob/master/imgs/p1.jpg)

###  toy-base
基础服务：负责基础功能启动、分布式集群、发布管理界面的启动


###  toy-core

负责中心模块

###  toy-dev

负责开发管理的服务

###  toy-respontory

负责业务数据存储、查询的管理

###  toy-common

通用的maven的引用管理

###  toy-web

通用的web服务启动