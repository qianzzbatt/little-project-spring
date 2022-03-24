# little-project-spring

系统架构图：
![系统整体图](img.png)

数据库初始化文件在sql目录

相关资源在doc文件夹

分支
base-framework:项目环境初始化搭建，maven相关配置,logback日志整合
add-aop_config:添加了aop日志记录功能，全局异常处理,拦截器参数校验
add_jdbc: 添加jdbc连接数据库，并引入DruidDataSource优化原始Jdbc,使用jdbcTemplate减少代码冗余
add_mybatis: 整合orm框架，mybatis
add_springevent: 添加spring监听事件，用户登陆时异步发送优惠券
add_springcache: 缓存热点数据
