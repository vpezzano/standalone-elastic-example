package com.techprimers.elastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * We will connect to ElasticSearch via TransportClient. When we start
 * ElasticSearch, it will listen on two different ports, 9200 for the
 * HttpClient, and 9300 for the TransportClient. So, port 9200 is for
 * http requests, while port 9300 is for socket requests. This last one
 * is not accessible via Http.
 */
@SpringBootApplication
public class StandaloneElasticExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(StandaloneElasticExampleApplication.class, args);
	}
}
