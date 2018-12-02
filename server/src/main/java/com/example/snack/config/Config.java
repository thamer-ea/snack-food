package com.example.snack.config;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class Config extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private MongoClientURI uri;

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(uri);
    }

    @Override
    protected String getDatabaseName() {
        return uri.getDatabase();
    }

    @Bean
    public Mongobee mongobee() throws Exception {
        Mongobee runner = new Mongobee(uri);
        runner.setDbName(getDatabaseName());
        runner.setChangeLogsScanPackage("com.example.snack.database");
        runner.setMongoTemplate(mongoTemplate());
        return runner;
    }
}
