package com.comarch.tomasz.kosacki.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;

public class ServiceHealthCheck extends HealthCheck {

    private MongoClient mongoClient;

    public ServiceHealthCheck(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() throws Exception {
        try {
            mongoClient.getDB("morphia_user").getStats();
        } catch (MongoClientException ex) {
            return Result.unhealthy(ex.getMessage());
        }
        return Result.healthy();
    }
}
