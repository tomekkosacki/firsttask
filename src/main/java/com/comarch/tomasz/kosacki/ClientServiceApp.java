package com.comarch.tomasz.kosacki;

import com.comarch.tomasz.kosacki.configurationClass.ProjectConfiguration;
import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import com.comarch.tomasz.kosacki.resources.UserService;
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

//        final UserEntity userOne = new UserEntity("1", "FN1", "LN1", "email1@email.com", new Date());
//        final UserEntity userTwo = new UserEntity("4", "FN1", "LN2", "email2@email.com", new Date());
//        datastore.save(userOne);
//        datastore.save(userTwo);

        final UserService personService = new UserService(new UserDB(datastore), new Mapper());
        environment.jersey().register(personService);
    }
}
