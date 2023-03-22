# Elasticsearch 是什么？

你知道的，用于搜索(和分析)。

Elasticsearch 是分布式搜索和分析引擎，是 Elastic Stack 核心。Logstash 和 Beats 有助于收集、聚合和丰富数据，并将其存储在 Elasticsearch 中。Kibana
能够交互式地探索、可视化和分享对数据的见解，并管理和监视堆栈。Elasticsearch 是索引、搜索和分析发生的地方。

Elasticsearch 为所有类型的数据提供近乎实时的搜索和分析。无论是结构化还是非结构化文本、数字数据还是地理空间数据，Elasticsearch
都可以支持快速搜索的方式高效地存储和索引。可以超越简单的数据检索和聚合信息，发现数据中的趋势和模式。随着数据和查询量的增长，Elasticsearch 的分布式特性使部署能够无缝增长。

虽然不是每个问题都是搜索问题，但 Elasticsearch 提供了在各种用例中处理数据的速度和灵活性：

- 在应用程序或网站中添加搜索框
- 存储和分析日志、指标和安全事件数据
- 使用机器学习来实时自动模拟数据的行为
- 使用 Elasticsearch 作为存储引擎自动化业务工作流
- 使用 Elasticsearch 作为地理信息系统 (GIS) 管理、集成和分析空间信息
- 使用 Elasticsearch 作为生物信息学研究工具存储和处理遗传数据

我们不断被人们使用搜索的新奇方式所震惊。但是，无论你的用例是类似于这些用例之一，还是使用 Elasticsearch 来解决新问题，你在 Elasticsearch 中处理数据、文档和索引的方式都是相同的。

## 数据输入：文档和索引

Elasticsearch 是一个分布式文档存储。Elasticsearch 不是将信息存储为列数据行，而是存储已序列化为 JSON 文档的复杂数据结构。当集群中有多个 Elasticsearch
节点时，存储的文档分布在整个集群中，并且可以从任何节点立即访问。

当文档被存储时，它几乎实时地被索引并完全可搜索 —— 不到1秒。Elasticsearch 使用一种称为倒排索引的数据结构，该结构支持非常快速的全文搜索。倒排索引列出了出现在任何文档中的每个唯一单词，并标识了每个单词出现在其中的所有文档。

可以将索引视为文档的优化集合，每个文档都是字段的集合，这些字段是包含数据的键-值对。默认情况下，Elasticsearch
会索引每个字段中的所有数据，并且每个索引字段都有一个专用的、优化的数据结构。例如，文本字段存储在倒排索引中，数字和地理字段存储在 BKD 树中。Elasticsearch
之所以如此快速，是因为它能够使用每个字段的数据结构来组合和返回搜索结果。

Elasticsearch 还具有无模式的能力，这意味着可以对文档进行索引，而无需显式指定如何处理文档中可能出现的每个不同字段。启用动态映射后，Elasticsearch
会自动检测新字段并将其添加到索引中。这种默认行为使索引和浏览数据变得很容易 —— 只需开始索引文档，Elasticsearch 就会检测布尔值、浮点值和整数值、日期和字符串，并将它们映射到适当的 Elasticsearch 数据类型。

然而，归根结底，你比 Elasticsearch 更了解你的数据以及你想要如何使用它。你可以定义控制动态映射的规则，并显式定义映射以完全控制字段的存储和索引方式。

定义你自己的映射使你能够：

- 区分全文字符串字段和精确值字符串字段
- 执行特定语言的文本分析
- 优化字段以进行部分匹配
- 使用自定义日期格式
- 使用无法自动检测的数据类型，如 `geo_point` 和 `geo_shape`

为了不同的目的，以不同的方式对同一字段进行索引通常是有用的。例如，你可能希望将字符串字段索引为用于全文搜索的文本字段和用于排序或聚合数据的关键字字段。或者，你可以选择使用多个语言分析器来处理包含用户输入的字符串字段的内容。

在索引期间应用于全文字段的分析链也在搜索时使用。查询全文字段时，在索引中查找术语之前，查询文本会经过相同的分析。

## 信息输出：搜索和分析

