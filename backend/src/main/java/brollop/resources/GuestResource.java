package brollop.resources;

import brollop.StaticUtils;
import brollop.contract.AddGuestDTO;
import brollop.core.Guest;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.jongo.Jongo;
import org.jongo.MongoCursor;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Path("/guests")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "guests", description = "Resource for handling guests")
public class GuestResource {
    private final AtomicLong counter;

    private final Jongo jongo;

    @Inject
    public GuestResource(Jongo jongo) {
        this.jongo = jongo;
        this.counter = new AtomicLong();
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

        Mapper mapper = new DozerBeanMapper();
        Guest g = mapper.map(guest, Guest.class);
        g.setUserId(StaticUtils.getRandomHexString(16));
        jongo.getCollection("guests").insert(g);
    }
}