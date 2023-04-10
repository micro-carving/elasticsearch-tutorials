package com.olinonee.elasticsearch.biz;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
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
import java.util.HashMap;
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
                        .document(new User("小芳", "女", "17712345678")));
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
