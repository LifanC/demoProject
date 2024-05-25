package com.project.server;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = "com.project.server.**.Mapper")
@EnableScheduling
@SpringBootApplication
public class ServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);
    public static void main(String... args) {
        SpringApplication.run(ServerApplication.class, args);
        start();
    }
    private static void start(){
        logger.info("Start Executing The Program........");
    }
}
