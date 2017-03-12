package com.breiler.brollop;

import com.breiler.brollop.health.TemplateHealthCheck;
import com.breiler.brollop.resources.GuestResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.meltmedia.dropwizard.mongo.MongoBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.jongo.Jongo;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;


public class BrollopApplication extends Application<BrollopConfiguration> {
    private MongoBundle<BrollopConfiguration> mongoBundle;

    public static void main(String[] args) throws Exception {
        new BrollopApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<BrollopConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));

        bootstrap.addBundle(mongoBundle = MongoBundle.<BrollopConfiguration>builder()
                .withConfiguration(BrollopConfiguration::getMongo)
                .build());
    }

    @Override
    public void run(BrollopConfiguration configuration,
                    Environment environment) {
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        final Jongo jongo = new Jongo(mongoBundle.getDB());
        environment.jersey().register(jongo);

        environment.jersey().register(new GuestResource(jongo, configuration));
        environment.jersey().setUrlPattern("/api/*");


        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        enableCORS(environment);

        // init Swagger resources
        initSwagger(configuration, environment);
    }

    private void initSwagger(BrollopConfiguration configuration, Environment environment) {
        environment.jersey().register(new ApiListingResource());

        BeanConfig config = new BeanConfig();
        config.setTitle("Swagger sample app");
        config.setVersion("1.0.0");
        config.setResourcePackage("com/breiler/brollop");
        config.setScan(true);
        config.setBasePath("/api");
    }

    private void enableCORS(Environment environment) {
        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

}