# DockerDesktop 版本（win10）

## Elasticsearch 和 Kibana 版本关系

在使用 Kibana 时，需要注意 Kibana 的版本号必须和 ES 的版本相互对应，不然会出现 Kibana 和 ES 不兼容的问题，导致 Kibana
安装后不能使用。具体的版本对应问题，可以参考官网：[elasticsearch 和 Kibana 版本关系](https://www.elastic.co/cn/support/matrix#matrix_compatibility)

![elasticsearch 和 Kibana 版本关系](./assets/elasticsearch%20和%20Kibana%20版本关系.png)

## 下载 Elasticsearch

```shell
docker pull elasticsearch:7.17.9
```

![win10下的docker拉取elasticsearch镜像](./assets/win10下的docker拉取elasticsearch镜像.png)

## 下载 Kibana

```shell
docker pull kibana:7.17.9
```

![win10下的docker拉取kibana镜像](./assets/win10下的docker拉取kibana镜像.png)

## 启动 Elasticsearch 容器

### 创建挂载目录

![创建docker挂载目录](./assets/创建docker挂载目录.png)

config：用于放置配置相关的目录

data：用于放置产生的数据的目录

plugins：用于放置插件

### 新增 elasticsearch.yml 配置文件

在 config 目录下面新建 `elasticsearch.yml` 配置文件（这里的 `host: 0.0.0.0` 中间是有空格的。如果无，启动失败），文件内容如下：

```yaml
http.host: 0.0.0.0
```

### 单节点启动

```shell
# 创建 docker 网络
docker network create elastic

# 启动
docker run --name elasticsearch --net elastic -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e
ES_JAVA_OPTS="-Xms64m -Xmx128m" -v D:/DockerDesktop/docker-container-workspace/elastic-search/config/elasticsearch.yml:
/usr/share/elasticsearch/config/elasticsearch.yml -v D:/DockerDesktop/docker-container-workspace/elastic-search/data:
/usr/share/elasticsearch/data -v D:/DockerDesktop/docker-container-workspace/elastic-search/plugins:
/usr/share/elasticsearch/plugins -d elasticsearch:7.17.9
```

### 验证启动是否成功

在浏览器输入 [http://127.0.0.1:9200/](http://127.0.0.1:9200/)，出现如下图所示表示启动成功！

![验证elasticsearch启动是否成功](./assets/验证elasticsearch启动是否成功.png)

## 启动Kibana容器

### 启动容器

```shell
docker run --name kibana --net elastic -p 5601:5601 -e "ELASTICSEARCH_HOSTS=http://elasticsearch:9200" -d kibana:7.17.9
```

### 验证是否启动成功

在浏览器输入 [http://localhost:5601/](http://localhost:5601/)，出现如下图所示表示启动成功！

![验证kibana启动是否成功](./assets/验证kibana启动是否成功.png)

进入开发者工具页面：[http://localhost:5601/app/dev_tools#/console](http://localhost:5601/app/dev_tools#/console)

![kibana开发者工具页面](./assets/kibana开发者工具页面.png)

## 停止或者删除容器

```shell
# 停止容器
docker stop elasticsearch
docker stop kibana

# 删除网络配置和容器
docker network rm elastic
docker rm elasticsearch
docker rm kibana
```

---

# Docker命令行版本（CentOS 7）

由于和 win10 下面的 docker 命令差不多，这里不再过多赘述，只贴出命令汇总，如下所示：

```shell
# 拉取 7.17.9 版本的 elasticsearch
docker pull elasticsearch:7.17.9

# 拉取 7.17.9 版本的 kibana，注意这里的版本和 elasticsearch 一致
docker pull kibana:7.17.9

# 创建文件目录
mkdir -p /home/你的用户名/docker/elasticsearch/config
mkdir -p /home/你的用户名/docker/elasticsearch/data
mkdir -p /home/你的用户名/docker/elasticsearch/plugins

# 赋予文件权限，防止容器启动失败
chmod -R 777 /home/你的用户名/docker/elasticsearch/

# 新增内容到 elasticsearch.yml 配置文件（这里的 host: 0.0.0.0 中间是有空格的。如果无，启动失败）
echo "http.host: 0.0.0.0" >> /home/你的用户名/docker/elasticsearch/config/elasticsearch.yml

# 创建docker 网络
docker network create elastic

# 启动 elasticsearch 容器
docker run --name elasticsearch --net elastic -p 9200:9200 -p 9300:9300 \
-e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms64m -Xmx128m" \
-v /home/你的用户名/docker/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /home/你的用户名/docker/elasticsearch/data:/usr/share/elasticsearch/data \
-v /home/你的用户名/docker/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-d elasticsearch:7.17.9

# 启动 kibana 容器
docker run --name kibana --net elastic -p 5601:5601 -e "ELASTICSEARCH_HOSTS=http://elasticsearch:9200" -d kibana:7.17.9

# 停止容器
docker stop elasticsearch
docker stop kibana

# 删除网络配置和容器
docker network rm elastic
docker rm elasticsearch
docker rm kibana
```

# 参考

官方安装教程：[https://www.elastic.co/guide/en/kibana/7.17/docker.html](https://www.elastic.co/guide/en/kibana/7.17/docker.html)
