##### 仓库nexus

nexus2和nexus3都安装过，没遇到啥问题就不mark了

##### 常用命令

###### 编译跳过测试

mvn clean install -X -Denv=dev -Dmaven.test.skip=true

###### mvn添加本地包

mvn install:install-file -Dfile=C:\Users\jn\Documents\QRCode.jar -DgroupId=QRCode -DartifactId=QRCode -Dversion=3.0 -Dpackaging=jar 

###### mvn添加到服务器

mvn deploy:deploy-file -DgroupId=icepdf -DartifactId=icepdf-core -Dversion=6.25 -Dpackaging=jar -Dfile=F:\icefJar\test\icepdf-core.jar -Durl=http://192.168.51.253:8081/nexus/content/groups/public/ -DrepositoryId=thirdparty  

mvn 私服 3rd party

###### 默认管理员

admin admin123 可以添加包

###### 本地地址

mvn deploy:deploy-file -DgroupId=com.jn.ssr -DartifactId=tpr-api -Dversion=1.0.0  -Dpackaging=jar -Dfile=E:/tpr.jar -Durl=http://192.168.51.135:8999/repository/3rd-party/ -DrepositoryId=nexus3

DrepositoryId 本地配置中密码的id