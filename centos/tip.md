### Centos的一些小tip

##### 1.获取真实ip（希望不要过期）

curl members.3322.org/dyndns/getip

2.当前文件夹下的文件夹大小 

du -h --max-depth=1

##### 3.当前用户公钥地址 

~/.ssh/authorized_keys

##### 4.scp复制

scp -r localFloder root@targeIP:targetFolder 

###### 5.vim批量去bom 

:args d:\aa\**   
注意这里用** 表示循环文件夹 

:ar  
可以查看目前添加了那些目标文件 

:argdo set nobomb |update!  
###### 去除bom

:set ff=unix

##### 设置unix编码

参考：http://liliangjie.iteye.com/blog/978987

##### 6.挂载磁盘

df -h 查看磁盘挂载
umount /dev/vdb1  解除目标挂载
fdisk -l 查看盘符
fdisk  /dev/vdb1   目标盘
输入 d 并按回车键，删除原来的分区。
删除分区不会造成数据盘内数据的丢失。
输入 n 并按回车键，开始创建新的分区。
输入 p 并按回车键，选择创建主分区。因为创建的是一个单分区数据盘，所以只需要创建主分区。
如果要创建 4 个以上的分区，您应该创建至少一个扩展分区，即选择 e。
输入分区编号并按回车键。因为这里仅创建一个分区，所以输入 1。
输入第一个可用的扇区编号：为了保证数据的一致性，First sector 需要与原来的分区保持一致。在本示例中，按回车键采用默认值。
如果发现 First sector 显示的位置和之前记录的不一致，说明之前可能使用 parted 来分区，那么就停止当前的 fdisk 操作，使用 parted 重新操作。
输入w写入
输入最后一个扇区编号：因为这里仅创建一个分区，所以按回车键采用默认值
新挂载磁盘需要格式化 mkfs.ext4 /dev/xxxx

e2fsck -f /dev/vdb1 # 检查文件系统
resize2fs /dev/vdb1 # 变更文件系统大小

需要根据 /etc/fstab 中挂载格式来配置永久挂载

mkfs.ext4 
编辑/etc/fstab
查看磁盘uuid blkid
/dev/xvdb1  /opt/app ext4 defaults  0  0  写入文件挂载  // /dev/vdb1  /var/lib/mysql  ext4 defaults  0  0

##### 7.切分文件

-a : 指定后缀长度
-b : 每个文件多少字节
-d : 使用数字后缀而不是字母
-l : 指定每个文件的行数

split -a 2 -d -b 1000m /目标文件  spitparts

##### 8.更改repo

repo更改

cd /etc/yum.repos.d/ 
mv /etc/yum.repos.d/CentOS-Base.repo 
/etc/yum.repos.d/CentOS-Base.repo.backup

wget http://mirrors.163.com/.help/CentOS7-Base-163.repo

yum clean all 
yum makecache

##### 9.查找包含某些内容的文件 

grep dd-wx.wshuttle.com  ./ -r -n

##### 10.配置网卡 

vim /etc/sysconfig/network-scripts/网卡名
设置静态ip

TYPE=Ethernet
BOOTPROTO=static
DEFROUTE=yes
PEERDNS=yes
PEERROUTES=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_PEERDNS=yes
IPV6_PEERROUTES=yes
IPV6_FAILURE_FATAL=no
NAME=ens32
UUID=ae9d822a-2639-439f-93a0-812a3bc93120
DEVICE=ens32
ONBOOT=yes
IPADDR=192.168.51.135
PREFIX=24
NETMASK=255.255.255.0
GATEWAY=192.168.51.254
DNS1=218.85.157.99

重启网卡 systemctl restart network

##### 11.find高级查询 

find /opt/app/logs -mtime +3 -name "*.log"
找到3天前编辑的文件

find /opt/app/logs -mtime +3 -name "*.log" -exec rm -f {} \;
清除三天前的文件
/opt/tomcat/apache-tomcat-7.0.82/logs

##### 12.tar批量压缩
tar -czf jpg.tar.gz *.log

find /opt/app/logs -mtime +3 -name "*.log" -exec rm -f {} \;
find ./  -mtime -1 -name "*.log" -exec zip log.zip -u {} \;

##### 13.服务器抓包

服务器抓包
1.yum install -y tcpdump  安装
2.确定网卡和目标ip
目的地 139.196.234.118
本地ip 114.215.195.105
 tcpdump -n -i eth1 host 114.215.195.105 and 139.196.234.118

##### 14.bash切分字段

参考：https://blog.csdn.net/wuyinggui10000/article/details/52779364

##### 15.远程同步 rsync

rsync -e 'ssh -o StrictHostKeyChecking=no -q' -avc  localFile  targetIp:targetFile



