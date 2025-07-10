package org.acme.resource;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/mobile")
public class MobileResource {

    List<Mobile> mobileList = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMobileList(){
        return Response.ok(mobileList).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMobile(Mobile mobile) {
        mobileList.add(mobile);
        return Response.ok(mobile).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMobile(@PathParam("id") int id, Mobile mobileToUpdate) {

        mobileList = mobileList.stream().map(mobile -> {
            if (mobile.getId() == id) {
                return mobileToUpdate;
            } else {
                return mobile;
            }
        }).toList();
        return Response.ok(mobileList).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMobile(@PathParam("id") int mobileId) {
        Optional<Mobile> optionalMobile = mobileList.stream().filter(mobile -> mobile.getId() == mobileId).findFirst();
        if (optionalMobile.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        mobileList.remove(optionalMobile.get());
        return Response.ok(optionalMobile.get()).build();
    }
}
