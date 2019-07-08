package com.comarch.tomasz.kosacki;

import com.comarch.tomasz.kosacki.configurationClass.ProjectConfiguration;
import com.comarch.tomasz.kosacki.resources.UserService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ClientServiceApp extends Application<ProjectConfiguration> {
    public static void main(String[] args) throws Exception {
        new ClientServiceApp().run(args);
    }

    @Override
    public void run(ProjectConfiguration configuration, Environment environment) {
        final UserService personService = new UserService();
        environment.jersey().register(personService);
    }
}
