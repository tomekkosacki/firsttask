package com.comarch.tomasz.kosacki.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ServiceHealthCheck extends HealthCheck {

    private MongoClient mongoClient;

    public ServiceHealthCheck(MongoClient mongoClient) {

        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() {

        MongoDatabase database = mongoClient.getDatabase("morphia_user");
        try {
            database.runCommand(new Document("serverStatus", 1));
        } catch (Exception ex) {
            return Result.unhealthy("No connection to data base");
        }

        return Result.healthy();
    }
}
