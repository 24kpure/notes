#### mysql部分小知识点

##### 1、日期格式化

date_format(col,'%Y')
%Y	年，4 位
%y	年，2 位
%m	月，数值(00-12)
%d	月的天，数值(00-31)
%H	小时 (00-23)
%i	分钟，数值(00-59)
%s	秒(00-59)

##### 2、截取字符串

左截取left(str, length)		从str左边开始截取长度为length的字符串，如：left('abcdefg',3)   结果：'abc'
右截取right(str, length)		从str右边开始截取长度为length的字符串，如：right('abcdefg',3)   结果：'efg'
substring(str, pos);			从str的第pos个位置开始截取，如：substring('abcdefg',3)   结果：'defg'
​				pos为负数，则是倒数，如：substring('abcdefg',-3) 结果: 'efg' 
substring(str, pos, len)		从str的第pos个位置开始截取长度为len的字符串，如：substring('abcdefg',3,2)   结果：'de'
​				pos为负数，则是倒数，如：substring('abcdefg',-3,2) 结果: 'ef' 
substring_index(str,delim,count)	str中第count次出现delim位置之前的字符串。如：substring_index('www.baidu.com','.',2) 结果：'www.baidu'
​				count为负数，则是倒数，如：substring_index('www.baidu.com','.',-2) 结果：'baidu.com'

##### 3、包含字符串

find_in_set('ab','aaaabbbb')		查找‘aaaabbbb’中‘ab’
locate(substr,str)			从str中查找substr字符所在的位置。如果包含，返回>0的数，否则返回0。如：locate('ab','aaaabbbb') 结果：4

##### 4、替换字符串

REPLACE (字段名,'原来的值','要修改的值')  

##### 5、连接字符串

concat(str1,str2,str3......)		连接所有字符串，如果有个值为null，则返回null。例：concat('11','22','33','44') 结果：11223344

case when expr1 then val else val1 end  		如果expr1为true，返回val，否则返回val1

##### 6、比较字符串

STRCMP(str1, str2)			比较两个字符串，如果这两个字符串相等返回0，如果第一个参数是根据当前的排序小于第二个参数顺序返回-1，否则返回1。

##### 7、取值在范围内

BETWEEN val1 and val2		用来代替组合“大于等于小于等于”条件。例：  t.col between 1 and 50   相当于  t.col>=1 and t.col<=50

##### 8、随机排序

rand()				生成一个0-1的值，用法   select * from t1 group by rand();

##### 9、dump命令

将h1服务器中的db1数据库的所有数据导入到h2中的db2数据库中，db2的数据库必须存在否则会报错120.26.204.123
JiNuoDBpwd
mysqldump --host=192.168.51.134 -uroot -prootpwd -C --databases test |mysql --host=120.26.204.123 -uroot -JiNuoDBpwd test 

##### 10、导出存储过程
其中，-d 表示--no-create-db, -n表示--no-data, -t表示--no-create-info, -R表示导出function和procedure。所以上述代码表示仅仅导出函数和存储过程，不导出表结构和数据,-C表示跨主机压缩数据。但是，这样导出的内容里，包含了trigger。再往mysql中导入时就会出问题，错误如下：

ERROR 1235 (42000) at line **: This version of MySQL doesn't yet support ‘multiple triggers with the same action time and event for one table’

所以在导出时需要把trigger关闭。代码为

 mysqldump -u 数据库用户名 -p -n -t -d -R --triggers=false 数据库名 > 文件名

========

##### 11、mysql日志清理

1.更改配置文件
vim /etc/my.conf
log-bin=mysql-bin
expire_logs_days = 10

2.数据库内部设置
set global expire_logs_days = 10;

3.手动清理
purge master logs before date_sub(current_date, interval 10 day);

========

##### 12.mysql for update 锁机制

http://blog.csdn.net/claram/article/details/54023216

============

##### 13.dump 问题记录

单个数据库可以重命名到远程库另外一个数据库名，但是不能加databases这个参数
mysqldump --host=192.168.1.238 -uroot -ppwd4RDS@2 -C  --opt  ssr_prod |mysql --host=192.168.1.34 -uroot -pmysql#JN99@dev  -C  ssr_test 

mysqldump --host=119.3.38.88  -uroot -pmysql#JN99@dev -C  --opt  ssr_test |mysql --host=192.168.51.134  -uroot -prootpwd  -C  ssr_test 