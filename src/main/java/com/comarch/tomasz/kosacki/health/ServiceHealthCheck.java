package com.comarch.tomasz.kosacki.health;

import com.codahale.metrics.health.HealthCheck;
import org.mongodb.morphia.Datastore;

public class ServiceHealthCheck extends HealthCheck {

    private Datastore datastore;

    public ServiceHealthCheck(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    protected Result check() throws Exception {

        return Result.healthy();
    }
}
