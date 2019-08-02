package com.comarch.tomasz.kosacki;

import com.comarch.tomasz.kosacki.configurationClass.ProjectConfiguration;
import com.comarch.tomasz.kosacki.dao.UserDao;
import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.generators.DateOfBirthGenerator;
import com.comarch.tomasz.kosacki.generators.ZodiacGenerator;
import com.comarch.tomasz.kosacki.health.ServiceHealthCheck;
import com.comarch.tomasz.kosacki.jobs.UsersJobFactory;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import com.comarch.tomasz.kosacki.resources.UserResources;
import com.comarch.tomasz.kosacki.service.UserService;
import com.comarch.tomasz.kosacki.serviceExceptions.AppExceptionMapper;
import com.comarch.tomasz.kosacki.tags.TagClient;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import com.mongodb.MongoClient;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.quartz.SchedulerException;


public class ClientServiceApp extends Application<ProjectConfiguration> {

    public static void main(String[] args) throws Exception {
        new ClientServiceApp().run(args);
    }

    @Override
    public void run(ProjectConfiguration configuration, Environment environment) throws Exception {

        TagClient tagClient = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .requestInterceptor(new BasicAuthRequestInterceptor(configuration.getUserName(), configuration.getUserPassword()))
                .target(TagClient.class, configuration.getTagUrl());

        final Morphia morphia = new Morphia();
        morphia.map(UserEntity.class);
        MongoClient mongoClient = new MongoClient();
        final Datastore datastore = morphia.createDatastore(mongoClient, configuration.getDbName());
        datastore.ensureIndexes();

        UserDao userDao = new UserDB(datastore);
        Mapper mapper = new Mapper();
        UserService userService = new UserService(tagClient, mapper, userDao, configuration);
        final UserResources userResources = new UserResources(userService);

        environment.jersey().register(userResources);
        environment.jersey().register(new AppExceptionMapper());
        environment.healthChecks().register("ServiceHealthCheck", new ServiceHealthCheck(mongoClient));


//        DateOfBirthGenerator dateOfBirthGenerator = new DateOfBirthGenerator();
//        UsersJobFactory usersJobFactoryBirthDay = new UsersJobFactory();
//        try {
//            usersJobFactoryBirthDay.runDateOFBirthJob(dateOfBirthGenerator, userService, configuration);
//        } catch (SchedulerException ex) {
//            ex.printStackTrace();
//        }

        ZodiacGenerator zodiacGenerator = new ZodiacGenerator();
        UsersJobFactory usersJobFactoryZodiac = new UsersJobFactory();
        try {
            usersJobFactoryZodiac.runZodiacJob(zodiacGenerator, userDao, configuration, tagClient);
        } catch (SchedulerException ex) {
            ex.printStackTrace();
        }
    }
}
