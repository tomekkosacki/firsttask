package com.comarch.tomasz.kosacki;

import com.comarch.tomasz.kosacki.configurationClass.ProjectConfiguration;
import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.health.ServiceHealthCheck;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import com.comarch.tomasz.kosacki.resources.UserService;
import com.comarch.tomasz.kosacki.servisExceptions.AppExceptionMapper;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;


public class ClientServiceApp extends Application<ProjectConfiguration> {

    public static void main(String[] args) throws Exception {
        new ClientServiceApp().run(args);
    }

    @Override
    public void run(ProjectConfiguration configuration, Environment environment) {

        final Morphia morphia = new Morphia();
        morphia.map(UserEntity.class);
        MongoClient mongoClient = new MongoClient();
        final Datastore datastore = morphia.createDatastore(mongoClient, "morphia_user");
        datastore.ensureIndexes();

        UserDB userDB = new UserDB(datastore);
        Mapper mapper = new Mapper();
        final UserService personService = new UserService(userDB, mapper);

        environment.jersey().register(personService);
        environment.jersey().register(new AppExceptionMapper());
        environment.healthChecks().register("ServiceHealthCheck", new ServiceHealthCheck(mongoClient));
    }
}
