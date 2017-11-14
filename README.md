# sqltest
sql通用处理类

包括sql通用类的编写以及log4j的配置

# 数据库：

    test
    
#### 表：


    CREATE TABLE `student` (
      `pk` int(11) NOT NULL AUTO_INCREMENT,
      `name` varchar(255) NOT NULL,
      `age` int(11) NOT NULL DEFAULT '0',
      PRIMARY KEY (`pk`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
    
