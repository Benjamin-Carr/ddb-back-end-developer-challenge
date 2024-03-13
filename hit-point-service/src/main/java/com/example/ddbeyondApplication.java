package com.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class ddbeyondApplication extends Application<ddbeyondConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ddbeyondApplication().run(args);
    }

    @Override
    public String getName() {
        return "ddbeyond";
    }

    @Override
    public void initialize(final Bootstrap<ddbeyondConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ddbeyondConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
