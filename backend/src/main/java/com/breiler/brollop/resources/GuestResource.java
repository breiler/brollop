package com.breiler.brollop.resources;

import com.breiler.brollop.BrollopConfiguration;
import com.breiler.brollop.StaticUtils;
import com.breiler.brollop.contract.AddGuestDTO;
import com.breiler.brollop.core.Guest;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.java.Log;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.jongo.Jongo;
import org.jongo.MongoCursor;

import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Path("/guests")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "guests", description = "Resource for handling guests")
@Log
public class GuestResource {
    private final AtomicLong counter;

    private final Jongo jongo;
    private final BrollopConfiguration configuration;

    @Inject
    public GuestResource(Jongo jongo, BrollopConfiguration configuration) {
        this.jongo = jongo;
        this.counter = new AtomicLong();
        this.configuration = configuration;
    }

    @GET
    @Timed
    public List<Guest> fetchGuests() {
        MongoCursor<Guest> guestsMongoCursor = jongo.getCollection("guests").find().as(Guest.class);
        return Lists.newArrayList(guestsMongoCursor.iterator());
    }

    @DELETE
    public void delete() {
        jongo.getCollection("guests").drop();
    }

    @POST
    @ApiOperation(value = "Registers a new guest",
            notes = "Registers a guest",
            response = Guest.class)
    public void create(
            @ApiParam(value = "The guest to be added", required = true, example = "Test")
                    AddGuestDTO guest) {

        log.info("Tog emot anmälan " + guest);

        Mapper mapper = new DozerBeanMapper();
        Guest g = mapper.map(guest, Guest.class);
        g.setUserId(StaticUtils.getRandomHexString(16));
        jongo.getCollection("guests").insert(g);

        try {
            ArrayList<InternetAddress> emailList = new ArrayList<>();
            emailList.add(new InternetAddress(g.getEmail()));

            Email email = new SimpleEmail();
            email.setSubject("Välkommen till vårt bröllop");
            email.setMsg("Du har nu anmält dig till Camilla och Joacims bröllop den 2:a september 2017, välkommen!");
            email.setTo(emailList);
            email.setFrom(configuration.getSmtpEmail(), "Bröllop");
            email.setHostName(configuration.getSmtpHost());
            email.setSmtpPort(Integer.valueOf(configuration.getSmtpPort()));
            email.setAuthenticator(new DefaultAuthenticator(configuration.getSmtpUsername(), configuration.getSmtpPassword()));
            email.send();
        } catch (AddressException | EmailException e) {
            throw new RuntimeException("Kunde inte registrera", e);
        }
    }
}