虽然可以将 Elasticsearch 用作文档存储并检索文档及其元数据，但真正的力量来自于能够轻松访问基于 Apache Lucene 搜索引擎库构建的全套搜索功能。
Elasticsearch 提供了一个简单、连贯的 REST API，用于管理集群、索引和搜索数据。出于测试目的，可以轻松地直接从命令行或通过 Kibana
中的开发人员控制台提交请求。从你的应用程序中，可以使用 [Elasticsearch
客户端](https://www.elastic.co/guide/en/elasticsearch/client/index.html) 来选择语言：Java、JavaScript、Go、.NET、PHP、Perl、Python 或
Ruby。

### 搜索你的数据

Elasticsearch REST API 支持结构化查询、全文查询和将两者结合在一起的复杂查询。结构化查询与你可以在 SQL 中构造的查询类型类似。例如，你可以搜索员工索引中的性别和年龄字段，并根据 `hire_date`
字段对匹配项进行排序。全文查询查找与查询字符串匹配的所有文档，并按相关性排序返回它们与搜索词的匹配程度。

除了搜索单个术语外，还可以执行短语搜索、相似性搜索和前缀搜索，并获得自动完成建议。

是否有要搜索的地理空间或其他数字数据？Elasticsearch 在支持高性能地理和数字查询的优化数据结构中对非文本数据进行索引。

可以使用 Elasticsearch 全面的 JSON 风格查询语言（Query DSL）访问所有这些搜索功能。还可以构造 SQL 风格的查询，在 Elasticsearch 内部本地搜索和聚合数据，JDBC 和 ODBC
驱动程序使各种第三方应用程序能够通过 SQL 与 Elasticsearch 进行交互。

### 分析你的数据

Elasticsearch 聚合使你能够构建数据的复杂摘要，并深入了解关键指标、模式和趋势。聚合可以帮助你回答以下问题，而不是像谚语所说的 “大海捞针”：

- 干草堆里有多少针？
- 针头的平均长度是多少？
- 按制造商分类，针头的中位数长度是多少？
- 在过去的六个月里，每个月都有多少针被添加到干草堆中？

还可以使用聚合来回答更微妙的问题，例如：

- 你们最受欢迎的针头制造商是什么？
- 有没有异常或异常的针头？

因为聚合利用了用于搜索的相同数据结构，所以它们也非常快。这使你能够实时分析和可视化你的数据。你的报告和仪表板会随着数据的更改而更新，因此你可以根据最新信息采取行动。

更重要的是，聚合与搜索请求一起运行。可以在单个请求中对相同的数据同时搜索文档、筛选结果并执行分析。而且因为聚合是在特定搜索的上下文中计算的，所以你不仅要显示所有尺寸为 70 的针的计数，还要显示符合用户搜索条件的尺寸为 70 的针的计数
—— 例如，所有尺寸为 70 的不粘针。

### 更多

想要自动化时间序列数据的分析？你可以使用机器学习功能来创建数据中正常行为的准确基线，并识别异常模式。通过机器学习，你可以检测到：

- 与数值、计数或频率的时间偏差有关的异常
- 统计稀缺性
- 群体中的一员的不寻常行为

最棒的是什么？无需指定算法、模型或其他与数据科学相关的配置即可完成此操作。

## 可伸缩性和弹性：集群、节点和分片

Elasticsearch 旨在随时可用，并根据你的需求进行扩展。它通过自然分布来实现这一点。你可以将服务器（节点）添加到集群中以增加容量，Elasticsearch
会自动在所有可用节点上分配数据和查询负载。不需要彻底检查你的应用程序，Elasticsearch 知道如何平衡多节点集群以提供规模和高可用性。节点越多越好。

这是如何工作的呢？实际上，Elasticsearch 索引只是一个或多个物理碎片的逻辑分组，其中每个碎片实际上是一个自包含的索引。通过在索引中跨多个分片分布文档，并将这些分片分布到多个节点，Elasticsearch
可以确保冗余，这既可以防止硬件故障，也可以在节点添加到集群时增加查询容量。随着集群的增长（或缩小），Elasticsearch 自动迁移碎片以重新平衡集群。

有两种类型的碎片：主碎片和副本碎片。索引中的每个文档都属于一个主碎片。副本碎片是主碎片的副本。副本提供数据的冗余副本以防止硬件故障，并增加容量以满足读取请求，如搜索或检索文档。

索引中的主分片数量在创建索引时是固定的，但副本分片的数量可以随时更改，而不会中断索引或查询操作。

### 视情况而定

关于碎片大小和为索引配置的主碎片的数量，有许多性能考虑因素和权衡。碎片越多，维护这些索引的开销就越大。当 Elasticsearch 需要重新平衡集群时，碎片大小越大，移动碎片所需的时间就越长。

查询大量的小分片可以使每个分片的处理速度更快，但是更多的查询意味着更多的开销，因此查询少量的大分片可能更快。简而言之，这要看情况。

作为起点：

- 目标是将平均碎片大小保持在几 GB 到几十 GB 之间。对于具有基于时间的数据的用例，通常会看到 20 GB 到 40 GB 的碎片。
- 避免巨大的碎片问题。一个节点可以容纳的碎片数量与可用堆空间成正比。一般来说，每 GB 堆空间的碎片数量应该少于 20 个。

为你的用例确定最佳配置的最佳方法是[使用你自己的数据和查询进行测试](https://www.elastic.co/elasticon/conf/2016/sf/quantitative-cluster-sizing)。

### 容灾

集群的节点之间需要良好、可靠的连接。为了提供更好的连接，通常将节点放在同一个数据中心或附近的数据中心。但是，为了保持高可用性，还需要避免任何单点故障。在一个位置发生重大中断时，另一个位置的服务器需要能够接管。答案是跨集群复制（CCR）。

CCR 提供了一种将索引从主集群自动同步到可作为热备份的辅助远程集群的方法。如果主集群发生故障，备用集群可以接管。还可以使用 CCR 创建辅助集群，以便在地理位置接近用户的地方为读请求提供服务。

跨集群复制为主备模式。主集群上的索引是活动的前导索引，处理所有写请求。复制到辅助集群的索引是只读跟踪器。

### 重视

与任何企业系统一样，你需要工具来保护、管理和监控你的 Elasticsearch 集群。集成到 Elasticsearch
中的安全、监控和管理功能使你能够将 [Kibana](https://www.elastic.co/guide/en/kibana/7.17/introduction.html)
用作管理集群的控制中心。[数据汇总](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/xpack-rollup.html)
和[索引生命周期管理](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/index-lifecycle-management.html)
等功能可帮助你随着时间的推移智能管理数据。

# 7.17 版本新特性

Elasticsearch 7.17 是 7.17 Elastic Stack 的一个兼容性版本，没有主要的增强。

有关此版本的详细信息，请参阅[发布说明](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/es-release-notes.html)
和[迁移指南](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/breaking-changes.html)。

其他版本:

[7.16](https://www.elastic.co/guide/en/elasticsearch/reference/7.16/release-highlights.html) | [7.15](https://www.elastic.co/guide/en/elasticsearch/reference/7.15/release-highlights.html) | [7.14](https://www.elastic.co/guide/en/elasticsearch/reference/7.14/release-highlights.html) | [7.13](https://www.elastic.co/guide/en/elasticsearch/reference/7.13/release-highlights.html) | [7.12](https://www.elastic.co/guide/en/elasticsearch/reference/7.12/release-highlights.html) | [7.11](https://www.elastic.co/guide/en/elasticsearch/reference/7.11/release-highlights.html) | [7.10](https://www.elastic.co/guide/en/elasticsearch/reference/7.10/release-highlights.html) | [7.9](https://www.elastic.co/guide/en/elasticsearch/reference/7.9/release-highlights.html) | [7.8](https://www.elastic.co/guide/en/elasticsearch/reference/7.8/release-highlights.html) | [7.7](https://www.elastic.co/guide/en/elasticsearch/reference/7.7/release-highlights.html) | [7.6](https://www.elastic.co/guide/en/elasticsearch/reference/7.6/release-highlights-7.6.0.html) | [7.5](https://www.elastic.co/guide/en/elasticsearch/reference/7.5/release-highlights-7.5.0.html) | [7.4](https://www.elastic.co/guide/en/elasticsearch/reference/7.4/release-highlights-7.4.0.html) | [7.3](https://www.elastic.co/guide/en/elasticsearch/reference/7.3/release-highlights-7.3.0.html) | [7.2](https://www.elastic.co/guide/en/elasticsearch/reference/7.2/release-highlights-7.2.0.html) | [7.1](https://www.elastic.co/guide/en/elasticsearch/reference/7.1/release-highlights-7.1.0.html) | [7.0](https://www.elastic.co/guide/en/elasticsearch/reference/7.0/release-highlights-7.0.0.html)

# 快速开始

本指南帮助初学者学习如何：

- 在测试环境中安装和运行 Elasticsearch
- 向 Elasticsearch 添加数据
- 搜索和排序数据
- 在搜索过程中从非结构化内容中提取字段

## 运行 Elasticsearch

设置 Elasticsearch 最简单的方法是在 Elastic Cloud 上使用 Elasticsearch Service 创建一个托管部署。如果你喜欢管理自己的测试环境，可以使用 Docker 安装和运行
Elasticsearch。

自行管理

> 安装并运行 Elasticsearch
> 1. 安装并启动 [Docker Desktop](https://www.docker.com/products/docker-desktop)
> 2. 运行
> ```shell
> docker network create elastic
> docker pull docker.elastic.co/elasticsearch/elasticsearch:7.17.9
> docker run --name es01-test --net elastic -p 127.0.0.1:9200:9200 -p 127.0.0.1:9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.17.9
> ```
> 3. 安装并运行 Kibana
>
> 要使用直观的 UI 分析、可视化和管理 Elasticsearch 数据，请安装 Kibana。
>
> ① 在新的终端会话中，运行：
> ```shell
> docker pull docker.elastic.co/kibana/kibana:7.17.9
> docker run --name kib01-test --net elastic -p 127.0.0.1:5601:5601 -e "ELASTICSEARCH_HOSTS=http://es01-test:9200" docker.elastic.co/kibana/kibana:7.17.9
> ```
> ② 访问 Kibana，请访问 [http://localhost:5601](http://localhost:5601)
>

## 向 Elasticsearch 发送请求

可以使用 REST api 向 Elasticsearch 发送数据和其他请求。这让你可以使用任何发送 HTTP 请求的客户端（如 curl）与 Elasticsearch 交互。你还可以使用 Kibana 的控制台向
Elasticsearch 发送请求。

自行管理

> 使用 Kibana
> 1. 打开 Kibana 的主菜单，进入 **Dev Tools > Console**。
     > ![https://www.elastic.co/guide/en/elasticsearch/reference/7.17/images/kibana-console.png](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/images/kibana-console.png)
> 2. 在控制台中运行以下API请求示例：
> ```shell
> GET /
> ```
> 使用 curl
> 要提交示例 API 请求，在新的终端会话中运行以下 curl 命令。
> ```shell
> curl -X GET http://localhost:9200/
> ```

## 新增数据

将数据作为 JSON 对象添加到 Elasticsearch 中，称为文档。Elasticsearch 将这些文档存储在可搜索索引中。

对于时间序列数据，例如日志和指标，通常将文档添加到由多个自动生成的备份索引组成的数据流中。

数据流需要一个与其名称匹配的索引模板。Elasticsearch 使用这个模板来配置流的支持索引。发送到数据流的文档必须有 `@timestamp` 字段。

### 添加单个文档

提交以下索引请求，向 `logs-my_app-default` 数据流添加一个日志条目。由于 `logs-my_app-default` 不存在，请求会使用内置的 `logs-*-*` 索引模板自动创建它。

```shell
POST logs-my_app-default/_doc
{
  "@timestamp": "2099-05-06T16:21:15.000Z",
  "event": {
    "original": "192.0.2.42 - - [06/May/2099:16:21:15 +0000] \"GET /images/bg.jpg HTTP/1.0\" 200 24736"
  }
}
```

响应包括 Elasticsearch 为文档生成的元数据：

- 包含文档的备份 `_index`。Elasticsearch 自动生成支持索引的名称。
- 索引中文档的唯一 `_id`。

```json
{
  "_index": ".ds-logs-my_app-default-2023.03.18-000001",
  "_type": "_doc",
  "_id": "S7qa84YBbeqs3cRlbdCc",
  "_version": 1,
  "result": "created",
  "_shards": {
    "total": 2,
    "successful": 1,
    "failed": 0
  },
  "_seq_no": 0,
  "_primary_term": 1
}
```

### 添加多个文档

使用 _bulk 端点在一个请求中添加多个文档。批量数据必须是换行分隔的 JSON (NDJSON)。每一行必须以换行符（\n）结束，包括最后一行。

```shell
PUT logs-my_app-default/_bulk
{ "create": { } }
{ "@timestamp": "2099-05-07T16:24:32.000Z", "event": { "original": "192.0.2.242 - - [07/May/2020:16:24:32 -0500] \"GET /images/hm_nbg.jpg HTTP/1.0\" 304 0" } }
{ "create": { } }
{ "@timestamp": "2099-05-08T16:25:42.000Z", "event": { "original": "192.0.2.255 - - [08/May/2099:16:25:42 +0000] \"GET /favicon.ico HTTP/1.0\" 200 3638" } }
```

## 搜索数据

索引文档几乎可以实时搜索。下面的搜索将匹配 `logs-my_app-default` 中的所有日志条目，并按照 `@timestamp` 降序对它们进行排序。

```shell
GET logs-my_app-default/_search
{
  "query": {
    "match_all": { }
  },
  "sort": [
    {
      "@timestamp": "desc"
    }
  ]
}
```

默认情况下，响应的 `hits` 部分最多包含与搜索匹配的前 10 个文档。每个命中的 `_source` 包含索引期间提交的原始 JSON 对象。

```json
{
  "took": 2,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 3,
      "relation": "eq"
    },
    "max_score": null,
    "hits": [
      {
        "_index": ".ds-logs-my_app-default-2099-05-06-000001",
        "_type": "_doc",
        "_id": "PdjWongB9KPnaVm2IyaL",
        "_score": null,
        "_source": {
          "@timestamp": "2099-05-08T16:25:42.000Z",
          "event": {
            "original": "192.0.2.255 - - [08/May/2099:16:25:42 +0000] \"GET /favicon.ico HTTP/1.0\" 200 3638"
          }
        },
        "sort": [
          4081940742000
        ]
      },
      ...
    ]
  }
}
```

### 获取特定字段

对于大型文档，解析整个 `_source` 非常麻烦。为了从响应中排除它，将 `_source` 参数设置为 `false`。相反，使用 `fields` 参数来检索所需的字段。

```shell
GET logs-my_app-default/_search
{
  "query": {
    "match_all": { }
  },
  "fields": [
    "@timestamp"
  ],
  "_source": false,
  "sort": [
    {
      "@timestamp": "desc"
    }
  ]
}
```

响应以平面数组的形式包含每个命中的字段值。

```json
{
  ...
  "hits": {
    ...
    "hits": [
      {
        "_index": ".ds-logs-my_app-default-2099-05-06-000001",
        "_type": "_doc",
        "_id": "PdjWongB9KPnaVm2IyaL",
        "_score": null,
        "fields": {
          "@timestamp": [
            "2099-05-08T16:25:42.000Z"
          ]
        },
        "sort": [
          4081940742000
        ]
      },
      ...
    ]
  }
}
```

### 搜索日期范围

若要跨特定时间或 IP 范围进行搜索，请使用 `range` 查询。

```shell
GET logs-my_app-default/_search
{
  "query": {
    "range": {
      "@timestamp": {
        "gte": "2099-05-05",
        "lt": "2099-05-08"
      }
    }
  },
  "fields": [
    "@timestamp"
  ],
  "_source": false,
  "sort": [
    {
      "@timestamp": "desc"
    }
  ]
}
```

你可以使用日期数学来定义相对时间范围。下面的查询搜索过去一天的数据，它不会匹配 `logs-my_app-default` 中的任何日志条目。

```shell
GET logs-my_app-default/_search
{
  "query": {
    "range": {
      "@timestamp": {
        "gte": "now-1d/d",
        "lt": "now/d"
      }
    }
  },
  "fields": [
    "@timestamp"
  ],
  "_source": false,
  "sort": [
    {
      "@timestamp": "desc"
    }
  ]
}
```

### 从非结构化内容中提取字段

在搜索过程中，可以从非结构化内容（如日志消息）中提取[运行时字段](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/runtime-search-request.html)
。

使用以下搜索从 `event.original` 中提取 `source.ip` 运行时字段。若要将其包含在响应中，请将 `source.ip` 添加到 `fields` 参数中。

```shell
GET logs-my_app-default/_search
{
  "runtime_mappings": {
    "source.ip": {
      "type": "ip",
      "script": """
        String sourceip=grok('%{IPORHOST:sourceip} .*').extract(doc[ "event.original" ].value)?.sourceip;
        if (sourceip != null) emit(sourceip);
      """
    }
  },
  "query": {
    "range": {
      "@timestamp": {
        "gte": "2099-05-05",
        "lt": "2099-05-08"
      }
    }
  },
  "fields": [
    "@timestamp",
    "source.ip"
  ],
  "_source": false,
  "sort": [
    {
      "@timestamp": "desc"
    }
  ]
}
```

### 合并查询

你可以使用 `bool` 查询来组合多个查询。以下搜索组合了两个 `range` 查询：一个在 `@timestamp` 上，另一个在 `source.ip` 运行时字段上。

```shell
GET logs-my_app-default/_search
{
  "runtime_mappings": {
    "source.ip": {
      "type": "ip",
      "script": """
        String sourceip=grok('%{IPORHOST:sourceip} .*').extract(doc[ "event.original" ].value)?.sourceip;
        if (sourceip != null) emit(sourceip);
      """
    }
  },
  "query": {
    "bool": {
      "filter": [
        {
          "range": {
            "@timestamp": {
              "gte": "2099-05-05",
              "lt": "2099-05-08"
            }
          }
        },
        {
          "range": {
            "source.ip": {
              "gte": "192.0.2.0",
              "lte": "192.0.2.240"
            }
          }
        }
      ]
    }
  },
  "fields": [
    "@timestamp",
    "source.ip"
  ],
  "_source": false,
  "sort": [
    {
      "@timestamp": "desc"
    }
  ]
}
```

### 聚合数据

使用聚合将数据总结为指标、统计数据或其他分析数据。

下面的搜索使用一个聚合，使用 `http.response.body.bytes` 运行时字段计算 `average_response_size`。聚合只在匹配查询的文档上运行。

```shell
GET logs-my_app-default/_search
{
  "runtime_mappings": {
    "http.response.body.bytes": {
      "type": "long",
      "script": """
        String bytes=grok('%{COMMONAPACHELOG}').extract(doc[ "event.original" ].value)?.bytes;
        if (bytes != null) emit(Integer.parseInt(bytes));
      """
    }
  },
  "aggs": {
    "average_response_size":{
      "avg": {
        "field": "http.response.body.bytes"
      }
    }
  },
  "query": {
    "bool": {
      "filter": [
        {
          "range": {
            "@timestamp": {
              "gte": "2099-05-05",
              "lt": "2099-05-08"
            }
          }
        }
      ]
    }
  },
  "fields": [
    "@timestamp",
    "http.response.body.bytes"
  ],
  "_source": false,
  "sort": [
    {
      "@timestamp": "desc"
    }
  ]
}
```

响应的 `aggregations` 对象包含聚合结果。

```json
{
  ...
  "aggregations": {
    "average_response_size": {
      "value": 24736.0
    }
  }
}
```

### 浏览更多搜索选项

要继续探索，请将更多数据索引到数据流中，并查看[常用搜索选项](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/search-your-data.html#common-search-options)
。

## 清空

完成后，删除测试数据流及其支持索引。

```shell
DELETE _data_stream/logs-my_app-default
```

你还可以删除测试部署。

自我管理

> 要停止 Elasticsearch 和 Kibana Docker 容器，请运行：
> ```shell
> docker stop es01-test
> docker stop kib01-test
> ```
> 要删除容器及其网络，请运行：
> ```shell
> docker network rm elastic
> docker rm es01-test
> docker rm kib01-test
> ```

## 下一章

- 通过设置数据层和
  ILM，最大限度地利用你的时间序列数据。请参见[使用 Elasticsearch 搜索时间序列数据](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/use-elasticsearch-for-time-series-data.html)
  。
- 使用 Fleet 和 Elastic Agent 直接从数据源收集日志和指标，并将它们发送到
  Elasticsearch。请参阅使用 [Elastic Agent 的摄取日志、指标和正常运行时间数据](https://www.elastic.co/guide/en/observability/7.17/ingest-logs-metrics-uptime.html)
  。
- 使用 Kibana 来探索、可视化和管理 Elasticsearch 数据。参见 [Kibana 快速入门指南](https://www.elastic.co/guide/en/kibana/7.17/get-started.html)
  。

# 设置 Elasticsearch

本节包括关于如何设置 Elasticsearch 并让它运行的信息，包括：

- 下载
- 安装
- 启动
- 配置

**支持的平台**

官方支持的操作系统和 JVM 列表可在此处获得：[支持列表](https://www.elastic.co/support/matrix)。Elasticsearch 在列出的平台上进行了测试，但它也可能在其他平台上运行。

**Java(JVM) 版本**

Elasticsearch 是使用 Java 构建的，并且在每个发行版中都包含了 JDK 维护者提供的 [OpenJDK](https://openjdk.java.net/) 的捆绑版本(GPLv2+CE)。捆绑的 JVM 是推荐的
JVM，位于 Elasticsearch 主目录的 jdk 目录中。

要使用你自己的 Java 版本，请设置 ES_JAVA_HOME 环境变量。如果你必须使用与捆绑的 JVM 不同的 Java
版本，我们建议使用受支持的 [LTS Java 版本](https://www.oracle.com/technetwork/java/eol-135779.html)。如果使用了已知的坏 Java 版本，Elasticsearch
将拒绝启动。使用你自己的 JVM 时，绑定的 JVM 目录可能会被删除。

**使用专用主机**

在生产环境中，我们建议你在专用主机或主服务上运行 Elasticsearch。一些 Elasticsearch 特性，比如自动 JVM 堆大小调整，假设它是主机或容器上唯一的资源密集型应用程序。例如，你可以同时运行 Metricbeat 和
Elasticsearch 来获取集群统计信息，但是资源密集型的 Logstash 部署应该在它自己的主机上。

## 安装 Elasticsearch

### 托管 Elasticsearch

你可以在自己的硬件上运行 Elasticsearch，也可以使用我们在 AWS、GCP 和 Azure 上提供的托管 Elasticsearch
服务。[免费试用 Elasticsearch 服务](https://www.elastic.co/cloud/elasticsearch-service/signup?baymax=docs-body&elektra=docs)。

### 自行安装 Elasticsearch

Elasticsearch 提供以下包格式：

| 平台                      | 描述                                                                                           |
|-------------------------|----------------------------------------------------------------------------------------------|
| Linux和MacOS `tar.gz` 存档 | `tar.gz` 存档文件可用于安装在任何 Linux 发行版和 MacOS 上。                                                    |
| Windows `.zip` 存档       | `zip` 归档文件适合安装在 Windows 上。                                                                   |
| `deb`                   | `deb` 包适用于 Debian、Ubuntu 和其他基于 Debian 的系统。Debian 软件包可以从 Elasticsearch 网站或我们的 Debian 存储库下载。   |
| `rpm`                   | `rpm` 包适用于 Red Hat、Centos、SLES、OpenSuSE 等基于 rpm 的系统。RPM 可以从 Elasticsearch 网站或从我们的 RPM 存储库下载。 |
| `docker`                | 镜像可以作为 Docker 容器运行 Elasticsearch。它们可以从 Elastic Docker Registry 下载。                           |
| `brew`                  | 惯例可从 Elastic Homebrew TAP 获得，用于在带有 Homebrew 程序包管理器的 MacOS 上安装 Elasticearch。                  |

#### 在 Linux 或者 MacOS 上安装 Elasticsearch

Elasticsearch是Linux和MacOS的.tar.gz归档文件。

这个包包含免费和订阅功能。开始一个30天的试验，尝试所有的功能。

Elasticsearch 的最新稳定版本可以在 [Elasticsearch 下载](https://www.elastic.co/downloads/elasticsearch)
页面上找到。其他版本可以在[历史版本页面](https://www.elastic.co/downloads/past-releases)上找到。

> **提示**
>
> Elasticsearch 包含 JDK 维护者（GPLv2+CE）提供的 [OpenJDK](https://openjdk.java.net/) 捆绑版本。要使用自己的 Java
> 版本，查阅 [JVM 版本要求](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/setup.html#jvm-version)。

##### 为 Linux 下载和安装压缩包

Elasticsearch v 7.17.9 的 Linux 压缩包，可以按以下操作进行下载和安装：

```shell
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.9-linux-x86_64.tar.gz
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.9-linux-x86_64.tar.gz.sha512
shasum -a 512 -c elasticsearch-7.17.9-linux-x86_64.tar.gz.sha512  ①
tar -xzf elasticsearch-7.17.9-linux-x86_64.tar.gz
cd elasticsearch-7.17.9/  ②
```

① 比较下载的 `.tar.gz` SHA 值和公开的校验值。正常应该输出 `elasticsearch-{version}-linux-x86_64.tar.gz: OK`。

② 此目录一般也是环境变量里的 `$ES_HOME`。

##### 为 MacOS 下载和安装压缩包

Elasticsearch v 7.17.9 的 MacOS 压缩包，可以按以下操作进行下载和安装：

```shell
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.9-darwin-x86_64.tar.gz
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.9-darwin-x86_64.tar.gz.sha512
shasum -a 512 -c elasticsearch-7.17.9-darwin-x86_64.tar.gz.sha512  ①
tar -xzf elasticsearch-7.17.9-darwin-x86_64.tar.gz
cd elasticsearch-7.17.9/   ②
```

① 比较下载的 `.tar.gz` SHA 值和公开的校验值。正常应该输出 `elasticsearch-{version}-linux-x86_64.tar.gz: OK`。

② 此目录一般也是环境变量里的 `$ES_HOME`。

##### 启用系统索引自动创建

一些商业特性会在 Elasticsearch 中自动创建索引。默认情况下， Elasticsearch 配置为允许自动创建索引而不需要额外的步骤。然而，如果你在 Elasticsearch
中禁用了自动索引创建，则必须在 `elasticsearch.yml`
中配置 [`action.auto_create_index`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docs-index_.html#index-creation)
以允许商业特性创建以下索引：

```yaml
action.auto_create_index: .monitoring*,.watches,.triggered_watches,.watcher-history*,.ml*
```

> **警告**
>
> 如果你在使用 [Logstash](https://www.elastic.co/products/logstash) 或 [Beats](https://www.elastic.co/products/beats)
> ，那么你很可能需要在你的 `action.auto_create_index` 设置中使用额外的索引名字，具体的值取决于你的本地配置。如果你不确定你环境的正确值，可以考虑设置这个值为 `*` 以允许自动创建所有索引。

##### 从命令行运行 Elasticsearch

Elasticsearch 可以如以下从命令行启动：

```shell
./bin/elasticsearch
```

如果你对 Elasticsearch
密钥库进行了密码保护，会提示输入密码库密码。[查看安全设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/secure-settings.html)获取更多详情。

默认情况下，Elasticsearch 将其日志打印到控制台(`stdout`)
和 [logs 目录](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#path-settings)
中的 `<cluster name>.log` 文件。Elasticsearch 在启动时记录一些信息，但一旦完成初始化，它将继续在前台运行，直到发生值得记录的事情为止，它不会继续记录任何信息。当 Elasticsearch
运行时，你可以通过它的 HTTP 接口与它进行交互，默认端口为 9200。要停止 Elasticsearch，请按 `Ctrl-C`。

> **注意**
>
> 所有与 Elasticsearch 关联的脚本，需要一个支持数组版本的 Bash，且 Bash 要放在 `/bin/bash` 中。因此，Bash 需要放在或者通过符号链接到这个路径中。

##### 检查 Elasticsearch 是否正在运行

你可以通过向 `localhost` 的 `9200` 端口发送 HTTP 请求来测试 Elasticsearch 节点是否正在运行：

```shell
GET /
```

这会给你这样的响应：

```json
{
  "name": "Cp8oag6",
  "cluster_name": "elasticsearch",
  "cluster_uuid": "AT69_T_DTp-1qgIJlatQqA",
  "version": {
    "number": "7.17.9",
    "build_flavor": "default",
    "build_type": "tar",
    "build_hash": "f27399d",
    "build_date": "2016-03-30T09:51:41.449Z",
    "build_snapshot": false,
    "lucene_version": "8.11.1",
    "minimum_wire_compatibility_version": "1.2.3",
    "minimum_index_compatibility_version": "1.2.3"
  },
  "tagline": "You Know, for Search"
}
```

可以在命令行中通过 `-q` 或 `--quiet` 选项禁用日志输出到标准输出（`stdout`）。

##### 作为守护进程运行

要作为守护进程运行 Elasticsearch，在命令行指定 `-d`，并使用 `-p` 选项在文件中记录进程 ID：

```shell
./bin/elasticsearch -d -p pid
```

如果你对 Elasticsearch
密钥库进行了密码保护，会提示输入密码库密码。[查看安全设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/secure-settings.html)获取更多详情。

日志信息可以在 `$ES_HOME/logs/` 目录中找到。

要关闭 Elasticsearch，杀死记录在 pid 文件中的 进程 ID：

```shell
pkill -F pid
```

> **注意**
>
> Elasticsearch `.tar.gz` 包不包含 `systemd` 模块。要把 Elasticsearch
> 作为服务，改用 [Debian](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/starting-elasticsearch.html#start-deb)
> 或者 [RPM](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/starting-elasticsearch.html#start-rpm) 包。

##### 在命令行配置 Elasticsearch

Elasticsearch 默认从 `$ES_HOME/configuring_elasticsearch/elasticsearch.yml`
加载配置。配置文件的格式在[配置 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html) 中解释。

在配置文件中可以指定的设置，也能在命令行中指定，如下使用 `-E` 语法：

```shell
./bin/elasticsearch -d -Ecluster.name=my_cluster -Enode.name=node_1
```

> **提示**
>
> 通常，任意集群范围设置（如 `cluster.name`）应该添加到 `elasticsearch.yml` 配置文件中，而任何节点特定设置，如 `node.name`，应该在命令行中指定。

##### 压缩包文件目录结构

压缩包发行版是完全独立的。默认情况下，所有文件和目录都包含在 `$ES_HOME` —— 解压压缩包时创建的目录。

这样很方便，因为你不必创建任意目录去启用 Elasticsearch，且卸载 Elasticsearch 就像移除 `$ES_HOME`
目录一样简单。但是，建议修改默认的配置目录（config）和数据目录（data）以便以后不用删除重要数据。

| 类型      | 描述                                                          | 默认位置               | 设置                                                                                                               |
|---------|-------------------------------------------------------------|--------------------|------------------------------------------------------------------------------------------------------------------|
| home    | Elasticsearch 主目录或 `$ES_HOME`                               | 解压压缩包创建的目录         ||
| bin     | 二进制脚本，包括启动节点的 `elasticsearch` 和安装插件的 `elasticsearch-plugin` | `$ES_HOME/bin`     ||
| conf    | 配置文件，包括 `elasticsearch.yml`                                 | `$ES_HOME/config`  | [ES_PATH_CONF](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#config-files-location) |
| data    | 分配在节点上的每个索引和分片的数据文件位置。可以有多个位置。                              | `$ES_HOME/data`    | `path.data`                                                                                                      |
| logs    | 日志文件位置                                                      | `$ES_HOME/logs`    | `path.logs`                                                                                                      |
| plugins | 插件文件位置。每个插件会包含在一个子目录中。                                      | `$ES_HOME/plugins` ||
| repo    | 共享文件系统仓库位置。可以有多个位置。文件系统仓库可以放在此处指定的任何目录的任何子目录中。              | 未配置                | `path.repo`                                                                                                      |

##### 下一步

你现在有一个测试 Elasticsearch 环境部署好。在你使用 Elasticsearch 正式开始开发或者生产之前，你必须做一些额外的设置：

- 学习如何[配置 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html)。
- 配置[重要的 Elasticsearch 设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html)。
- 配置[重要的系统设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/system-config.html)。

#### 在 Windows 上用 `.zip` 安装 Elasticsearch

Elasticsearch 可以使用 Windows `.zip` 存档安装到 Windows 上。可以使用 `elasticsearch-service.bat` 安装 Elasticsearch 作为服务运行。

这个包包含免费和订阅的特性。[开始 30 天的试用](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/license-settings.html)，尝试所有功能。

> **注意**
>
> 在 Windows 上，Elasticsearch 机器学习特性需要 Microsoft 通用 C 运行时库。它内置于 Windows 10、Windows Server 2016 和更高版本的 Windows 中。对于老版本的
> Windows，它可以通过 Windows Update
> 安装，也可以从[独立下载](https://support.microsoft.com/en-us/help/2999226/update-for-universal-c-runtime-in-windows)安装。如果你不能安装
> Microsoft 通用 C 运行时库，禁用机器学习特性你也能使用 Elasticsearch 的其他特性。

Elasticsearch 的最新稳定版本，能在 [Elasticsearch 下载页面](https://www.elastic.co/downloads/elasticsearch)
找到。其他版本能在[历史发布页面](https://www.elastic.co/downloads/past-releases)找到。

> **注意**
>
> Elasticsearch 包含 JDK 维护者（GPLv2+CE）提供的 [OpenJDK](https://openjdk.java.net/)捆绑版本。要使用自己的 Java
> 版本，查阅 [JVM 版本要求](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/setup.html#jvm-version)。

##### 下载安装 `.zip` 包

Elasticsearch v7.17.9 的 .zip
存档可以从这里下载：[https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.9-windows-x86_64.zip](https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.9-windows-x86_64.zip)

用你最喜欢的解压工具解压它。这会创建一个名为 `elasticsearch-7.17.9` 的文件夹，我们将其作为 `%ES_HOME%`。在终端窗口，cd 到 `%ES_HOME%` 目录，如：

```shell
cd c:\elasticsearch-7.17.9
```

##### 启用系统索引自动创建

一些商业特性会在 Elasticsearch 中自动创建索引。默认情况下， Elasticsearch 配置为允许自动创建索引而不需要额外的步骤。然而，如果你在 Elasticsearch
中禁用了自动索引创建，则必须在 `elasticsearch.yml`
中配置 [`action.auto_create_index`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docs-index_.html#index-creation)
以允许商业特性创建以下索引：

```yaml
action.auto_create_index: .monitoring*,.watches,.triggered_watches,.watcher-history*,.ml*
```

> **警告**
>
> 如果你在使用 [Logstash](https://www.elastic.co/products/logstash) 或 [Beats](https://www.elastic.co/products/beats)
> ，那么你很可能需要在你的 `action.auto_create_index` 设置中使用额外的索引名字，具体的值取决于你的本地配置。如果你不确定你环境的正确值，可以考虑设置这个值为 `*` 以允许自动创建所有索引。

##### 从命令行运行 Elasticsearch

Elasticsearch 可以如以下从命令行启动：

```shell
.\bin\elasticsearch.bat
```

如果你对 Elasticsearch
密钥库进行了密码保护，会提示输入密码库密码。[查看安全设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/secure-settings.html)获取更多详情。

默认情况下，Elasticsearch 将其日志打印到控制台(`stdout`)
和 [logs 目录](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#path-settings)
中的 `<cluster name>.log` 文件。Elasticsearch 在启动时记录一些信息，但一旦完成初始化，它将继续在前台运行，直到发生值得记录的事情为止，它不会继续记录任何信息。当 Elasticsearch
运行时，你可以通过它的 HTTP 接口与它进行交互，默认端口为 9200。要停止 Elasticsearch，请按 `Ctrl-C`。

##### 在命令行配置 Elasticsearch

Elasticsearch 默认从 `$ES_HOME\configuring_elasticsearch\elasticsearch.yml`
加载配置。配置文件的格式在[配置 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html) 中解释。

在配置文件中可以指定的设置，也能在命令行中指定，如下使用 `-E` 语法：

```shell
.\bin\elasticsearch.bat -Ecluster.name=my_cluster -Enode.name=node_1
```

> **注意**
>
> 包含空格的值必须使用引号括起来。如 `-Epath.logs="C:\My Logs\logs"`。

> **提示**
>
> 通常，任意集群范围设置（如 `cluster.name`）应该添加到 `elasticsearch.yml` 配置文件中，而任何节点特定设置，如 `node.name`，应该在命令行中指定。

##### 检查 Elasticsearch 是否正在运行

你可以通过向 `localhost` 的 `9200` 端口发送 HTTP 请求来测试 Elasticsearch 节点是否正在运行：

```shell
GET /
```

这会给你这样的响应：

```json
{
  "name": "Cp8oag6",
  "cluster_name": "elasticsearch",
  "cluster_uuid": "AT69_T_DTp-1qgIJlatQqA",
  "version": {
    "number": "7.17.9",
    "build_flavor": "default",
    "build_type": "tar",
    "build_hash": "f27399d",
    "build_date": "2016-03-30T09:51:41.449Z",
    "build_snapshot": false,
    "lucene_version": "8.11.1",
    "minimum_wire_compatibility_version": "1.2.3",
    "minimum_index_compatibility_version": "1.2.3"
  },
  "tagline": "You Know, for Search"
}
```

##### 在 Windows 上安装 Elasticsearch 作为服务

Elasticsearch 可以作为一项服务安装在后台运行，也可以无需用户交互的在启动时自动启动。这可以通过在 `bin\` 目录中的 `elasticsearch-service.bat`
脚本来在命令行允许安装、移除、管理或者配置服务，且能启动和停止服务。

```shell
c:\elasticsearch-7.17.9\bin>elasticsearch-service.bat

Usage: elasticsearch-service.bat install|remove|start|stop|manager [SERVICE_ID]
```

这脚本需要一个参数（要执行的命令），后面跟着一个可选参数表示服务的id（安装多个 Elasticsearch 服务时很有用）。

命令有：

| 命令      | 描述                                  |
|---------|-------------------------------------|
| install | 安装 Elasticsearch 作为服务               |
| remove  | 移除安装的 Elasticsearch 服务（如果已启动，则停止服务） |
| start   | 启动 Elasticsearch 服务（如果已安装）          |
| stop    | 停止 Elasticsearch 服务（如果已启动）          |
| manager | 启动图形化界面（GUI）管理已安装的服务                |

在安装时，将提供服务的名字和 `JAVA_HOME` 的值：

```shell
c:\elasticsearch-7.17.9\bin>elasticsearch-service.bat install
Installing service      :  "elasticsearch-service-x64"
Using ES_JAVA_HOME (64-bit):  "c:\jvm\jdk1.8"
The service 'elasticsearch-service-x64' has been installed.
```

> **注意**
>
> 虽然 JRE 可以用于 Elasticsearch 服务，由于它使用客户端 VM （而不是服务器 JVM —— 它为长时间运行的应用程序提供更好的性能），不鼓励使用，并将发出警告。

> **注意**
>
> 系统环境变量 `ES_JAVA_HOME` 应该设置为你想让服务使用的 JDK 的安装路径。如果你升级 JDK，你不必重装服务，但你必须设置系统环境变量 `ES_JAVA_HOME` 为最新的 JDK 安装路径。然而，不支持跨 JVM
> 类型（如，JRE 对 SE）的升级，必须重装服务。

##### 自定义服务配置

可以在安装之前通过设置以下环境变量（使用命令行中的设置 [set命令](https://technet.microsoft.com/en-us/library/cc754250(v=ws.10).aspx)，或通过 `系统属性->环境变量`
GUI）来配置 Elasticsearch 服务。

| 名称                     | 描述                                                                                                                |
|------------------------|-------------------------------------------------------------------------------------------------------------------|
| `SERVICE_ID`           | 服务唯一标识。在同一台机器上安装多个实例时有用。默认为 `elasticsearch-service-x64`。                                                          |
| `SERVICE_USERNAME`     | 运行服务的用户，默认为本地系统账户。                                                                                                |
| `SERVICE_PASSWORD`     | 在 `%SERVICE_USERNAME%` 中指定的用户的密码。                                                                                 |
| `SERVICE_DISPLAY_NAME` | 服务名字。默认为 `Elasticsearch <version> %SERVICE_ID%`。                                                                  |
| `SERVICE_DESCRIPTION`  | 服务描述。默认为 `Elasticsearch <version> Windows Service - https://elastic.co`。                                          |
| `ES_JAVA_HOME`         | 运行服务所需的 JVM 安装目录。                                                                                                 |
| `SERVICE_LOG_DIR`      | 服务的日志目录，默认为 `%ES_HOME%\logs`。注意这不控制 Elasticsearch 日志路径，这个路径通过在配置文件 `elasticsearch.yml` 中的设置 `path.logs` 或在命令行中设置。 |
| `ES_PATH_CONF`         | 配置文件目录（需要包含 `elasticsearch.yml`、`jvm.options` 和 `log4j2.properties` 文件），默认为 `%ES_HOME%\config`。                   |
| `ES_JAVA_OPTS`         | 你想应用的任何其他 JVM 系统属性。                                                                                               |
| `ES_START_TYPE`        | 服务启动模式。可以为 `auto` 或 `manual`（默认）。                                                                                 |
| `ES_STOP_TIMEOUT`      | procrun 等待服务优雅退出的超时（秒）。默认为 `0`。                                                                                   |

> **注意**
>
> 作为它的核心，`elasticsearch-service.bat` 依赖于 [Apache Commons Daemon](https://commons.apache.org/proper/commons-daemon/)
> 项目来安装服务。在服务安装之前设置的环境变量被复制，并在服务生命周期中使用。这意味着除非重装服务，否则在安装后对他们的变更都不会被获取。

> **注意**
>
> 默认情况，Elasticsearch
> 依据节点的[角色](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-node.html#node-roles)和总内存，自动调整 JVM
> 堆大小。对于大多数生产环境，我们推荐默认调整。如果需要，你可以手动设置堆大小来覆盖默认调整。
>
> 当第一次在 Windows 上安装 Elasticsearch 作为服务或者从命令行运行 Elasticsearch
>
时，你可以按[JVM 堆大小设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/advanced-configuration.html#set-jvm-heap-size)
> 手工设置堆大小。为了调整已安装服务的堆大小，使用服务管理器：`bin\elasticsearch-service.bat manager`。

> **注意**
>
> 该服务会自动配置一个私有临时目录供 Elasticsearch 运行时使用。该私有临时目录配置为用户运行安装的私有临时目录的子目录。如果这个服务要在不同的用户下运行，你可以在执行服务安装之前，通过设置环境变量 `ES_TMPDIR`
> 配置这个临时目录的优先位置。

##### 使用管理 GUI

也可以在安装后使用管理界面（`elasticsearch-service-mgr.exe`
）来配置服务，它提供了对已安装服务的深入了解，包括其状态、启动类型、JVM、启动和停止设置等。从命令行调用 `elasticsearch-service.bat` 将会打开管理器窗口：

![https://www.elastic.co/guide/en/elasticsearch/reference/7.17/images/service-manager-win.png](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/images/service-manager-win.png)

通过管理界面所做的大多数修改（如 JVM 设置），都需要重启服务才能生效。

##### `.zip` 存档的目录结构

`.zip` 包是完全独立的。默认情况下，所有文件和目录都包含在 `$ES_HOME` —— 解压存档时创建的目录。

这样很方便，因为你不必创建任意目录去启用 Elasticsearch，且卸载 Elasticsearch 就像移除 `$ES_HOME`
目录一样简单。但是，建议修改默认的配置目录（config）和数据目录（data）以便以后不用删除重要数据。

| 类型      | 描述                                                          | 默认位置               | 设置                                                                                                               |
|---------|-------------------------------------------------------------|--------------------|------------------------------------------------------------------------------------------------------------------|
| home    | Elasticsearch 主目录或 `$ES_HOME`                               | 解压压缩包创建的目录         ||
| bin     | 二进制脚本，包括启动节点的 `elasticsearch` 和安装插件的 `elasticsearch-plugin` | `$ES_HOME/bin`     ||
| conf    | 配置文件，包括 `elasticsearch.yml`                                 | `$ES_HOME/config`  | [ES_PATH_CONF](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#config-files-location) |
| data    | 分配在节点上的每个索引和分片的数据文件位置。可以有多个位置。                              | `$ES_HOME/data`    | `path.data`                                                                                                      |
| logs    | 日志文件位置                                                      | `$ES_HOME/logs`    | `path.logs`                                                                                                      |
| plugins | 插件文件位置。每个插件会包含在一个子目录中。                                      | `$ES_HOME/plugins` ||
| repo    | 共享文件系统仓库位置。可以有多个位置。文件系统仓库可以放在此处指定的任何目录的任何子目录中。              | 未配置                | `path.repo`                                                                                                      |

##### 下一步

你现在有一个测试 Elasticsearch 环境部署好。在你使用 Elasticsearch 正式开始开发或者生产之前，你必须做一些额外的设置：

- 学习如何[配置 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html)。
- 配置[重要的 Elasticsearch 设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html)。
- 配置[重要的系统设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/system-config.html)。

#### 使用 Debian 包安装 Elasticsearch

Elasticsearch 的 Debian 包可以从[我们的网站](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/deb.html#install-deb)
或者从我们的 [APT 仓库](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/deb.html#deb-repo)下载。它可以用于在任何基于 Debian
的系统（如 Debian 和 Ubuntu）上安装 Elasticsearch。

这个包包含免费和订阅的特性。[开始 30 天的试用](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/license-settings.html)，尝试所有功能。

Elasticsearch 的最新稳定版本，能在 [Elasticsearch 下载页面](https://www.elastic.co/downloads/elasticsearch)
找到。其他版本能在[历史发布页面](https://www.elastic.co/downloads/past-releases)找到。

> **注意**
>
> Elasticsearch 包含 JDK 维护者（GPLv2+CE）提供的 [OpenJDK](https://openjdk.java.net/)捆绑版本。要使用自己的 Java
> 版本，查阅 [JVM 版本要求](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/setup.html#jvm-version)。

##### 导入 Elasticsearch PGP 密钥

我们使用带指纹的 Elasticsearch 签名密钥（PGP 密钥 [D88E42B4](https://pgp.mit.edu/pks/lookup?op=vindex&search=0xD27D666CD88E42B4)
，存在[https://pgp.mit.edu](https://pgp.mit.edu/)上）签名所有的包：

4609 5ACC 8548 582C 1A26 99A9 D27D 666C D88E 42B4

下载和安装公共签名密钥：

```shell
wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo gpg --dearmor -o /usr/share/keyrings/elasticsearch-keyring.gpg
```

##### 从 APT 仓库安装

在继续之前，你可能需要在 Debian 上安装 `apt-transport-https` 包：

```shell
sudo apt-get install apt-transport-https
```

将仓库定义保存到 `/etc/apt/sources.list.d/elastic-7.x.list`:

```shell
echo "deb [signed-by=/usr/share/keyrings/elasticsearch-keyring.gpg] https://artifacts.elastic.co/packages/7.x/apt stable main" | sudo tee /etc/apt/sources.list.d/elastic-7.x.list
```

> 由于以下原因，指南不使用 `add-apt-repository`:
>
> 1. `add-apt-repository` 向系统 `/etc/apt/sources.list` 文件中添加条目，而不是 `/etc/apt/sources.list.d` 中的每个仓库的干净文件
> 2. `add-apt-repository` 不是许多发行版本的默认安装部分，需要许多非默认的依赖
> 3. 老版本的 `add-apt-repository` 总会添加一个 `deb-src` 条目，由于我们没有提供源包，这会导致错误。如果你已经添加了 `deb-src` 条目，在你删除 `deb-src`
     条目前，你会看到如下错误： `Unable to find expected entry 'main/source/Sources' in Release file (Wrong sources.list entry or malformed file`)

你可以这样安装 Elasticsearch Debian 包：

```shell
sudo apt-get update && sudo apt-get install elasticsearch
```

> **警告**
>
> 如果 Elasticsearch 仓库中存在两条相同的条目，你在 `apt-get update` 操作时，会看到如下错误：
>
> `Duplicate sources.list entry https://artifacts.elastic.co/packages/7.x/apt/ ...`
>
> 检查 `/etc/apt/sources.list.d/elasticsearch-7.x.list` 的重复条目，或者在 `/etc/apt/sources.list.d/` 中的文件和 `/etc/apt/sources.list`
> 文件中定位重复条目。

> **注意**
>
> 在基于 systemd 的发行版上，安装脚本尝试设置内核参数（如 `vm.max_map_count`）；你可以通过屏蔽 `systemd-sysctl.service` 单位来跳过这个操作。

##### 手工下载和安装 Debian 包

Elasticsearch v7.17.9 的 Debian 包，可以按以下命令从网站下载和安装：

```shell
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.9-amd64.deb
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.9-amd64.deb.sha512
shasum -a 512 -c elasticsearch-7.17.9-amd64.deb.sha512  ①
sudo dpkg -i elasticsearch-7.17.9-amd64.deb
```

① 比较下载的 Debian 包 SHA 值和公开的校验值。正常应该输出 `elasticsearch-{version}-amd64.deb: OK`。

##### 启用系统索引自动创建

一些商业特性会在 Elasticsearch 中自动创建索引。默认情况下， Elasticsearch 配置为允许自动创建索引而不需要额外的步骤。然而，如果你在 Elasticsearch
中禁用了自动索引创建，则必须在 `elasticsearch.yml`
中配置 [`action.auto_create_index`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docs-index_.html#index-creation)
以允许商业特性创建以下索引：

```yaml
action.auto_create_index: .monitoring*,.watches,.triggered_watches,.watcher-history*,.ml*
```

> **警告**
>
> 如果你在使用 [Logstash](https://www.elastic.co/products/logstash) 或 [Beats](https://www.elastic.co/products/beats)
> ，那么你很可能需要在你的 `action.auto_create_index` 设置中使用额外的索引名字，具体的值取决于你的本地配置。如果你不确定你环境的正确值，可以考虑设置这个值为 `*` 以允许自动创建所有索引。

##### SysV `init` VS `systemd`

Elasticsearch 在安装后不会自动启动。如何启动和停止 Elasticsearch，取决于你的系统用的 SysV `init` 还是 `systemd`（更新的发行版用的）。你可以通过以下命令来判断用的哪个：

```shell
ps -p 1
```

##### 使用 SysV `init` 运行 Elasticsearch

使用 `update-rc.d` 命令配置当系统启动时自动启动 Elasticsearch：

```shell
sudo update-rc.d elasticsearch defaults 95 10
```

Elasticsearch 可以使用 `service` 命令来启动和停止：

```shell
sudo -i service elasticsearch start
sudo -i service elasticsearch stop
```

如果 Elasticsearch 由于任何原因启动失败，它会输出失败原因到标准输出（STDOUT）。日志文件可以在 `/var/log/elasticsearch/` 中被找到。

##### 使用 `systemd` 运行 Elasticsearch

为了配置 Elasticsearch 在系统启动时自动启动，运行以下命令：

```shell
sudo /bin/systemctl daemon-reload
sudo /bin/systemctl enable elasticsearch.service
```

Elasticsearch 可以按以下方式启动和停止：

```shell
sudo systemctl start elasticsearch.service
sudo systemctl stop elasticsearch.service
```

这些命令不提供 Elasticsearch 运行成功与否的反馈。相反，信息写在位于 `/var/log/elasticsearch/` 中的日志文件。

如果你的 Elasticsearch 密码库受密码保护，你需要使用本地文件和 `systemd` 环境变量向密码库提供密码。本地文件存在时，应该受到保护，一旦 Elasticsearch 启动并运行，就可以安全删除此文件。

```shell
echo "keystore_password" > /path/to/my_pwd_file.tmp
chmod 600 /path/to/my_pwd_file.tmp
sudo systemctl set-environment ES_KEYSTORE_PASSPHRASE_FILE=/path/to/my_pwd_file.tmp
sudo systemctl start elasticsearch.service
```

默认情况下，Elasticsearch 服务不会在 `systemd` 日志中记录信息。要启用 `journalctl` 日志，必须从文件 `elasticsearch.service` 中的 `ExecStart`
命令行移除 `--quiet` 选项。

当 `systemd` 日志启用时，使用 `journalctl` 命令日志信息可用。

要跟踪日志：

```shell
sudo journalctl -f
```

列出 Elasticsearch 服务的日志条目：

```shell
sudo journalctl --unit elasticsearch
```

要列出从给定时间开始的 Elasticsearch 服务的日志条目，请执行以下操作：

```shell
sudo journalctl --unit elasticsearch --since  "2016-10-30 18:17:16"
```

检查 `man journalctl`
或者 [https://www.freedesktop.org/software/systemd/man/journalctl.html](https://www.freedesktop.org/software/systemd/man/journalctl.html)
获取更多的命令行选项。

> **提示**
>
> **旧 systemd 版本的启动超时**
>
> 默认情况下，Elasticsearch 将 `TimeoutStartSec` 参数设置为 `systemd` 为 `900` 秒。如果你正在运行 `systemd` 的 238 以上版本，Elasticsearch
> 可以自动延长启动超时时间，即使启动时间超过 900 秒，Elasticsearch 也会重复这样做，直到启动完成。
>
> `systemd` 238 之前的版本不支持超时扩展机制，如果 Elasticsearch 进程在配置的超时内没有完全启动，将终止 Elasticsearch 进程。如果发生这种情况，Elasticsearch
> 将在日志中报告它在启动后短时间内正常关闭：
>
> ```text
> [2022-01-31T01:22:31,077][INFO ][o.e.n.Node               ] [instance-0000000123] starting ...
> ...
> [2022-01-31T01:37:15,077][INFO ][o.e.n.Node               ] [instance-0000000123] stopping ...
> ```
>
> 但是，`systemd` 日志将报告启动超时
>
> ```text
> Jan 31 01:22:30 debian systemd[1]: Starting Elasticsearch...
> Jan 31 01:37:15 debian systemd[1]: elasticsearch.service: Start operation timed out. Terminating.
> Jan 31 01:37:15 debian systemd[1]: elasticsearch.service: Main process exited, code=killed, status=15/TERM
> Jan 31 01:37:15 debian systemd[1]: elasticsearch.service: Failed with result 'timeout'.
> Jan 31 01:37:15 debian systemd[1]: Failed to start Elasticsearch.
> ```
>
> 要避免这种情况，请将 `systemd` 升级到至少 238 版本。你还可以通过扩展 `TimeoutStartSec` 参数临时解决这个问题。

##### 检查 Elasticsearch 是否正在运行

你可以通过向 `localhost` 的 `9200` 端口发送 HTTP 请求来测试 Elasticsearch 节点是否正在运行：

```shell
GET /
```

这会给你这样的响应：

```json
{
  "name": "Cp8oag6",
  "cluster_name": "elasticsearch",
  "cluster_uuid": "AT69_T_DTp-1qgIJlatQqA",
  "version": {
    "number": "7.17.9",
    "build_flavor": "default",
    "build_type": "tar",
    "build_hash": "f27399d",
    "build_date": "2016-03-30T09:51:41.449Z",
    "build_snapshot": false,
    "lucene_version": "8.11.1",
    "minimum_wire_compatibility_version": "1.2.3",
    "minimum_index_compatibility_version": "1.2.3"
  },
  "tagline": "You Know, for Search"
}
```

##### 配置 Elasticsearch

`/etc/elasticsearch` 目录包含 Elasticsearch 默认运行时配置。该目录和所包含的所有文件所有权在包安装时设置为 `root:elasticsearch`。

`setgid` 标志对目录 `/etc/elasticsearch` 应用组权限，以确保 Elasticsearch 能读取任何包含的文件和子目录。所有文件和子目录继承 `root:elasticsearch`
所有权。从该目录或者任何子目录运行命令，如 [`elasticsearch-keystore` 工具](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/secure-settings.html)
，需要 `root:elasticsearch` 权限。

Elasticsearch 默认从 `/etc/elasticsearch/elasticsearch.yml`
文件加载它的配置。在[配置 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html) 中解释了配置文件的格式。

Debian 包也有一个系统配置文件（`/etc/default/elasticsearch`），它允许你设置以下的变量：

| 配置                   | 描述                                                                                                                                                                                                                            |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `ES_JAVA_HOME`       | 设置使用的自定义 Java 路径                                                                                                                                                                                                              |
| `MAX_OPEN_FILES`     | 打开文件的最大值，默认为 `65535`                                                                                                                                                                                                          |
| `MAX_LOCKED_MEMORY`  | 最大锁定内存值。如果你在 elasticsearch.yml 中使用 `bootstrap.memory_lock` 选项，设置为 `unlimited`。                                                                                                                                                |
| `MAX_MAP_COUNT`      | 进程可能具有的最大内存映射区域数。如果你使用 `mmapfs` 作为索引存储类型，请确保将其设置一个高值。有关更多的信息，请查看关于 `max_map_count` 的 [linux 内核文档](https://github.com/torvalds/linux/blob/master/Documentation/sysctl/vm.txt)。在 Elasticsearch 启动前，通过 `sysctl` 设置，默认为 `262144`。 |
| `ES_PATH_CONF`       | 配置文件目录（需要包含 `elasticsearch.yml`、`jvm.options` 和 `log4j2.properties` 文件），默认为 `/etc/elasticsearch`。                                                                                                                             |
| `ES_JAVA_OPTS`       | 你想应用的任何其他 JVM 系统属性。                                                                                                                                                                                                           |
| `RESTART_ON_UPGRADE` | 在包升级时配置重启，默认为 `false`。这意味着你必须在手工安装包后重启你的 Elasticsearch 实例。这样做的原因是为了确保集群的升级不会导致持续的分片重分配，进而导致的高网络流量和降低了集群的响应时间。                                                                                                                 |

> **注意**
>
> 使用 `systemd` 的发行版本要求需要通过 `systemd` 配置系统资源限制，而不是通过 `/etc/sysconfig/elasticsearch`
> 文件。更多信息，请参阅 [Systemd 配置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/setting-system-settings.html#systemd)
> 。

##### Debian 包目录结构

Debian 包将配置文件、日志和数据目录放在基于 Debian 系统的适当位置：

| 类型      | 描述                                                                                            | 默认位置                               | 设置                                                                                                               |
|---------|-----------------------------------------------------------------------------------------------|------------------------------------|------------------------------------------------------------------------------------------------------------------|
| home    | Elasticsearch 主目录或 `$ES_HOME`                                                                 | `/usr/share/elasticsearch`         ||
| bin     | 二进制脚本，包括启动节点的 `elasticsearch` 和安装插件的 `elasticsearch-plugin`                                   | `/usr/share/elasticsearch/bin`     ||
| conf    | 配置文件，包括 `elasticsearch.yml`                                                                   | `/etc/elasticsearch`               | [ES_PATH_CONF](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#config-files-location) |
| conf    | 环境变量，包括堆大小，文件描述符。                                                                             | `/etc/default/elasticsearch`       |                                                                                                                  |
| data    | 分配在节点上的每个索引和分片的数据文件位置。可以有多个位置。                                                                | `/var/lib/elasticsearch`           | `path.data`                                                                                                      |
| jdk     | 用于运行 Elasticsearch 的捆绑 Java 开发工具包。可以通过在 `/etc/default/elasticsearch` 中覆盖 `ES_JAVA_HOME` 环境变量。 | `/usr/share/elasticsearch/jdk`     |                                                                                                                  |
| logs    | 日志文件位置                                                                                        | `/var/log/elasticsearch`           | `path.logs`                                                                                                      |
| plugins | 插件文件位置。每个插件会包含在一个子目录中。                                                                        | `/usr/share/elasticsearch/plugins` ||
| repo    | 共享文件系统仓库位置。可以有多个位置。文件系统仓库可以放在此处指定的任何目录的任何子目录中。                                                | 未配置                                | `path.repo`                                                                                                      |

##### 下一步

你现在有一个测试 Elasticsearch 环境部署好。在你使用 Elasticsearch 正式开始开发或者生产之前，你必须做一些额外的设置：

- 学习如何[配置 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html)。
- 配置[重要的 Elasticsearch 设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html)。
- 配置[重要的系统设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/system-config.html)。

#### 使用 RPM 安装 Elasticsearch

Elasticsearch 的 RPM 可以从[我们的网站](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/rpm.html#install-rpm)
或者从我们的 [RPM 仓库](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/rpm.html#rpm-repo)下载。它可以用于在任何基于 RPM 的系统（如
OpenSuSE，SLES，Centos，Red Hat 和 Oracle Enterprise）上安装 Elasticsearch。

> **注意**
>
> 老版本的 RPM 发行版本（如 SLES 11 和 CentOS 5）不支持 RPM
> 安装。请参阅 [在 Linux 或 MacOS 上用存档安装 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/targz.html)
> 。

这个包包含免费和订阅的特性。[开始 30 天的试用](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/license-settings.html)，尝试所有功能。

Elasticsearch 的最新稳定版本，能在 [Elasticsearch 下载页面](https://www.elastic.co/downloads/elasticsearch)
找到。其他版本能在[历史发布页面](https://www.elastic.co/downloads/past-releases)找到。

> **注意**
>
> Elasticsearch 包含 JDK 维护者（GPLv2+CE）提供的 [OpenJDK](https://openjdk.java.net/)捆绑版本。要使用自己的 Java
> 版本，查阅 [JVM 版本要求](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/setup.html#jvm-version)。

##### 导入 Elasticsearch PGP 密钥

我们使用带指纹的 Elasticsearch 签名密钥（PGP 密钥 [D88E42B4](https://pgp.mit.edu/pks/lookup?op=vindex&search=0xD27D666CD88E42B4)
，存在[https://pgp.mit.edu](https://pgp.mit.edu/)上）签名所有的包：

4609 5ACC 8548 582C 1A26 99A9 D27D 666C D88E 42B4

下载和安装公共签名密钥：

```shell
rpm --import https://artifacts.elastic.co/GPG-KEY-elasticsearch
```

##### 从 RPM 仓库安装

为基于 RedHat 的发行版，在目录 `/etc/yum.repos.d/` 中创建一个命名为 `elasticsearch.repo` 的文件，或者为基于 OpenSuSE 的发行版在目录 `/etc/zypp/repos.d/`
中创建文件，内容包括：

```shell
[elasticsearch]
name=Elasticsearch repository for 7.x packages
baseurl=https://artifacts.elastic.co/packages/7.x/yum
gpgcheck=1
gpgkey=https://artifacts.elastic.co/GPG-KEY-elasticsearch
enabled=0
autorefresh=1
type=rpm-md
```

仓库已准备就绪。现在可以使用以下的任一命令安装 Elasticsearch：

```shell
sudo yum install --enablerepo=elasticsearch elasticsearch  ①
sudo dnf install --enablerepo=elasticsearch elasticsearch  ②
sudo zypper modifyrepo --enable elasticsearch && \
  sudo zypper install elasticsearch; \
  sudo zypper modifyrepo --disable elasticsearch  ③
```

① 在 CentOS 和老的基于 Red Hat 的发行版上使用 `yum`。

② 在 Fedora 和其他新的 Red Hat 的发行版上使用 `dnf`。

③ 在基于 OpenSUSE 的发行版本上使用 `zypper`。

> **注意**
>
> 配置的仓库默认是禁用的。这排除了升级系统其他部分时意外升级 Elasticsearch 的可能性。每个安装或者升级命令必须显示启用仓库，如上面的示例命令所示。

##### 手工下载和安装 RPM

Elasticsearch v7.17.9 的 RPM，可以按以下命令从网站下载和安装：

```shell
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.9-x86_64.rpm
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.9-x86_64.rpm.sha512
shasum -a 512 -c elasticsearch-7.17.9-x86_64.rpm.sha512   ①
sudo rpm --install elasticsearch-7.17.9-x86_64.rpm
```

① 比较下载的 RPM SHA 值和公开的校验值。正常应该输出 `elasticsearch-{version}-x86_64.rpm: OK`。

> **注意**
>
> 在基于 `systemd` 的发行版上，安装脚本将尝试设置内核参数（例如 `vm.max_map_count`）；你可以通过屏蔽 `systemd-sysctl.service` 单元来跳过此操作。

##### 启用系统索引自动创建

一些商业特性会在 Elasticsearch 中自动创建索引。默认情况下， Elasticsearch 配置为允许自动创建索引而不需要额外的步骤。然而，如果你在 Elasticsearch
中禁用了自动索引创建，则必须在 `elasticsearch.yml`
中配置 [`action.auto_create_index`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docs-index_.html#index-creation)
以允许商业特性创建以下索引：

```yaml
action.auto_create_index: .monitoring*,.watches,.triggered_watches,.watcher-history*,.ml*
```

> **警告**
>
> 如果你在使用 [Logstash](https://www.elastic.co/products/logstash) 或 [Beats](https://www.elastic.co/products/beats)
> ，那么你很可能需要在你的 `action.auto_create_index` 设置中使用额外的索引名字，具体的值取决于你的本地配置。如果你不确定你环境的正确值，可以考虑设置这个值为 `*` 以允许自动创建所有索引。

##### SysV `init` VS `systemd`

Elasticsearch 在安装后不会自动启动。如何启动和停止 Elasticsearch，取决于你的系统用的 SysV `init` 还是 `systemd`（更新的发行版用的）。你可以通过以下命令来判断用的哪个：

```shell
ps -p 1
```

##### 使用 SysV `init` 运行 Elasticsearch

使用 `chkconfig` 命令配置 Elasticsearch 在系统引导时自动启动：

```shell
sudo chkconfig --add elasticsearch
```

Elasticsearch 可以使用 `service` 命令来启动和停止：

```shell
sudo -i service elasticsearch start
sudo -i service elasticsearch stop
```

如果 Elasticsearch 由于任何原因启动失败，它会输出失败原因到标准输出（STDOUT）。日志文件可以在 `/var/log/elasticsearch/` 中被找到。

##### 使用 `systemd` 运行 Elasticsearch

为了配置 Elasticsearch 在系统启动时自动启动，运行以下命令：

```shell
sudo /bin/systemctl daemon-reload
sudo /bin/systemctl enable elasticsearch.service
```

Elasticsearch 可以按以下方式启动和停止：

```shell
sudo systemctl start elasticsearch.service
sudo systemctl stop elasticsearch.service
```

这些命令不提供 Elasticsearch 运行成功与否的反馈。相反，信息写在位于 `/var/log/elasticsearch/` 中的日志文件。

如果你的 Elasticsearch 密码库受密码保护，你需要使用本地文件和 `systemd` 环境变量向密码库提供密码。本地文件存在时，应该受到保护，一旦 Elasticsearch 启动并运行，就可以安全删除此文件。

```shell
echo "keystore_password" > /path/to/my_pwd_file.tmp
chmod 600 /path/to/my_pwd_file.tmp
sudo systemctl set-environment ES_KEYSTORE_PASSPHRASE_FILE=/path/to/my_pwd_file.tmp
sudo systemctl start elasticsearch.service
```

默认情况下，Elasticsearch 服务不会在 `systemd` 日志中记录信息。要启用 `journalctl` 日志，必须从文件 `elasticsearch.service` 中的 `ExecStart`
命令行移除 `--quiet` 选项。

当 `systemd` 日志启用时，使用 `journalctl` 命令日志信息可用。

要跟踪日志：

```shell
sudo journalctl -f
```

列出 Elasticsearch 服务的日志条目：

```shell
sudo journalctl --unit elasticsearch
```

要列出从给定时间开始的 Elasticsearch 服务的日志条目，请执行以下操作：

```shell
sudo journalctl --unit elasticsearch --since  "2016-10-30 18:17:16"
```

检查 `man journalctl`
或者 [https://www.freedesktop.org/software/systemd/man/journalctl.html](https://www.freedesktop.org/software/systemd/man/journalctl.html)
获取更多的命令行选项。

> **提示**
>
> **旧 systemd 版本的启动超时**
>
> 默认情况下，Elasticsearch 将 `TimeoutStartSec` 参数设置为 `systemd` 为 `900` 秒。如果你正在运行 `systemd` 的 238 以上版本，Elasticsearch
> 可以自动延长启动超时时间，即使启动时间超过 900 秒，Elasticsearch 也会重复这样做，直到启动完成。
>
> `systemd` 238 之前的版本不支持超时扩展机制，如果 Elasticsearch 进程在配置的超时内没有完全启动，将终止 Elasticsearch 进程。如果发生这种情况，Elasticsearch
> 将在日志中报告它在启动后短时间内正常关闭：
>
> ```text
> [2022-01-31T01:22:31,077][INFO ][o.e.n.Node               ] [instance-0000000123] starting ...
> ...
> [2022-01-31T01:37:15,077][INFO ][o.e.n.Node               ] [instance-0000000123] stopping ...
> ```
>
> 但是，`systemd` 日志将报告启动超时
>
> ```text
> Jan 31 01:22:30 debian systemd[1]: Starting Elasticsearch...
> Jan 31 01:37:15 debian systemd[1]: elasticsearch.service: Start operation timed out. Terminating.
> Jan 31 01:37:15 debian systemd[1]: elasticsearch.service: Main process exited, code=killed, status=15/TERM
> Jan 31 01:37:15 debian systemd[1]: elasticsearch.service: Failed with result 'timeout'.
> Jan 31 01:37:15 debian systemd[1]: Failed to start Elasticsearch.
> ```
>
> 要避免这种情况，请将 `systemd` 升级到至少 238 版本。你还可以通过扩展 `TimeoutStartSec` 参数临时解决这个问题。

##### 检查 Elasticsearch 是否正在运行

你可以通过向 `localhost` 的 `9200` 端口发送 HTTP 请求来测试 Elasticsearch 节点是否正在运行：

```shell
GET /
```

这会给你这样的响应：

```json
{
  "name": "Cp8oag6",
  "cluster_name": "elasticsearch",
  "cluster_uuid": "AT69_T_DTp-1qgIJlatQqA",
  "version": {
    "number": "7.17.9",
    "build_flavor": "default",
    "build_type": "tar",
    "build_hash": "f27399d",
    "build_date": "2016-03-30T09:51:41.449Z",
    "build_snapshot": false,
    "lucene_version": "8.11.1",
    "minimum_wire_compatibility_version": "1.2.3",
    "minimum_index_compatibility_version": "1.2.3"
  },
  "tagline": "You Know, for Search"
}
```

##### 配置 Elasticsearch

`/etc/elasticsearch` 目录包含 Elasticsearch 默认运行时配置。该目录和所包含的所有文件所有权在包安装时设置为 `root:elasticsearch`。

`setgid` 标志对目录 `/etc/elasticsearch` 应用组权限，以确保 Elasticsearch 能读取任何包含的文件和子目录。所有文件和子目录继承 `root:elasticsearch`
所有权。从该目录或者任何子目录运行命令，如 [`elasticsearch-keystore` 工具](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/secure-settings.html)
，需要 `root:elasticsearch` 权限。

Elasticsearch 默认从 `/etc/elasticsearch/elasticsearch.yml`
文件加载它的配置。在[配置 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html) 中解释了配置文件的格式。

Debian 包也有一个系统配置文件（`/etc/default/elasticsearch`），它允许你设置以下的变量：

| 配置                   | 描述                                                                                                                                                                                                                            |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `ES_JAVA_HOME`       | 设置使用的自定义 Java 路径                                                                                                                                                                                                              |
| `MAX_OPEN_FILES`     | 打开文件的最大值，默认为 `65535`                                                                                                                                                                                                          |
| `MAX_LOCKED_MEMORY`  | 最大锁定内存值。如果你在 elasticsearch.yml 中使用 `bootstrap.memory_lock` 选项，设置为 `unlimited`。                                                                                                                                                |
| `MAX_MAP_COUNT`      | 进程可能具有的最大内存映射区域数。如果你使用 `mmapfs` 作为索引存储类型，请确保将其设置一个高值。有关更多的信息，请查看关于 `max_map_count` 的 [linux 内核文档](https://github.com/torvalds/linux/blob/master/Documentation/sysctl/vm.txt)。在 Elasticsearch 启动前，通过 `sysctl` 设置，默认为 `262144`。 |
| `ES_PATH_CONF`       | 配置文件目录（需要包含 `elasticsearch.yml`、`jvm.options` 和 `log4j2.properties` 文件），默认为 `/etc/elasticsearch`。                                                                                                                             |
| `ES_JAVA_OPTS`       | 你想应用的任何其他 JVM 系统属性。                                                                                                                                                                                                           |
| `RESTART_ON_UPGRADE` | 在包升级时配置重启，默认为 `false`。这意味着你必须在手工安装包后重启你的 Elasticsearch 实例。这样做的原因是为了确保集群的升级不会导致持续的分片重分配，进而导致的高网络流量和降低了集群的响应时间。                                                                                                                 |

> **注意**
>
> 使用 `systemd` 的发行版本要求需要通过 `systemd` 配置系统资源限制，而不是通过 `/etc/sysconfig/elasticsearch`
> 文件。更多信息，请参阅 [Systemd 配置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/setting-system-settings.html#systemd)
> 。

##### RPM 目录结构

RPM 包将配置文件、日志和数据目录放在基于 RPM 系统的适当位置：

| 类型      | 描述                                                                                            | 默认位置                               | 设置                                                                                                               |
|---------|-----------------------------------------------------------------------------------------------|------------------------------------|------------------------------------------------------------------------------------------------------------------|
| home    | Elasticsearch 主目录或 `$ES_HOME`                                                                 | `/usr/share/elasticsearch`         ||
| bin     | 二进制脚本，包括启动节点的 `elasticsearch` 和安装插件的 `elasticsearch-plugin`                                   | `/usr/share/elasticsearch/bin`     ||
| conf    | 配置文件，包括 `elasticsearch.yml`                                                                   | `/etc/elasticsearch`               | [ES_PATH_CONF](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#config-files-location) |
| conf    | 环境变量，包括堆大小，文件描述符。                                                                             | `/etc/default/elasticsearch`       |                                                                                                                  |
| data    | 分配在节点上的每个索引和分片的数据文件位置。可以有多个位置。                                                                | `/var/lib/elasticsearch`           | `path.data`                                                                                                      |
| jdk     | 用于运行 Elasticsearch 的捆绑 Java 开发工具包。可以通过在 `/etc/default/elasticsearch` 中覆盖 `ES_JAVA_HOME` 环境变量。 | `/usr/share/elasticsearch/jdk`     |                                                                                                                  |
| logs    | 日志文件位置                                                                                        | `/var/log/elasticsearch`           | `path.logs`                                                                                                      |
| plugins | 插件文件位置。每个插件会包含在一个子目录中。                                                                        | `/usr/share/elasticsearch/plugins` ||
| repo    | 共享文件系统仓库位置。可以有多个位置。文件系统仓库可以放在此处指定的任何目录的任何子目录中。                                                | 未配置                                | `path.repo`                                                                                                      |

##### 下一步

你现在有一个测试 Elasticsearch 环境部署好。在你使用 Elasticsearch 正式开始开发或者生产之前，你必须做一些额外的设置：

- 学习如何[配置 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html)。
- 配置[重要的 Elasticsearch 设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html)。
- 配置[重要的系统设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/system-config.html)。

#### 使用 Docker 安装 Elasticsearch

Elasticsearch 也提供 Docker 镜像。所有已发布的 Docker 镜像列表和版本都在 [www.docker.elastic.co](https://www.docker.elastic.co/)
上。源文件在 [github](https://github.com/elastic/elasticsearch/blob/7.17/distribution/docker)。

软件包包含免费和订阅的特性。[开始 30 天的试用](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/license-settings.html)，尝试所有功能。

##### 拉取镜像拉取镜像

Docker 上获取 Elasticsearch，简单到只要向 Elastic Docker 仓库发出 `docker pull` 命令一样。

```shell
docker pull docker.elastic.co/elasticsearch/elasticsearch:7.17.9
```

##### 使用 Docker 启动单节点集群

要启动单节点 Elasticsearch
集群进行开发或测试，请指定[单节点发现](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/bootstrap-checks.html#single-node-discovery)
以绕过[启动检查](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/bootstrap-checks.html)：

```shell
docker run -p 127.0.0.1:9200:9200 -p 127.0.0.1:9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.17.9
```

##### 使用 Docker Compose 启动多节点集群

为了在 Docker 中启动和运行一个三节点 Elasticsearch 集群，你可以使用 Docker Compose：

1. 创建一个 docker-compose.yml 文件：

```yaml
version: '2.2'
services:
  es01:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.17.9
    container_name: es01
    environment:
      - node.name=es01
      - cluster.name=es-docker-cluster
      - discovery.seed_hosts=es02,es03
      - cluster.initial_master_nodes=es01,es02,es03
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - data01:/usr/share/elasticsearch/data
    ports:
      - 9200:9200
    networks:
      - elastic
  es02:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.17.9
    container_name: es02
    environment:
      - node.name=es02
      - cluster.name=es-docker-cluster
      - discovery.seed_hosts=es01,es03
      - cluster.initial_master_nodes=es01,es02,es03
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - data02:/usr/share/elasticsearch/data
    networks:
      - elastic
  es03:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.17.9
    container_name: es03
    environment:
      - node.name=es03
      - cluster.name=es-docker-cluster
      - discovery.seed_hosts=es01,es02
      - cluster.initial_master_nodes=es01,es02,es03
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - data03:/usr/share/elasticsearch/data
    networks:
      - elastic

volumes:
  data01:
    driver: local
  data02:
    driver: local
  data03:
    driver: local

networks:
  elastic:
    driver: bridge
```

> **注意**
>
> 例子中的 `docker-compose.yml` 使用环境变量 `ES_JAVA_OPTS` 手工设置堆大小为 512 MB。我们不推荐在生产环境使用 `ES_JAVA_OPTS`
> 。参看[手工设置堆大小](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#docker-set-heap-size)。

这个示例 Docker Compose 文件，提供了一个三节点 Elasticsearch 集群。节点 `es01` 监听 `localhost:9200`，`es02` 和 `es03` 通过 Docker 网络与 `es01` 通信。

请注意此配置在所有网络接口上暴露端口 9200，并且考虑到 Docker 怎么在 Linux 上操作 `iptables`，这意味着你的 Elasticsearch 集群可以公开访问，可能会忽略任何防火墙设置。如果你不想暴露端口
9200，转而使用反向代理，在 docker-compose.yml 文件中用 `127.0.0.1:9200:9200` 替代 `9200:9200`。Elasticsearch 将只能从主机自身访问。

[Docker 命名卷](https://docs.docker.com/storage/volumes)`data01`、`data02` 和 `data03`
存储节点数据目录，以便重启时数据持续存在。如果他们不存在，`docker-compose` 将会在你创建集群时创建他们。

1. 确保 Docker Engine 分配了至少 4 GiB 内存。在 Docker 桌面中，你可以在首选项（macOS）或设置（Windows）的高级选项卡中配置资源使用。

> **注意**
>
> 在 Linux 上，Docker Compose 未与 Docker 一起预装。在 docs.docker.com
> 查看安装指南：在 [Linux 安装 Compose](https://docs.docker.com/compose/install)

2. 运行 `docker-compose` 以启动集群：

```shell
docker-compose up
```

3. 提交请求 `_cat/nodes` 查看节点是否启动运行

```shell
curl -X GET "localhost:9200/_cat/nodes?v=true&pretty"
```

日志消息进入控制台，由配置的 Docker 日志驱动处理。默认情况下，你可以使用 `docker logs` 访问日志。如果你更想 Elasticsearch 容器把日志写入磁盘，设置环境变量 `ES_LOG_STYLE` 为
file。这将导致 Elasticsearch 使用与其他 Elasticsearch 分发格式相同的配置。

要停止集群，运行 `docker-compose down`。当你使用 `docker-compose up` 重启集群，Docker 卷中的数据将被保存和加载。为了在停止集群时**删除数据卷**，指定 `-v`
选项： `docker-compose down -v`。

###### 启动开启 TLS 的多节点集群

参阅[在 Elasticsearch Docker 容器的加密通信](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/configuring-tls-docker.html)
和在 [Docker 中开启 TLS 运行 Elastic 栈](https://www.elastic.co/guide/en/elastic-stack-get-started/7.17/get-started-docker.html#get-started-docker-tls)
。

##### 在生产环境使用 Docker 镜像

以下要求和建议适用于生产环境中在 Docker 中运行 Elasticsearch。

###### 设置 `vm.max_map_count` 至少为 `262144`

在生产环境使用，`vm.max_map_count` 内核设置必须至少为 `262144`。

如何设置 `vm.max_map_count` 基于你的平台：

- Linux

查询 `vm.max_map_count` 当前的值设置，运行：

```shell
grep vm.max_map_count /etc/sysctl.conf
vm.max_map_count=262144
```

在运行的系统中应用此配置，执行：

```shell
sysctl -w vm.max_map_count=262144
```

要永久更改 `vm.max_map_count` 设置的值，请更新 `/etc/sysctl.conf` 中的值。

- 带 [Mac 版 Docker](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#_macos_with_docker_for_mac)的
  macOS

`vm.max_map_count` 设置必须在 xhyve 虚机中设置：

1. 从命令行运行：

```shell
screen ~/Library/Containers/com.docker.docker/Data/vms/0/tty
```

2. 按回车，使用 `sysctl` 配置 `vm.max_map_count`：

```shell
sysctl -w vm.max_map_count=262144
```

3. 退出 `screen` 会话，按 `Ctrl a d`

- 带 [Docker 桌面版](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#_windows_and_macos_with_docker_desktop) 的 Windows 和 macOS

`vm.max_map_count` 必须通过 docker-machine 设置。

```shell
docker-machine ssh
sudo sysctl -w vm.max_map_count=262144
```

- 带 [Docker WSL 2 后端桌面版](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#_windows_with_docker_desktop_wsl_2_backend) 的 Windows

`vm.max_map_count` 必须在 docker-desktop 容器中设置。

```shell
wsl -d docker-desktop
sysctl -w vm.max_map_count=262144
```

###### 配置文件必须可被用户 `elasticsearch` 用户读取

默认情况下，Elasticsearch 通过 `uid:gid 1000:0`，以用户 `elasticsearch` 在容器中运行。

> **警告**
>
> 一个例外是 [OpenShift](https://docs.openshift.com/container-platform/3.6/creating_images/guidelines.html#openshift-specific-guidelines)
，它使用任意分配的用户 ID 运行容器。OpenShift 显示的持久卷的 gid 设置为 `0`，它可以无需调整的运行。

如果你要绑定挂载本地目录或文件，它必须可被用户 `elasticsearch`
读取。此外，此用户对[配置、数据和日志目录](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#path-settings)
有写权限（Elasticsearch 需要对 `config` 目录有写权限，这样它才能生成密钥库）。一个好的策略是为本地目录 gid `0` 分配组访问权限。

例如，要准备本地目录以通过绑定挂载来存储数据，按以下操作：

```shell
mkdir esdatadir
chmod g+rwx esdatadir
chgrp 0 esdatadir
```

你也可以使用自定义 UID 和 GID 来运行 Elasticsearch 容器。你必须确保文件权限不会阻止Elasticsearch的执行。你可以使用以下两种选择之一：

- 绑定挂载每个 `config`、`data` 和 `logs`
  目录。如果你打算安装插件，而不想[创建自定义 Docker 镜像](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#_c_customized_image)
  ，则还必须绑定挂载 `plugins` 目录。
- 为 `docker run` 传递命令行选项 `--group-add 0`。这样可以确保运行 Elasticsearch 的用户也是容器 `root` （GID 0）组的成员。

最后，你还可以通过环境变量 `TAKE_FILE_OWNERSHIP`
强制容器更改用于[数据和日志目录](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#path-settings)
的绑定挂载的所有权。当你这样做的时候，它们将属于 uid:gid `1000:0`，它提供了 Elasticsearch 进程所需的读写访问权限。

###### 为 nofile 和 nproc 增加 ulimit

必须为 Elasticsearch
容器提供 [nofile](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/setting-system-settings.html)
和 [nproc](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/max-number-threads-check.html) 增加 ulimit。验证
Docker 的守护进程是否的 [init system](https://github.com/moby/moby/tree/ea4d1243953e6b652082305a9c3cda8656edab26/contrib/init)
是否将它们设置为可接受的值。

为了检测 Docker 守护进程默认的 ulimit，执行：

```shell
docker run --rm docker.elastic.co/elasticsearch/elasticsearch:{version} /bin/bash -c 'ulimit -Hn && ulimit -Sn && ulimit -Hu && ulimit -Su'
```

如果需要，在守护进程中调整他们，或者在每个容器中重载他们。例如，使用 `docker run` 时，设置：

```shell
--ulimit nofile=65535:65535
```

###### 禁用 swapping

为了提高性能和节点稳定性，swapping
需要禁用。有关执行此操作的更多信息，请参阅 [禁用 swapping](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/setup-configuration-memory.html)
。

如果你选择 `bootstrap.memory_lock: true`
，你也需要在 [Docker 守护进程](https://docs.docker.com/engine/reference/commandline/dockerd/#default-ulimits)中定义 `memlock: true`
限定，或者如[示例 compose 文件](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#docker-compose-file)
中显示的设置。当使用 `docker run`，你可以指定：

```shell
-e "bootstrap.memory_lock=true" --ulimit memlock=-1:-1
```

###### 随机发布端口

镜像[暴露](https://docs.docker.com/engine/reference/builder/#/expose) TCP 端口 9200 和 9300。对生产环境集群，推荐通过 `--publish-all`
随机发布端口，除非你为每个主机固定一个容器。

###### 手工设置堆大小

默认情况下，Elasticsearch 基于节点的[角色](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-node.html#node-roles)
和节点容器总可用内存，自动地设置 JVM 堆。对大多数生产环境，我们推荐默认大小设置。如果有需要，你可以通过手工设置 JVM 堆大小来重载默认设置。

为了在生产环境手工设置堆大小，绑定挂载包含了你期望的[堆大小](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/advanced-configuration.html#set-jvm-heap-size)
设置的 [JVM 选项](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/advanced-configuration.html#set-jvm-options)
文件（在 `/usr/share/elasticsearch/configuring_elasticsearchjvm.options.d` 中）。

用于测试的话，你可以通过环境变量 `ES_JAVA_OPTS` 手工设置堆大小。例如，要用 16 GB，通过 `docker run` 指定 `-e ES_JAVA_OPTS="-Xms16g -Xmx16g"`
。`ES_JAVA_OPTS` 重载所有其他 JVM 选项。在生产环境，我们不推荐使用 `ES_JAVA_OPTS`。上述的 `docker-compose.yml` 可以看到设置堆大小为 512 MB。

###### 部署固定为指定的镜像版本

将部署固定为指定的 Elasticsearch Docker 镜像。例如 `docker.elastic.co/elasticsearch/elasticsearch:7.17.9`。

###### 始终绑定数据卷

出于以下原因，你应该对 `/usr/share/elasticsearch/data` 使用卷绑定：

1. 如果容器被杀死，Elasticsearch 节点数据不会丢失
2. Elasticsearch 对 I/O 敏感，而 Docker 存储驱动不适合快速 I/O
3. 允许使用高级 [Docker 卷插件](https://docs.docker.com/engine/extend/plugins/#volume-plugins)

###### 禁止使用 `loop-lvm` 模式

如果你正在使用 devicemapper 存储驱动，不要使用默认的 `loop-lvm` 模式。配置 docker-engine
以使用 [direct-lvm](https://docs.docker.com/engine/userguide/storagedriver/device-mapper-driver/#configure-docker-with-devicemapper)
。

###### 集中你的日志

考虑使用不同的[日志驱动](https://docs.docker.com/engine/admin/logging/overview/)来集中日志。还要注意，默认的 json-file 日志驱动不适合生产环境。

##### 使用 Docker 配置 Elasticsearch

当你在 Docker
中运行时， [Elasticsearch 配置文件](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#config-files-location)
从 `/usr/share/elasticsearch/config/` 加载。

为了使用自定义配置文件，你要[绑定挂载文件](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#docker-config-bind-mount)
到镜像中的配置文件上。

你可以通过环境变量设置独立的 Elasticsearch
配置参数。[示例 compose 文件](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#docker-compose-file)
和[单节点示例](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#docker-cli-run-dev-mode)
就用的这种方法。你可以直接使用设置名称作为环境变量名称。如果你不能这样做，例如因为你的编制平台禁止在环境变量名称中使用句点，那么你可以使用另一种样式，按以下方式转换设置名称。

1. 将设置名称更改为大写字母
2. 以 `ES_SETTING_` 为前缀
3. 通过复制转义任何下划线(`_`)
4. 将所有句点(`.`)转换为下划线(`_`)

例如，`-e bootstrap.memory_lock=true` 变为 `-e ES_SETTING_BOOTSTRAP_MEMORY__LOCK=true`。

你可以使用文件的内容来设置 `ELASTIC_PASSWORD` 或 `KEYSTORE_PASSWORD` 环境变量的值，方法是在环境变量名后面加上 `_FILE` 。这对于秘密传输配置（如密码）给
Elasticsearch，而不是直接指定它们非常有用。

例如，为了从文件设置 Elasticsearch 的启动密码，你可以绑定挂载这个文件，然后在挂载路径中设置环境变量 `ELASTIC_PASSWORD_FILE`
。如果你挂载的密码文件为 `/run/secrets/bootstrapPassword.txt`，如下指定：

```shell
-e ELASTIC_PASSWORD_FILE=/run/secrets/bootstrapPassword.txt
```

你还可以通过传递 Elasticsearch 配置参数作为命令行选项，来重载默认的命令。例如：

```shell
docker run <various parameters> bin/elasticsearch -Ecluster.name=mynewclustername
```

虽然绑定挂载配置文件通常在生产环境是首选方法，你也可以创建包含你自己配置的[自定义 Docker 镜像](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#_c_customized_image)
。

###### 挂载 Elasticsearch 配置文件

创建自定义配置文件，将其绑定挂载到 Docker 镜像的相应文件上。例如，使用 `docker run` 绑定挂载 `custom_elasticsearch.yml`，如下指定：

```shell
-v full_path_to/custom_elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml
```

如果绑定挂载自定义 `elasticsearch.yml` 文件，请确保它包含 `network.host:0.0.0.0.0` 设置。此设置确保节点可以访问 HTTP 和传输流量，前提是其端口是公开的。Docker
镜像的内置 `elasticsearch.yml` 文件默认包含此设置。

> **警告**
>
> 容器以用户 `elasticsearch`，使用 `uid:gid 1000:0` 运行 Elasticsearch。绑定挂载的主机目录和文件，必须能被此用户访问，且数据和日志目录必须能被此用户写入。

###### 挂载 Elasticsearch 密钥库

默认情况下，Elasticsearch 会为[安全设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/secure-settings.html)
自动生成密钥库文件。这个文件是混淆的，但没有加密。

要使用密码加密你的安全设置，并让它们在容器之外持久存在，请使用 `docker run` 命令手动创建密钥存储库。命令必须：

- 绑定装载 `config` 目录。该命令将在此目录中创建一个 `elasticsearch.keystore` 文件。为了避免错误，不要直接绑定挂载 `elasticsearch.keystore` 文件。
- 使用带有 `create -p` 选项的 `elasticsearch-keystore` 工具。系统将提示你输入密钥库的密码。

例如：

```shell
docker run -it --rm \
-v full_path_to/config:/usr/share/elasticsearch/config \
docker.elastic.co/elasticsearch/elasticsearch:7.17.9 \
bin/elasticsearch-keystore create -p
```

还可以使用 `docker run` 命令在密钥存储库中添加或更新安全设置。系统将提示你输入设置值。如果加密了密钥存储库，还会提示你输入密钥存储库密码。

```shell
ocker run -it --rm \
-v full_path_to/config:/usr/share/elasticsearch/config \
docker.elastic.co/elasticsearch/elasticsearch:7.17.9 \
bin/elasticsearch-keystore \
add my.secure.setting \
my.other.secure.setting
```

如果你已经创建了密钥库，并且不需要更新它，那么可以直接绑定挂载 `elasticsearch.keystore` 文件。你可以使用 `KEYSTORE_PASSWORD` 环境变量在启动时为容器提供密钥库密码。例如，`docker run`
命令可能具有以下选项：

```shell
-v full_path_to/config/elasticsearch.keystore:/usr/share/elasticsearch/config/elasticsearch.keystore
-e KEYSTORE_PASSWORD=mypassword
```

###### 使用自定义 Docker 镜像

在某些环境中，准备一个包含你的配置的自定义镜像可能更有意义。实现这一点的 `Dockerfile` 可能非常简单：

```shell
FROM docker.elastic.co/elasticsearch/elasticsearch:7.17.9
COPY --chown=elasticsearch:elasticsearch elasticsearch.yml /usr/share/elasticsearch/config/
```

然后，你可以使用该工具构建和运行镜像：

```shell
docker build --tag=elasticsearch-custom .
docker run -ti -v /usr/share/elasticsearch/data elasticsearch-custom
```

有些插件需要额外的安全权限。你必须明确地接受它们：

- 在运行 Docker 镜像时附加 `tty`，并在提示时允许权限。
- 通过在插件安装命令中添加 `--batch` 标志来检查安全权限并接受它们(如果合适的话)。

更多信息请参见[插件管理](https://www.elastic.co/guide/en/elasticsearch/plugins/7.17/_other_command_line_parameters.html)。

##### 排查 Elasticsearch 的 Docker 错误

下面是如何解决使用 Docker 运行 Elasticsearch 时的常见错误。

###### elasticsearch.keystore 是一个目录

```shell
Exception in thread "main" org.elasticsearch.bootstrap.BootstrapException: java.io.IOException: Is a directory: SimpleFSIndexInput(path="/usr/share/elasticsearch/config/elasticsearch.keystore") Likely root cause: java.io.IOException: Is a directory
```

一个与[密钥库相关](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#docker-keystore-bind-mount)
的 `docker run` 命令试图直接绑定挂载一个不存在的 `elasticsearch.keystore` 文件。如果使用 `-v` 或 `--volume` 标志来装载不存在的文件，Docker 会创建一个同名目录。

要解决此错误：

- 删除 `config` 目录中的 `elasticsearch.keystore` 目录。
- 更新 `-v` 或 `--volume`
  标志以指向配置目录路径，而不是密钥库文件的路径。有关示例，请参阅[创建加密的 Elasticsearch密钥库](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#docker-keystore-bind-mount)
  。
- 请重试该命令。

###### elasticsearch.keystore：设备或资源繁忙

```shell
Exception in thread "main" java.nio.file.FileSystemException: /usr/share/elasticsearch/config/elasticsearch.keystore.tmp -> /usr/share/elasticsearch/config/elasticsearch.keystore: Device or resource busy
```

`docker run` 命令试图在直接绑定挂载 `elasticsearch.keystore`
文件的同时[更新密钥库](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#docker-keystore-bind-mount)
。要更新密钥库，容器需要访问 config 目录中的其他文件，如 `keystore.tmp`。

要解决此错误：

- 更新 `-v` 或 `--volume` 标志以指向 `config`
  目录路径，而不是密钥库文件的路径。有关示例，请参阅[创建加密的 Elasticsearch 密钥库](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#docker-keystore-bind-mount)
  。
- 请重试该命令。

##### 下一步

你现在有一个测试 Elasticsearch 环境部署好。在你使用 Elasticsearch 正式开始开发或者生产之前，你必须做一些额外的设置：

- 学习如何[配置 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html)。
- 配置[重要的 Elasticsearch 设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html)。
- 配置[重要的系统设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/system-config.html)。

#### 使用 Homebrew 在 macOS 上安装 Elasticsearch

Elasticsearch 发布了 Homebrew formulae，所以你可以通过 [Homebrew](https://brew.sh/)包管理器安装 Elasticsearch。

要使用 Homebrew 安装，你要先连接上（tap） Elastic Homebrew 仓库：

```shell
brew tap elastic/tap
```

如果你已经连接到了 Elasticsearch Homebrew 仓库，你可以使用 `brew install` 安装 Elasticsearch：

```shell
brew install elastic/tap/elasticsearch-full
```

##### Homebrew 安装的目录结构

当你使用 `brew install` 安装 Elasticsearch，配置文件、日志和数据目录存储在以下位置。

| 类型      | 描述                                                          | 默认位置                                                    | 设置                                                                                                               |
|---------|-------------------------------------------------------------|---------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| home    | Elasticsearch 主目录或 `$ES_HOME`                               | `/usr/local/var/homebrew/linked/elasticsearch-full`     ||
| bin     | 二进制脚本，包括启动节点的 `elasticsearch` 和安装插件的 `elasticsearch-plugin` | `/usr/local/var/homebrew/linked/elasticsearch-full/bin` ||
| conf    | 配置文件，包括 `elasticsearch.yml`                                 | `/usr/local/etc/elasticsearch`                          | [ES_PATH_CONF](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#config-files-location) |
| data    | 分配在节点上的每个索引和分片的数据文件位置。                                      | `/usr/local/var/lib/elasticsearch`                      | `path.data`                                                                                                      |
| logs    | 日志文件位置                                                      | `/usr/local/var/log/elasticsearch`                      | `path.logs`                                                                                                      |
| plugins | 插件文件位置。每个插件会包含在一个子目录中。                                      | `/usr/local/var/homebrew/linked/elasticsearch/plugins`  ||

##### 下一步

你现在有一个测试 Elasticsearch 环境部署好。在你使用 Elasticsearch 正式开始开发或者生产之前，你必须做一些额外的设置：

- 学习如何[配置 Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html)。
- 配置[重要的 Elasticsearch 设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html)。
- 配置[重要的系统设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/system-config.html)。

## 配置 Elasticsearch

Elasticsearch 具有良好的默认值，只需要很少的配置。可以使用[集群更新设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/cluster-update-settings.html) API 在运行的集群上更改大多数设置。

配置文件应包含特定于节点的设置（如 `node.name` 和路径），或节点加入群集所需的设置，如 `cluster.name` 和 `network.host`。

1. 配置文件位置

Elasticsearch有三个配置文件：

- `elasticsearch.yml` 用于配置 Elasticsearch
- `jvm.options` 用于配置 Elasticsearch JVM 设置
- `log4j2.properties` 用于配置 Elasticsearch 日志记录

这些文件位于 config 目录中，其默认位置取决于安装是来自存档发行版（`tar.gz` 或 `zip`）还是软件包发行版（Debian 或 RPM 软件包）。

对于压缩分发版，配置目录位置默认为 `$ES_HOME/config`。可以通过 `ES_PATH_CONF` 环境变量更改配置目录的位置，如下所示：

```shell
ES_PATH_CONF=/path/to/my/config ./bin/elasticsearch
```

或者，你可以通过命令行或 shell 概要文件导出 `ES_PATH_CONF` 环境变量。

对于包发行版，配置目录位置默认为 `/etc/elasticsearch`。还可以通过 `ES_PATH_CONF` 环境变量更改配置目录的位置，但请注意，在 shell
中设置此值是不够的。相反，这个变量来源于 `/etc/default/elasticsearch`（对于 Debian 包）和 `/etc/sysconfig/elasticsearch`（对于 RPM
包）。你需要相应地编辑其中一个文件中的 `ES_PATH_CONF=/etc/elasticsearch` 条目，以更改配置目录位置。

2. 配置文件格式

配置格式为 [YAML](https://yaml.org/)。以下是更改数据和日志目录路径的示例：

```yaml
path:
  data: /var/lib/elasticsearch
  logs: /var/log/elasticsearch
```

还可以按如下方式展平设置：

```yaml
path.data: /var/lib/elasticsearch
path.logs: /var/log/elasticsearch
```

在 YAML 中，可以将非标量值格式化为序列：

```yaml
discovery.seed_hosts:
  - 192.168.1.10:9300
  - 192.168.1.11
  - seeds.mydomain.com
```

虽然不太常见，但也可以将非标量值格式化为数组：

```yaml
discovery.seed_hosts: [ "192.168.1.10:9300", "192.168.1.11", "seeds.mydomain.com" ]
```

3. 环境变量替代

配置文件中使用 `${...}` 符号引用的环境变量将替换为环境变量的值。例如：

```yaml
node.name: ${HOSTNAME}
network.host: ${ES_NETWORK_HOST}
```

环境变量的值必须是简单字符串。使用逗号分隔的字符串提供 Elasticsearch 将解析为列表的值。例如，Elasticsearch 将以下字符串拆分为 `${HOSTNAME}` 环境变量的值列表：

```shell
export HOSTNAME="host1,host2"
```

4. 集群和节点设置类型

集群和节点设置可以根据配置方式进行分类：

- 动态

你可以使用[集群更新设置 API](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/cluster-update-settings.html)
在正在运行的集群上配置和更新动态设置。你还可以使用 `elasticsearch.yml` 在未启动或关闭的节点上本地配置动态设置。

使用集群更新设置 API 进行的更新可以是持久的，适用于跨集群重新启动；也可以是瞬时的，在集群重新启动后重置。你还可以通过使用 API 为瞬态或持久设置分配 `null` 来重置它们。

如果你使用多个方法配置同一设置，Elasticsearch 将按以下优先顺序应用设置：

    1. 瞬态设置
    2. 持续设置
    3. `elasticsearch.yml` 设置
    4. 默认设置值

例如，可以应用瞬态设置来覆盖持久设置或 `elasticsearch.yml` 设置。但是，更改 `elasticsearch.yml` 设置不会覆盖已定义的瞬态或持久设置。

> **注意**
>
> 如果使用 Elasticsearch 服务，请使用[用户设置](https://www.elastic.co/guide/en/cloud/current/ec-add-user-settings.html)
> 功能配置所有集群设置。此方法允许 Elasticsearch 服务自动拒绝可能破坏集群的不安全设置。
>
> 如果你在自己的硬件上运行 Elasticsearch，请使用[集群更新设置 API](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/cluster-update-settings.html)
> 配置动态集群设置。仅使用 `elasticsearch.yml` 用于静态集群设置和节点设置。API 不需要重新启动，并确保设置的值在所有节点上都相同。

> **警告**
>
> 我们不再建议使用临时集群设置。请改用永久集群设置。如果集群变得不稳定，瞬态设置可能会意外清除，从而导致可能不需要的集群配置。参阅[瞬态设置迁移指南](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/transient-settings-migration-guide.html)。

- 静态

只能使用 `elasticsearch.yml` 在未启动或关闭的节点上配置静态设置。

必须在集群中的每个相关节点上设置静态设置。

### 重要的 Elasticsearch 配置

Elasticsearch 只需要很少的配置即可开始，但在生产中使用集群之前，**必须**考虑以下几点：

- [路径设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#path-settings)
- [集群名设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#cluster-name)
- [节点名设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#node-name)
- [网络主机设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#network.host)
- [发现设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#discovery-settings)
- [堆大小设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#heap-size-settings)
- [JVM 堆转储路径设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#heap-dump-path)
- [GC 日志设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#gc-logging)
- [临时目录设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#es-tmpdir)
- [JVM 致命错误日志设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#error-file-path)
- [集群备份](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#important-settings-backups)

我们的[Elastic Cloud](https://www.elastic.co/cloud/elasticsearch-service/signup?baymax=docs-body&elektra=docs)
服务自动配置这些项目，默认情况下，使你的集群生产就绪。

#### 路径设置

Elasticsearch 将索引的数据写入索引，并将数据流写入 `data` 目录。Elasticsearch 将自己的应用程序日志写入 `logs` 目录，其中包含有关集群运行状况和操作的信息。

对于 `macOS .tar.gz`，`Linux .tar.gz` 和 `Windows .zip` 安装，`data` 和 `logs` 默认是 `$ES_HOME` 的子目录。然而，`$ES_HOME`
中的文件可能在升级过程中被删除。

在生产中，我们强烈建议你设置 `elasticsearch.yml` 中的 `path.data` 和 `path.logs` 在 `$ES_HOME` 之外的位置。默认情况下，`Docker`、`Debian` 和 `RPM`
安装将数据和日志写入 `$ES_HOME` 之外的位置。

支持的 `path.data` 和 `path.logs`。值因平台而异：

- 类 Unix 系统

Linux 和 macOS 安装支持 Unix 风格的路径：

```yaml
path:
  data: /var/data/elasticsearch
  logs: /var/log/elasticsearch
```

- Windows

Windows 安装支持带转义反斜杠的 DOS 路径：

```yaml
path:
  data: "C:\\Elastic\\Elasticsearch\\data"
  logs: "C:\\Elastic\\Elasticsearch\\logs"
```

> **警告**
>
> 不要修改数据目录中的任何内容或运行可能会干扰其内容的进程。如果 Elasticsearch 以外的其他内容修改了数据目录的内容，则 Elasticsearch
> 可能会失败，报告损坏或其他数据不一致，或者可能在默默丢失部分数据后正常工作。不要尝试对数据目录进行文件系统备份；不支持还原此类备份的方法。相反，请使用[快照和还原](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/snapshot-restore.html)
> 来安全地进行备份。不要在数据目录上运行病毒扫描程序。病毒扫描程序可能会阻止 Elasticsearch 正常工作，并可能会修改数据目录的内容。数据目录不包含可执行文件，因此病毒扫描只会发现误报。

#### 多数据路径

> **警告**
>
> 7.13.0 版本已弃用

如果需要，可以在 `path.data` 中指定多个路径。Elasticsearch 跨所有提供的路径存储节点的数据，但将每个分片的数据保持在同一路径上。

Elasticsearch 不平衡节点数据路径上的分片。单个路径中的高磁盘使用率可以触发整个节点的[高磁盘利用率水印](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-cluster.html#disk-based-shard-allocation)
。如果触发，Elasticsearch 将不会向节点添加分片，即使节点的其他路径具有可用磁盘空间。如果需要额外的磁盘空间，建议你添加新节点，而不是其他数据路径。

- 类 Unix 系统

Linux 和 macOS 安装支持 `path.data` 中的多个 Unix 风格路径：

```yaml
path:
  data:
    - /mnt/elasticsearch_1
    - /mnt/elasticsearch_2
    - /mnt/elasticsearch_3
```

- Windows

Windows 安装支持 `path.data` 中的多个 DOS 路径：

```yaml
path:
  data:
    - "C:\\Elastic\\Elasticsearch_1"
    - "E:\\Elastic\\Elasticsearch_1"
    - "F:\\Elastic\\Elasticsearch_3"
```

#### 从多个数据路径迁移

支持多数据路径在 7.13 中已被弃用，并将在未来版本中删除。

作为多数据路径的替代方案，你可以使用硬件虚拟化层（如 RAID）或软件虚拟化层，如 Linux 上的逻辑卷管理器（LVM）或 Windows
上的存储空间，创建跨越多个磁盘的文件系统。如果希望在一台计算机上使用多个数据路径，则必须为每个数据路径运行一个节点。

如果当前在[高可用集群](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/high-availability-cluster-design.html)
中使用多个数据路径，则可以迁移到为每个节点使用单一路径的设置，而无需使用类似于[滚动重启](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/restart-cluster.html#restart-cluster-rolling)
的过程进行停机：依次关闭每个节点，并将其替换为一个或多个节点，每个节点都配置为使用单一数据路径。更详细地说，对于当前具有多个数据路径的每个节点，应遵循以下过程。原则上，你可以在滚动升级到 8.0
期间执行此迁移，但我们建议在开始升级之前迁移到单个数据路径设置。

1. 创建快照以在发生灾难时保护你的数据。

2. 可选地，通过使用[分配过滤器](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-cluster.html#cluster-shard-allocation-filtering)
将数据从目标节点迁移：

```text
PUT _cluster/settings
{
  "persistent": {
    "cluster.routing.allocation.exclude._name": "target-node-name"
  }
}
```

你可以使用 [cat 分配 API](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/cat-allocation.html)
跟踪数据迁移的进度。如果一些分片没有迁移，那么[集群分配解释 API](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/cluster-allocation-explain.html)
将帮助你确定原因。

3. 按照[滚动重启](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/restart-cluster.html#restart-cluster-rolling)
   过程中的步骤操作，直到关闭目标节点。

4. 确保集群运行状况为 `yellow`（黄色）或 `green`（绿色），以便有分配给集群中至少一个其他节点的每个分片的副本。

5. 如果适用，请删除前面步骤中应用的分配筛选器。

```text
PUT _cluster/settings
{
  "persistent": {
    "cluster.routing.allocation.exclude._name": null
  }
}
```

6. 通过删除已停止节点的数据路径的内容，丢弃已停止节点持有的数据。

7. 重新配置存储。例如，使用 LVM 或存储空间将磁盘合并到单个文件系统中。确保重新配置的存储具有足够的空间来存放数据。

8. 通过调整 `elasticsearch.yml` 文件中的 `path.data` 设置重新配置节点。如果需要，安装更多节点，每个节点都有自己的 `path.data` 设置指向单独数据路径。

9. 启动新节点，并对其执行其余的[滚动重启过程](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/restart-cluster.html#restart-cluster-rolling)。

10. 确保集群运行状况为 `green`，以便已分配每个分片。

你也可以向集群添加一些单数据路径节点，使用[分配过滤器](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-cluster.html#cluster-shard-allocation-filtering)
将所有数据迁移到这些新节点，然后从集群中删除旧节点。这种方法将使集群的大小临时增加一倍，因此只有在你有能力这样扩展集群的情况下，它才能工作。

如果你当前使用多个数据路径，但你的群集不是高度可用的，则可以通过创建快照，再创建具有所需配置的新集群并将快照还原到其中，用以迁移到非弃用配置。

#### 集群名设置

节点只能在加入集群后，向集群中其他所有节点共享其 `cluster.name`。默认名称是 `elasticsearch`，但你应该将其更改为描述集群用途的适当名称。

```yaml
cluster.name: logging-prod
```

> **重要**
>
> 不要在不同的环境中重用相同的集群名称。否则，节点可能加入错误的集群。

#### 节点名设置

Elasticsearch 使用 `node.name` 作为 Elasticsearch 特定实例的可读标识符。此名称包含在许多 API 的响应中。节点名称默认为 Elasticsearch
启动时机器的主机名，但可以在 `elasticsearch.yml` 中显式配置：

```yaml
node.name: prod-data-2
```

#### 网络主机设置

默认情况下，Elasticsearch 仅绑定到回环地址，如 `127.0.0.1` 和 `[::1]`
。这足以在单个服务器上运行一个或多个节点的集群进行开发和测试，但[弹性生产集群](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/high-availability-cluster-design.html)
必须涉及其他服务器上的节点。有许多[网络设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-network.html)
，但通常只需要配置 `network.host`：

```yaml
network.host: 192.168.1.10
```

> **重要**
>
> 当你提供 `network.host` 值，Elasticsearch
> 假设你正在从开发模式转移到生产模式，并将大量系统启动检查从警告升级为异常。查看[开发和生产模式](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/system-config.html#dev-vs-prod)
> 之间的差异。

#### 发现和集群构成设置

在投入生产之前，配置两个重要的发现和群集形成设置，以便群集中的节点可以相互发现并选择主节点。

##### `discovery.seed_hosts`

开箱即用，无需任何网络配置，Elasticsearch 将绑定到可用的环回地址，并扫描本地端口 `9300` 至 `9305`，以与运行在同一服务器上的其他节点连接。这种行为提供了一种无需进行任何配置的自动集群体验。

当你希望与其他主机上的节点组成集群时，请使用[静态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#static-cluster-setting) `discovery.seed_hosts`
设置。此设置提供群集中其他节点的列表，这些节点具有主资格，并且可能处于活动状态，并且可以联系以启动[发现进程](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-discovery-hosts-providers.html)
。此设置接受集群中所有主合格节点的 YAML 序列或地址数组。每个地址可以是 IP 地址，也可以是通过 DNS 解析为一个或多个 IP 地址的主机名。

```yaml
discovery.seed_hosts:
   - 192.168.1.10:9300
   - 192.168.1.11         ①
   - seeds.mydomain.com   ②
   - [0:0:0:0:0:ffff:c0a8:10c]:9301 ③
```

① 端口可选，默认为 9300，但可以被[覆盖](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-discovery-hosts-providers.html#built-in-hosts-providers)。

② 如果主机名解析为多个 IP 地址，则节点将尝试在所有解析的地址发现其他节点。

③ IPv6 地址必须用方括号括起来。

如果符合主节点条件的节点没有固定名称或地址，请使用[备用主机提供](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-discovery-hosts-providers.html#built-in-hosts-providers)程序动态查找其地址。

##### `cluster.initial_master_nodes`

首次启动 Elasticsearch 集群时，[群集引导](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-discovery-bootstrap-cluster.html)步骤将确定在第一次选举中计算其投票的主合格节点集。在[开发模式](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/bootstrap-checks.html#dev-vs-prod-mode)下，如果未配置发现设置，此步骤将由节点自己自动执行。

由于自动引导[原生不安全](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-discovery-quorums.html)，因此在生产模式下启动新集群时，必须明确列出在第一次选举中应计算其投票的主合格节点。你可以使用 `cluster.initial_master_nodes` 设置此列表。

> **重要**
>
> 集群首次成功形成后，删除每个节点配置中的 `cluster.initial_master_nodes` 设置。重新启动集群或向现有集群添加新节点时，不要使用此设置。

```yaml
discovery.seed_hosts:
   - 192.168.1.10:9300
   - 192.168.1.11
   - seeds.mydomain.com
   - [0:0:0:0:0:ffff:c0a8:10c]:9301
cluster.initial_master_nodes:  ①
   - master-node-a
   - master-node-b
   - master-node-c
```

① 按他们的 [`node.name`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#node-name) 标识初始主节点，默认为其主机名。确保 `cluster.initial_master_nodes` 中的值与 `node.name` 完全匹配。如果使用完全限定域名（FQDN），例如 `master-node-a.example.com` 作为节点名称，则必须使用此列表中的 FQDN。相反，如果 `node.name` 是一个没有任何尾部限定符的裸主机名，你还必须省略 `cluster.initial_master_nodes` 中的尾部限定符。

参阅[引导集群](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-discovery-bootstrap-cluster.html)以及[发现和集群构成设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-discovery-settings.html)。

#### 堆大小设置

默认情况下，Elasticsearch 根据节点的[角色](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-node.html#node-roles)和总内存自动设置 JVM 堆大小。我们建议大多数生产环境使用默认大小。

> **注意**
>
> 自动堆大小调整需要[捆绑的 JDK](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/setup.html#jvm-version)，如果使用自定义 JRE 位置，则需要 Java 14 或更高版本的 JRE。

如果需要，可以通过[手动设置 JVM 堆大小](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/advanced-configuration.html#set-jvm-heap-size)来覆盖默认大小。

#### JVM 堆转储路径设置

默认情况下，Elasticsearch 将 JVM 配置为将内存不足异常的堆转储到默认数据目录。在 [RPM](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/rpm.html) 和 [Debian](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/deb.html) 软件包中，数据目录是 `/var/lib/elasticsearch`。在 [Linux 和 MacOS](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/targz.html) 及 [Windows](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/zip-windows.html) 发行版上，`data` 目录位于 Elasticsearch 安装的根目录下。

如果此路径不适合接收堆转储，请修改 [`jvm.options`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/advanced-configuration.html#set-jvm-options) 中的 `-XX:HeapDumpPath=...` 条目：

- 如果指定目录，JVM 将根据运行实例的 PID 为堆转储生成文件名。

- 如果指定固定文件名而不是目录，则当 JVM 需要对内存不足异常执行堆转储时，该文件必须不存在。否则，堆转储将失败。

#### GC 日志设置

默认情况下，Elasticsearch 启用垃圾收集（GC）日志。这些是在 [`jvm.options`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/advanced-configuration.html#set-jvm-options) 中配置的，并输出到与 Elasticsearch 日志相同的默认位置。默认配置每 64 MB 旋转一次日志，最多可消耗 2 GB 的磁盘空间。

你可以使用 [JEP 158：统一 JVM 日志](https://openjdk.java.net/jeps/158)中描述的命令行选项重新配置 JVM 日志。除非更改默认 `jvm.options` 文件中，除了你自己的设置之外，还将应用 Elasticsearch 默认配置。要禁用默认配置，首先通过提供 `-Xlog:disable` 选项禁用日志记录，然后提供你自己的命令行选项。这将禁用所有 JVM 日志记录，因此请确保查看可用选项并启用所需的所有功能。

要查看原始 JEP 中未包含的其他选项，参阅使用 [JVM 统一日志框架启用日志](https://docs.oracle.com/en/java/javase/13/docs/specs/man/java.html#enable-logging-with-the-jvm-unified-logging-framework)。

#### 示例

使用一些示例选项，通过创建 `$ES_HOME/config/jvm.options.d/gc.options` 来变更默认 GC 日志输出路径为 `/opt/my-app/gc.log`：

```shell
# Turn off all previous logging configuratons
-Xlog:disable

# Default settings from JEP 158, but with `utctime` instead of `uptime` to match the next line
-Xlog:all=warning:stderr:utctime,level,tags

# Enable GC logging to a custom location with a variety of options
-Xlog:gc*,gc+age=trace,safepoint:file=/opt/my-app/gc.log:utctime,pid,tags:filecount=32,filesize=64m
```

配置 Elasticsearch [Docker 容器](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html)，将 GC 调试日志发送到标准错误（`stderr`）。这允许容器编排器处理输出。如果使用 `ES_JAVA_OPTS` 环境变量，请指定：

```shell
MY_OPTS="-Xlog:disable -Xlog:all=warning:stderr:utctime,level,tags -Xlog:gc=debug:stderr:utctime"
docker run -e ES_JAVA_OPTS="$MY_OPTS" # etc
```

#### 临时目录设置

默认情况下，Elasticsearch 使用启动脚本在系统临时目录下创建的私有临时目录。

在某些 Linux 发行版上，如果文件和目录最近未被访问，系统实用程序将从 `/tmp` 中清除这些文件和目录。如果长时间未使用需要临时目录的功能，则此行为可能导致在 Elasticsearch 运行时删除专用临时目录。如果随后使用需要此目录的功能，则删除专用临时目录会导致问题。

如果使用 `.deb` 或 `.rpm` 软件包安装 Elasticsearch 并在 `systemd` 下运行，Elasticsearch 使用的私有临时目录将被排除在定期清理之外。

如果你打算运行 `.tar.gz` 在 Linux 或 MacOS 上的长期发行版，请考虑为 Elasticsearch 创建一个专用的临时目录，该目录不在将清除旧文件和目录的路径下。该目录应设置权限，以便只有运行 Elasticsearch 的用户才能访问该目录。然后，在启动 Elasticsearch 之前，将 `$ES_TMPDIR` 环境变量设置为指向该目录。

#### JVM 致命错误日志设置

默认情况下，Elasticsearch 将 JVM 配置为将致命错误日志写入默认日志目录。在 [RPM](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/rpm.html) 和 [Debian](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/deb.html) 软件包中，这个目录是 `/var/log/elasticsearch`。在 [Linux 和 MacOS](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/targz.html) 及 [Windows](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/zip-windows.html) 发行版上，`logs` 目录位于 Elasticsearch 安装的根目录下。

这些是 JVM 遇到致命错误（如分段错误）时生成的日志。如果此路径不适合接收日志，请修改 [`jvm.options`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/advanced-configuration.html#set-jvm-options) 中的 `-XX:ErrorFile=...` 条目。

#### 集群备份

在灾难中，[快照](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/snapshot-restore.html)可以防止永久数据丢失。[快照生命周期管理](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/snapshots-take-snapshot.html#automate-snapshots-slm)是对集群进行定期备份的最简单方法。有关详细信息，参阅[创建快照](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/snapshots-take-snapshot.html)。

> **警告**
> 
> **快照是备份集群的唯一可靠且受支持的方法**。你无法通过复制 Elasticsearch 集群节点的数据目录来备份该集群。不支持从文件系统级备份中恢复任何数据的方法。如果尝试从这样的备份中恢复集群，它可能会失败，并报告损坏或丢失文件或其他数据不一致，或者它似乎成功又悄悄地丢失了一些数据。

### 安全设置

有些设置是敏感的，依靠文件系统权限来保护它们的值是不够的。对于这个用例，Elasticsearch 提供一个密钥存储库和 [`elasticsearch-keystore` 工具](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/elasticsearch-keystore.html)来管理密钥存储库中的设置。

> **重要**
> 
> 只有一些设置被设计为从密钥库中读取。但是，密钥库没有验证来阻止不支持的设置。向密钥库中添加不支持的设置将导致 Elasticsearch 启动失败。要查看密钥存储库中是否支持某个设置，请在设置引用中查找 “Secure” 限定符。

所有对密钥库的修改只有在重新启动 Elasticsearch 后才会生效。

这些设置，就像配置文件 `elasticsearch.yml` 中的常规设置一样，需要在集群中的每个节点上指定。目前，所有安全设置都是特定于节点的设置，必须在每个节点上具有相同的值。

#### 可重新加载的安全设置

就像 `elasticsearch.yml` 中的设置值一样中，对密钥存储库内容的更改不会自动应用到正在运行的 Elasticsearch 节点。重新读取设置需要重新启动节点。但是，某些安全设置被标记为 **reloadable**（可重新加载）。这些设置可以[重新读取并应用到运行中的节点上](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/cluster-nodes-reload-secure-settings.html)。

所有安全设置的值(无论是否 **reloadable**（可重新加载）)，必须在所有集群节点上相同。在完成所需的安全设置更改后，使用 `bin/elasticsearch-keystore add` 命令，调用：

```text
POST _nodes/reload_secure_settings
{
  "secure_settings_password": "keystore-password"  ①
}
```

① Elasticsearch 密钥存储库使用的加密密码。

该 API 在每个集群节点上解密并重新读取整个密钥存储库，但只应用 **reloadable** 的安全设置。对其他设置的更改直到下一次重启才生效。一旦调用返回，重新加载就完成了，这意味着依赖于这些设置的所有内部数据结构都已被更改。一切都应该看起来好像设置从一开始就有了新的值。

在更改多个 **reloadable** 的安全设置时，在每个集群节点上修改所有这些设置，然后发出 [`reload_secure_settings`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/cluster-nodes-reload-secure-settings.html) 调用，而不是在每次修改后重新加载。

有可重新加载的安全设置:

- [Azure 存储库插件](https://www.elastic.co/guide/en/elasticsearch/plugins/7.17/repository-azure-client-settings.html)
- [EC2 发现插件](https://www.elastic.co/guide/en/elasticsearch/plugins/7.17/discovery-ec2-usage.html#_configuring_ec2_discovery)
- [GCS 存储库插件](https://www.elastic.co/guide/en/elasticsearch/plugins/7.17/repository-gcs-client.html)
- [S3 存储库插件](https://www.elastic.co/guide/en/elasticsearch/plugins/7.17/repository-s3-client.html)
- [监控设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/monitoring-settings.html)
- [观察器设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/notification-settings.html)

### 审计安全设置

你可以使用[审计日志](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/enable-audit-logging.html)记录与安全相关的事件，例如身份验证失败、拒绝连接和数据访问事件。此外，通过 API 对安全配置的更改，例如创建、更新和删除[本机](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/native-realm.html)和[内置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/built-in-users.html)用户、[角色](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/security-api-put-role.html)、[角色映射](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/security-api-put-role-mapping.html)和 [API 键](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/security-api-create-api-key.html)也会被记录下来。

> **提示**
> 
> 审计日志仅在某些订阅级别上可用。欲了解更多信息，参阅 [https://www.elastic.co/subscriptions](https://www.elastic.co/subscriptions)

如果配置了，则必须在集群中的每个节点上设置审计设置。静态设置，例如 `xpack.security.audit.enabled`，必须在每个节点的 `elasticsearch.yml` 中配置。对于动态审计设置，使用[集群更新设置 API](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/cluster-update-settings.html) 确保所有节点上的设置相同。

#### 常规审计设置

- `xpack.security.audit.enabled`

([静态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#static-cluster-setting)) 设置为 `true`，启用对节点的审计。默认值为 `false`。这将把审计事件放在一个名为 `<clustername>_audit.json` 的专用文件中。

如果启用，则必须在 `elasticsearch.yml` 中配置此设置。在集群中的所有节点上使用。

#### 审计事件设置

可以通过使用以下设置来控制记录的事件和其他信息:

- `xpack.security.audit.logfile.events.include`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 指定要在审计输出中打印的[事件类型](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/audit-event-types.html)。此外，`_all` 可以用于详尽地审计所有事件，但通常不建议这样做，因为它会变得非常冗长。默认列表值包括：`access_denied`、`access_granted`、`anonymous_access_denied`、`authentication_failed`、`connection_denied`、`tampered_request`、`run_as_denied`、`run_as_granted`、`security_config_change`。

- `xpack.security.audit.logfile.events.exclude`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 从包含列表中排除指定[事件类型](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/audit-event-types.html)。这在事件的情况下很有用。包含设置包含特殊值 `_all`。默认是空列表。

- `xpack.security.audit.logfile.events.emit_request_body`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 指定是否将来自 REST 请求的完整请求体作为某些类型的审计事件的属性。此设置可用于[审计搜索查询](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/auditing-search-queries.html)。

默认值为 `false`，因此不打印请求体。

> **重要**
>
> 当在审计事件中包含请求体时，建议可能会以纯文本的形式审计敏感数据，即使所有安全 API(如更改用户密码的 API)在审计时都会过滤掉凭据。

#### 本地节点信息设置

- `xpack.security.audit.logfile.emit_node_name`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 是否在每个审计事件中包含[节点名](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#node-name)字段。默认值为 `false`。

- `xpack.security.audit.logfile.emit_node_host_address`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 在每个审计事件中是否包含节点的 IP 地址作为字段。默认值为 `false`。

- `xpack.security.audit.logfile.emit_node_host_name`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 是否在每个审计事件中包含节点的主机名作为字段。默认值为 `false`。

- `xpack.security.audit.logfile.emit_node_id`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 是否在每个审计事件中包含节点id字段。节点名称的值可能在管理员更改配置文件中的设置时发生变化，与[节点名称](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/important-settings.html#node-name)不同的是，节点 id 将在集群重新启动时保持不变，管理员不能更改它。缺省值为 `true`。

#### 审计日志文件事件忽略策略

以下设置影响[忽略策略](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/audit-log-ignore-policy.html)，这些策略支持对哪些审计事件打印到日志文件进行细粒度控制。具有相同策略名称的所有设置组合在一起形成单一策略。如果一个事件匹配任何策略的所有条件，它将被忽略并且不打印。大多数审计事件服从于忽略策略。唯一的例外是 `security_config_change` 类型的事件，它不能被过滤掉，除非完全[排除](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/auditing-settings.html#xpack-sa-lf-events-exclude)。

- `xpack.security.audit.logfile.events.ignore_filters.<policy_name>.users`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 用户名或通配符的列表。指定的策略将不会打印匹配这些值的用户的审计事件。

- `xpack.security.audit.logfile.events.ignore_filters.<policy_name>.realms`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 认证领域名称或通配符的列表。指定的策略将不会为这些域中的用户打印审计事件。

- `xpack.security.audit.logfile.events.ignore_filters.<policy_name>.actions`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 操作名或通配符的列表。操作名可以在审计事件的 `action` 字段中找到。指定的策略将不会为匹配这些值的操作打印审计事件。

- `xpack.security.audit.logfile.events.ignore_filters.<policy_name>.roles`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 角色名或通配符的列表。指定的策略将不会为具有这些角色的用户打印审计事件。如果用户有多个角色，其中一些角色不在策略中覆盖，则策略将不覆盖此事件。

- `xpack.security.audit.logfile.events.ignore_filters.<policy_name>.indices`

([动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)) 索引名或通配符的列表。当事件中的所有索引都匹配这些值时，指定的策略将不打印审计事件。如果事件涉及多个索引，其中一些不在策略覆盖范围内，则策略将不覆盖此事件。

### 断路器设置

Elasticsearch 包含多个断路器，用于防止操作导致 OutOfMemoryError。每个断路器都指定了它可以使用多少内存的限制。此外，还有一个父级断路器，它指定可以跨所有断路器使用的内存总量。

除非另有说明，否则可以使用 [集群更新设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/cluster-update-settings.html) API 在活动集群上动态更新这些设置。

有关断路器错误的信息，参阅[断路器错误](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/fix-common-cluster-issues.html#circuit-breaker-errors)。

#### 父断路器

父级断路器可以配置以下设置：

- `indices.breaker.total.use_real_memory`

（[静态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#static-cluster-setting)）确定父断路器是应考虑实际内存使用（`true`）还是仅考虑子断路器保留的内存量（`false`）。默认为 `true`。

- `indices.breaker.total.limit`

（[动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)）整体母断路器的启动限制。如果 `indices.breaker.total.use_real_memory` 为 `false`，则默认 JVM 堆为 70%。。如果 `indices.breaker.total.use_real_memory` 为 `true`，默认 JVM 堆为 95%。

#### 字段数据断路器

字段数据断路器估计将字段加载到[字段数据缓存](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-fielddata.html)所需的堆内存。如果加载字段会导致缓存超过预定义的内存限制，断路器将停止操作并返回错误。

- `indices.breaker.fielddata.limit`

（[动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)）现场数据断路器的限制。默认为 JVM 堆的 40%。

- `indices.breaker.fielddata.overhead`

（[动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)）将所有现场数据估计值相乘以确定最终估计值的常数。默认为 `1.03`。

#### 请求断路器

请求断路器允许 Elasticsearch 防止每个请求的数据结构（例如，请求期间用于计算聚合的内存）超过一定的内存量。

- `indices.breaker.request.limit`

（[动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)）请求断路器的限制，默认为 JVM 堆的 60%。

- `indices.breaker.request.overhead`

（[动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)）一个常数，所有请求估计都与之相乘以确定最终估计。默认为 `1`。

#### 动态请求断路器

动态请求断路器允许 Elasticsearch 限制传输或 HTTP 级别上所有当前活动传入请求的内存使用，使其不超过节点上的特定内存量。内存使用情况基于请求本身的内容长度。这个断路器还考虑到，不仅需要内存来表示原始请求，而且还需要内存作为一个结构化对象，由默认开销反映出来。

- `network.breaker.inflight_requests.limit`

（[动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)）运行中请求断路器的限制，默认为 JVM 堆的 100%。这意味着它受到为主断路器配置的限制的约束。

- `network.breaker.inflight_requests.overhead`

（[动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)）一个常数，所有动态请求估值都乘以该常数以确定最终估值。默认为 `2`。

#### 计数请求断路器

计数断路器允许 Elasticsearch 限制内存中保存的、请求完成时未释放的内容的内存使用。这包括 Lucene 段内存之类的东西。

- `indices.breaker.accounting.limit`

（[动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)）计数断路器的限制，默认为 JVM 堆的 100%。这意味着它受到为主断路器配置的限制的约束。

- `indices.breaker.accounting.overhead`

（[动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)）将所有计数估值相乘以确定最终估值的常数。默认为 `1`。

#### 脚本编译断路器

与以前基于内存的断路器略有不同，脚本编译断路器限制了一段时间内内联脚本编译的数量。

有关更多信息，请参阅[脚本](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-scripting-using.html)文档的 “首选参数” 部分。

- `script.max_compilations_rate`

（[动态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#dynamic-cluster-setting)）限制在特定间隔内允许编译的唯一动态脚本的数量。默认为 `150/5m`，即每 5 分钟 150 次。

#### 正则断路器

编写不好的正则表达式会降低集群的稳定性和性能。正则断路器限制了 [Painless 脚本中正则](https://www.elastic.co/guide/en/elasticsearch/painless/7.17/painless-regexes.html)的使用和复杂性。

- `script.painless.regex.enabled`

（[静态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#static-cluster-setting)）在 Painless 脚本中启用正则。允许以下值：

`limited (默认)`：启用正则，但使用 [`script.painles.regex.limit-factor`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/circuit-breaker.html#script-painless-regex-limit-factor) 集群设置限制复杂度。

`true`：启用正则并不限制复杂度。禁用正则断路器。

`false`：禁用正则。任何包含正则表达式的 Painless 脚本都会返回一个错误。

- `script.painless.regex.limit-factor`

（[静态](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/settings.html#static-cluster-setting)）限制 Painless 脚本中正则表达式可以考虑的字符数。Elasticsearch 通过将设置值乘以脚本输入的字符长度来计算此限制。

例如，输入 `foobarbaz` 的字符长度为 9。如果是 `script.painless.regex.limit-factor` 为 `6`，`foobarbaz` 上的正则表达式最多可以考虑 54（9 * 6）个字符。如果表达式超过此限制，则会触发正则断路器并返回错误。

Elasticsearch 仅在 [`script.painless.regex.enabled`](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/circuit-breaker.html#script-painless-regex-enabled) 为 `limited` 时应用此限制。

# 升级 Elasticsearch

# 索引模块

# 映射

# 文本分析

# 索引模板

# 数据流

# 提取管道

# 别名

# 搜索你的数据

# 查询 DSL

# 聚合

# EQL

# SQL

# 脚本

# 数据管理

# ILM：管理索引生命周期

# 自动扩展

# 集群监控

# 转换数据

# 设置集群实现高可用

# 快照和还原

# 安全

# 监控器

# 命令行工具

# 如何做？

# REST api

# 迁移手册

# 发布说明

# 依赖和版本

# 参考

- [https://www.elastic.co/guide/en/elasticsearch/reference/7.17/index.html](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/index.html)
- [https://elasticsearch.bookhub.tech/#%E6%84%9F%E8%B0%A2%E4%B8%8E%E6%94%AF%E6%8C%81](https://elasticsearch.bookhub.tech/#%E6%84%9F%E8%B0%A2%E4%B8%8E%E6%94%AF%E6%8C%81)
