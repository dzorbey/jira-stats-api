package com.jira.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

//@PropertySource(value = "file:api.properties")
@SpringBootApplication
public class JiraStatsApplication {
    public static void main(String[] args) {
        SpringApplication.run(JiraStatsApplication.class, args);
    }
}
