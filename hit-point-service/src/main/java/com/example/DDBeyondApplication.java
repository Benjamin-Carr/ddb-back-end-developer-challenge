package com.example;

import com.example.resources.CharacterResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class DDBeyondApplication extends Application<DDBeyondConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DDBeyondApplication().run(args);
    }

    @Override
    public String getName() {
        return "DDBeyond";
    }

    @Override
    public void initialize(final Bootstrap<DDBeyondConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final DDBeyondConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new CharacterResource());
    }

}
