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

[7.16](https://www.elastic.co/guide/en/elasticsearch/reference/7.16/release-highlights.html)
| [7.15](https://www.elastic.co/guide/en/elasticsearch/reference/7.15/release-highlights.html)
| [7.14](https://www.elastic.co/guide/en/elasticsearch/reference/7.14/release-highlights.html)
| [7.13](https://www.elastic.co/guide/en/elasticsearch/reference/7.13/release-highlights.html)
| [7.12](https://www.elastic.co/guide/en/elasticsearch/reference/7.12/release-highlights.html)
| [7.11](https://www.elastic.co/guide/en/elasticsearch/reference/7.11/release-highlights.html)
| [7.10](https://www.elastic.co/guide/en/elasticsearch/reference/7.10/release-highlights.html)
| [7.9](https://www.elastic.co/guide/en/elasticsearch/reference/7.9/release-highlights.html)
| [7.8](https://www.elastic.co/guide/en/elasticsearch/reference/7.8/release-highlights.html)
| [7.7](https://www.elastic.co/guide/en/elasticsearch/reference/7.7/release-highlights.html)
| [7.6](https://www.elastic.co/guide/en/elasticsearch/reference/7.6/release-highlights-7.6.0.html)
| [7.5](https://www.elastic.co/guide/en/elasticsearch/reference/7.5/release-highlights-7.5.0.html)
| [7.4](https://www.elastic.co/guide/en/elasticsearch/reference/7.4/release-highlights-7.4.0.html)
| [7.3](https://www.elastic.co/guide/en/elasticsearch/reference/7.3/release-highlights-7.3.0.html)
| [7.2](https://www.elastic.co/guide/en/elasticsearch/reference/7.2/release-highlights-7.2.0.html)
| [7.1](https://www.elastic.co/guide/en/elasticsearch/reference/7.1/release-highlights-7.1.0.html)
| [7.0](https://www.elastic.co/guide/en/elasticsearch/reference/7.0/release-highlights-7.0.0.html)

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

> 警告
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

> 警告
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

> 提示
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
> 时，你可以按[JVM 堆大小设置](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/advanced-configuration.html#set-jvm-heap-size)
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
