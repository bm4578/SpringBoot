## 官方文档
[点此跳往](https://min.io/docs/minio/linux/developers/java/API.html)

## 配置说明
1. 安装minio。前提需要装好docker、docker-compose环境，请[参考](https://github.com/bm4578/minio.git) 。
   + 创建文件夹
      ```shell
      mkdir -p "minio/minio_data"
      cd minio
      ```
   + 赋予docker-compose可执行权限
      ```shell
      chown 1001 minio_data
      ```
   + 下载配置文件
      ```shell
      wget https://link.storjshare.io/s/jx6nbon5k7o447td2cmicercyvma/data/dev/docker/minio/docker-compose.yml?download=1 -O docker-compose.yml
      ```
   + 开始安装
      ```shell
      docker-compose up -d
      ```
   + 放通端口
      ```shell
      firewall-cmd --zone=public --add-port=9000-9001/tcp --permanent
      ```
   + 浏览器进入ip:9001
     ![](https://raw.githubusercontent.com/bm4578/images/master/202212291156159.png)

2. 拉取项目
    ```shell
    git clone https://github.com/bm4578/SpringBoot-minio.git
    ```
3. 进入本机安装好的minio后台，创建一个Bucket
   ![](https://raw.githubusercontent.com/bm4578/images/master/202212291415129.png)
4. 配置Bucket为公有访问权限
   ![](https://raw.githubusercontent.com/bm4578/images/master/202212291418487.png)
   ![](https://raw.githubusercontent.com/bm4578/images/master/202212291419297.png)
5. 配置application.yml
   ![](https://raw.githubusercontent.com/bm4578/images/master/202212291449463.png)
6. 后端项目展示
   ![](https://raw.githubusercontent.com/bm4578/images/master/202212291200953.png)
7. 前端项目展示，基于vue2 。项目地址 ，请[参考](https://github.com/bm4578/SpringBoot-minio-vue.git) 。
   ![](https://raw.githubusercontent.com/bm4578/images/master/202212291207966.png)

PS :若有帮助，希望您给我的GitHub项目点一个免费的Star。有问题一块交流bmt4578@gmail.com   
