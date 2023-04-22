package com.olinonee.elasticsearch.biz;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.olinonee.elasticsearch.api.entity.User;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * es 案例测试类
 * <p>
 * 官方参考链接：<a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/connecting.html">https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/connecting.html</a>
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2023-04-03
 */
@SpringBootTest
public class ElasticSearchDemoTests {

    // 创建低级客户端
    private final RestClient restClient = RestClient.builder(
                    new HttpHost("localhost", 9200))
            .build();

    // 使用 Jackson 映射器创建传输层
    private final ElasticsearchTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper());

    // 创建 API 客户端
    private final ElasticsearchClient client = new ElasticsearchClient(transport);

    @Test
    void testConnect() throws IOException {
        // API 响应值，状态码 （1xx, 2xx 和 3xx 是 true 但是 4xx 是 false）
        final BooleanResponse ping = client.ping();
        System.out.println("ES 的 API 响应结果为：" + ping.value());
        closeClient();
    }

    @Test
    void testCreateIndex() throws IOException {
        final CreateIndexResponse createIndexResponse = client.indices().create(builder -> builder.index("order"));
        final boolean acknowledged = createIndexResponse.acknowledged();
        System.out.println("ES 的 API 创建索引的响应结果为：" + acknowledged);
        closeClient();
    }

    @Test
    void testGetIndex() throws IOException {
        final GetIndexResponse indexResponse = client.indices().get(builder -> builder.index("order"));
        final Map<String, IndexState> result = indexResponse.result();
        System.out.println("ES 的 API 获取索引的响应结果为：" + result);
    }

    @Test
    void testDeleteIndex() throws IOException {
        final DeleteIndexResponse deleteIndexResponse = client.indices().delete(builder -> builder.index("order"));
        final boolean acknowledged = deleteIndexResponse.acknowledged();
        System.out.println("ES 的 API 删除索引的响应结果为：" + acknowledged);
        closeClient();
    }

    @Test
    void testCreateDocument() throws IOException {
        final CreateResponse createResponse = client.create(builder ->
                builder.index("user")
                        .id("1002")
                        .document(new User("小芳", "女", "17712345678", 18)));
        System.out.println("ES 的 API 创建文档的响应结果为：" + createResponse.result());
        closeClient();
    }

    @Test
    void testQueryDocument() throws IOException {
        final GetResponse<User> getResponse = client.get(builder -> builder.index("user").id("1003"), User.class);
        System.out.println("ES 的 API 查询文档的响应结果为：" + getResponse.source());
        closeClient();
    }

    @Test
    void testUpdateDocument() throws IOException {
        // 使用集合封装需要修改的内容
        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("name", "小丽");

        final UpdateResponse<User> updateResponse = client.update(builder -> builder.index("user").id("1002").doc(userInfoMap), User.class);
        System.out.println("ES 的 API 修改文档的响应结果为：" + updateResponse.result());
        closeClient();
    }

    @Test
    void testDeleteDocument() throws IOException {
        final DeleteResponse deleteResponse = client.delete(builder -> builder.index("user").id("1003"));
        System.out.println("ES 的 API 删除文档的响应结果为：" + deleteResponse.result());
        closeClient();
    }

    @Test
    void testBatchAddDocument() throws IOException {
        // 构建一个批量数据集合
        List<BulkOperation> list = new ArrayList<>();
        list.add(new BulkOperation.Builder().create(
                        builder -> builder
                                .id("1003")
                                .document(new User("小强", "男", "17812345678", 30)))
                .build());
        list.add(new BulkOperation.Builder().create(
                        builder -> builder
                                .id("1004")
                                .document(new User("晓彤", "女", "18112345678", 18)))
                .build());
        list.add(new BulkOperation.Builder().create(
                        builder -> builder
                                .id("1005")
                                .document(new User("小李", "男", "15512345678", 65)))
                .build());
        // 调用 bulk 方法执行批量插入操作
        final BulkResponse bulkResponse = client.bulk(builder -> builder.index("user").operations(list));
        System.out.println("ES 的 API 批量添加文档的响应结果为：" + bulkResponse.items());
        closeClient();
    }

    @Test
    void testBatchDeleteDocument() throws IOException {
        // 构建一个批量数据集合
        List<BulkOperation> list = new ArrayList<>();
        list.add(new BulkOperation.Builder().delete(
                        builder -> builder
                                .id("1004"))
                .build());
        list.add(new BulkOperation.Builder().delete(
                        builder -> builder
                                .id("1005"))
                .build());
        // 调用 bulk 方法执行批量删除操作
        final BulkResponse bulkResponse = client.bulk(builder -> builder.index("user").operations(list));
        System.out.println("ES 的 API 批量删除文档的响应结果为：" + bulkResponse.items());
        closeClient();
    }

    @Test
    void testFullQueryDocument() throws IOException {
        final SearchResponse<User> searchResponse = client.search(builder -> builder.index("user").query(q -> q.matchAll(item -> item)), User.class);
        final HitsMetadata<User> hitsMetadata = searchResponse.hits();
        for (Hit<User> hit : hitsMetadata.hits()) {
            final User user = hit.source();
            System.out.println("user -> " + user);
        }
        assert hitsMetadata.total() != null;
        final long total = hitsMetadata.total().value();
        System.out.println("ES 的 API 全量查询的数据总数为：" + total);
        closeClient();
    }

    @Test
    void testPagingQueryDocument() throws IOException {
        final SearchResponse<User> searchResponse = client.search(builder -> builder.index("user")
                .query(q -> q.matchAll(item -> item))
                .from(1)
                .size(2), User.class);
        searchResponse.hits().hits().forEach(userHit -> System.out.println("user -> " + userHit.source()));
        assert searchResponse.hits().total() != null;
        System.out.println("ES 的 API 分页查询的数据为：" + searchResponse.hits().hits());
        closeClient();
    }

    @Test
    void testSortQueryDocument() throws IOException {
        final SearchResponse<User> searchResponse = client.search(builder -> builder.index("user")
                .query(q -> q.matchAll(item -> item))
                .sort(s -> s.field(f -> f.field("tel")
                        .order(SortOrder.Desc))), User.class);
        searchResponse.hits().hits().forEach(userHit -> System.out.println("user -> " + userHit.source()));
        assert searchResponse.hits().total() != null;
        System.out.println("ES 的 API 查询排序的数据为：" + searchResponse.hits().hits());
        closeClient();
    }

    @Test
    void testConditionQueryDocument() throws IOException {
        final SearchResponse<User> searchResponse = client.search(builder -> builder.index("user")
                .query(q -> q.matchAll(item -> item))
                .sort(s -> s.field(f -> f.field("tel").order(SortOrder.Desc)))
                .source(f -> f.filter(item -> item.includes("name", "tel").excludes("sex"))), User.class);
        searchResponse.hits().hits().forEach(userHit -> System.out.println("user -> " + userHit.source()));
        assert searchResponse.hits().total() != null;
        System.out.println("ES 的 API 条件查询的数据为：" + searchResponse.hits().hits());
        closeClient();
    }

    @Test
    void testCombinationMustQueryDocument() throws IOException {
        final SearchResponse<User> searchResponse = client.search(builder -> builder.index("user")
                .query(q -> q.bool(b -> b.must(m -> m.match(f -> f.field("sex").query("男")))
                        .must(m -> m.match(f -> f.field("name").query("小")))
                        .mustNot(m -> m.match(f -> f.field("sex").query("女"))))), User.class);
        searchResponse.hits().hits().forEach(userHit -> System.out.println("user -> " + userHit.source()));
        assert searchResponse.hits().total() != null;
        System.out.println("ES 的 API 组合 Must 查询的数据为：" + searchResponse.hits().hits());
        closeClient();
    }

    @Test
    void testCombinationShouldQueryDocument() throws IOException {
        final SearchResponse<User> searchResponse = client.search(builder -> builder.index("user")
                .query(q -> q.bool(b -> b.should(m -> m.match(f -> f.field("sex").query("男")))
                        .should(m -> m.match(f -> f.field("name").query("小"))))), User.class);
        searchResponse.hits().hits().forEach(userHit -> System.out.println("user -> " + userHit.source()));
        assert searchResponse.hits().total() != null;
        System.out.println("ES 的 API 组合 Should 查询的数据为：" + searchResponse.hits().hits());
        closeClient();
    }

    @Test
    void testRangeQueryDocument() throws IOException {
        // 范围查询，gte()表示取大于等于，gt()表示大于，lte()表示小于等于
        final SearchResponse<User> searchResponse = client.search(builder -> builder.index("user")
                .query(q -> q.range(r -> r.field("age").gte(JsonData.of(20)).lt(JsonData.of(45)))), User.class);
        searchResponse.hits().hits().forEach(userHit -> System.out.println("user -> " + userHit.source()));
        assert searchResponse.hits().total() != null;
        System.out.println("ES 的 API 范围查询的数据为：" + searchResponse.hits().hits());
        closeClient();
    }

    @Test
    void testFuzzyQueryDocument() throws IOException {
        // 模糊查询，fuzziness 它可以被设置为 “0”，“1”，“2” 或 “auto”。“auto”是推荐的选项，它会根据查询词的长度定义距离。
        final SearchResponse<User> searchResponse = client.search(builder -> builder.index("user")
                .query(q -> q.fuzzy(r -> r.field("name").value("小").fuzziness("auto"))), User.class);
        searchResponse.hits().hits().forEach(userHit -> System.out.println("user -> " + userHit.source()));
        assert searchResponse.hits().total() != null;
        System.out.println("ES 的 API 模糊查询的数据为：" + searchResponse.hits().hits());
        closeClient();
    }

    @Test
    void testHighlightQueryDocument() throws IOException {
        // 对查询的字段内容进行高亮显示，preTags-设置标签前缀，postTags-设置标签后缀
        final SearchResponse<User> searchResponse = client.search(s -> s.index("user").query(q -> q.term(t -> t.field("sex").value("女")))
                        .highlight(h -> h.fields("sex", f -> f.preTags("<font color='red'>").postTags("</font>")))
                , User.class);
        searchResponse.hits().hits().forEach(userHit -> System.out.println("user -> " + userHit.source()));
        assert searchResponse.hits().total() != null;
        System.out.println("ES 的 API 高亮查询的数据为：" + searchResponse.hits().hits());
        closeClient();
    }

    @Test
    void testAggregateMaxQueryDocument() throws IOException {
        // 聚合查询，取最大年龄
        SearchResponse<User> searchResponse = client.search(s -> s.index("user").aggregations("maxAge", a -> a.max(m -> m.field("age")))
                , User.class);
        searchResponse.aggregations().forEach((key, value) -> System.out.println("ES 的 API 聚合查询的最大年龄（" + key + "）为：" + value.max().value() + "岁"));
        closeClient();
    }

    /**
     * 关闭传输层和客户端
     *
     * @throws IOException IO异常
     */
    private void closeClient() throws IOException {
        // 关闭 ES 客户端
        transport.close();
        restClient.close();
    }
}
