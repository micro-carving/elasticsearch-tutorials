package com.olinonee.elasticsearch.biz;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

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

    @Test
    void testConnect() throws IOException {
        // 创建低级客户端
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)).build();

        // 使用 Jackson 映射器创建传输层
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // 创建API客户端
        ElasticsearchClient client = new ElasticsearchClient(transport);
        // API 响应值，状态码 （1xx, 2xx 和 3xx 是 true 但是 4xx 是 false）
        final BooleanResponse ping = client.ping();
        System.out.println("ES 的 API 响应结果为：" + ping.value());

        // 关闭 ES 客户端
        transport.close();
        restClient.close();
    }
}
