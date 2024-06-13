package com.aptech.bookingmovies.configurations;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.aptech.bookingmovies.repositories")
public class ElasticsearchConfig {
//
//    @Bean
//    public RestHighLevelClient client() {
//        return new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("localhost", 9200, "http")
//                )
//        );
//    }
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchRestTemplate(ElasticsearchConverter elasticsearchConverter) {
//        return new ElasticsearchRestTemplate(client(), elasticsearchConverter);
//    }
//
//    @Bean
//    public ElasticsearchConverter elasticsearchConverter() {
//        MappingElasticsearchConverter mappingElasticsearchConverter = new MappingElasticsearchConverter();
//        return mappingElasticsearchConverter;
//    }
}

