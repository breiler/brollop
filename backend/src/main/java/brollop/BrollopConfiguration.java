package brollop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meltmedia.dropwizard.mongo.MongoConfiguration;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class BrollopConfiguration extends Configuration {

    @JsonProperty
    @NotEmpty
    private String template;

    @JsonProperty
    @NotEmpty
    private String defaultName = "Stranger";

    @JsonProperty
    protected MongoConfiguration mongo;

    @JsonProperty
    @NotEmpty
    private String smtpHost;

    @JsonProperty
    @NotEmpty
    private String smtpUsername;

    @JsonProperty
    @NotEmpty
    private String smtpPassword;

    @JsonProperty
    @NotEmpty
    private String smtpPort;

    @NotNull
    @JsonProperty
    private String smtpEmail;

    @Valid
    @NotNull
    @JsonProperty
    private String swaggerBasePath;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getDefaultName() {
        return defaultName;
    }


    public String getSmtpHost() {
        return smtpHost;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    public MongoConfiguration getMongo() {
        return mongo;
    }

    public String getSwaggerBasePath(){ return swaggerBasePath; }

    public String getSmtpEmail() {
        return smtpEmail;
    }
}