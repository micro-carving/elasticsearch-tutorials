package com.olinonee.elasticsearch.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * es 程序入口
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2023-04-03
 */
@SpringBootApplication
public class ElasticSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }
}
