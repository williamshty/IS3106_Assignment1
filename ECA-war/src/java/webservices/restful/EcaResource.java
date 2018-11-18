/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Admin;
import entity.Buyer;
import entity.Item;
import entity.ItemOrder;
import entity.Seller;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.ECASessionBeanLocal;

/**
 * REST Web Service
 *
 * @author tianyuan.shi
 */
@Path("eca")
public class EcaResource {

    @EJB
    private ECASessionBeanLocal ecaSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Seller> test() {

        return getEcaSessionBeanLocal().viewAllSellers();
    }

    @Path("/admin/login")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response adminLogin(Admin a) {
        ecaSessionBeanLocal.adminLogin(a.getUsername(), a.getPassword());
        return Response.status(200).entity(
                a
        ).build();
    }

    @Path("/admin/seller/deactivate/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateSeller(@PathParam("id") Long sID) {
        try {
            ecaSessionBeanLocal.deactivateSeller(sID);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/admin/seller/activate/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateSeller(@PathParam("id") Long sID) {
        try {
            ecaSessionBeanLocal.activateSeller(sID);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/admin/buyer/deactivate/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateBuyer(@PathParam("id") Long sID) {
        try {
            ecaSessionBeanLocal.deactivateBuyer(sID);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/admin/buyer/activate/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateBuyer(@PathParam("id") Long sID) {
        try {
            ecaSessionBeanLocal.activateBuyer(sID);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/admin/seller/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadAllSeller() {
        List<Seller> results = getEcaSessionBeanLocal().viewAllSellers();
        for (int i = 0; i < results.size(); i++) {
            Seller newSeller = new Seller();
            newSeller.setGender(results.get(i).getGender());
            newSeller.setStatus(results.get(i).isStatus());
            newSeller.setUsername(results.get(i).getUsername());
            newSeller.setName(results.get(i).getName());
            newSeller.setId(results.get(i).getId());
            results.set(i, newSeller);
        }
        GenericEntity<List<Seller>> entity = new GenericEntity<List<Seller>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();

    }

    @Path("/admin/buyer/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadAllBuyer() {
        List<Buyer> results = getEcaSessionBeanLocal().viewAllBuyers();
        for (int i = 0; i < results.size(); i++) {
            Buyer newBuyer = new Buyer();
            newBuyer.setGender(results.get(i).getGender());
            newBuyer.setStatus(results.get(i).isStatus());
            newBuyer.setUsername(results.get(i).getUsername());
            newBuyer.setName(results.get(i).getName());
            newBuyer.setId(results.get(i).getId());
            results.set(i, newBuyer);
        }
        GenericEntity<List<Buyer>> entity = new GenericEntity<List<Buyer>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();

    }

    @Path("/seller/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sellerRegistration(Seller s) {
        s.setStatus(true);
        ecaSessionBeanLocal.registerSeller(s);
        return Response.status(200).entity(
                s
        ).build();
    }

    @Path("/seller/login")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sellerLogin(Seller s) {
        Seller result = ecaSessionBeanLocal.sellerLogin(s.getUsername(), s.getPassword());

        if (!result.isStatus() || result == null) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception).build();
        } else {
            Seller newSeller = new Seller();

            newSeller.setGender(result.getGender());
            newSeller.setStatus(result.isStatus());
            newSeller.setUsername(result.getUsername());
            newSeller.setName(result.getName());
            newSeller.setId(result.getId());
            result = newSeller;
            return Response.status(200).entity(
                    result
            ).build();
        }
    }

    @Path("/seller/item/add/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sellerAddItem(@PathParam("id") Long sID, Item i) {
        try {
            ecaSessionBeanLocal.addItem(i, sID);

            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/seller/profile/{id}/{name}/{gender}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response sellerEditProfile(@PathParam("id") Long sID, @PathParam("name") String name, @PathParam("gender") byte gender) {
        try {
            Seller s = ecaSessionBeanLocal.getSellerByID(sID);
            s.setGender(gender);
            s.setName(name);
            ecaSessionBeanLocal.updateSellerProfile(s);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/seller/item/edit")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sellerEditItem(Item i) {
        try {
            ecaSessionBeanLocal.editItem(i);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/seller/item/delete/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response sellerDeleteItem(@PathParam("id") Long iID) {
        try {
            ecaSessionBeanLocal.deleteItem(iID);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/seller/item/all/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSellerItem(@PathParam("id") Long sID) {
        List<Item> results = getEcaSessionBeanLocal().viewSellerItems(sID, "");
        for (int i = 0; i < results.size(); i++) {
            Item newItem = new Item();
            newItem.setCategory(results.get(i).getCategory());
            newItem.setDescription(results.get(i).getDescription());
            newItem.setName(results.get(i).getName());
            newItem.setPrice(results.get(i).getPrice());
            newItem.setQuantity(results.get(i).getQuantity());
            newItem.setOrders(results.get(i).getOrders());
            newItem.setId(results.get(i).getId());
            results.set(i, newItem);
        }
        GenericEntity<List<Item>> entity
                = new GenericEntity<List<Item>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();

    }

    @Path("/seller/item/search/{id}/{key}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSellerItem(@PathParam("id") Long sID, @PathParam("key") String key) {
        List<Item> results = getEcaSessionBeanLocal().viewSellerItems(sID, key);
        for (int i = 0; i < results.size(); i++) {
            Item newItem = new Item();
            newItem.setCategory(results.get(i).getCategory());
            newItem.setDescription(results.get(i).getDescription());
            newItem.setName(results.get(i).getName());
            newItem.setPrice(results.get(i).getPrice());
            newItem.setQuantity(results.get(i).getQuantity());
            newItem.setOrders(results.get(i).getOrders());
            newItem.setId(results.get(i).getId());
            results.set(i, newItem);
        }
        GenericEntity<List<Item>> entity
                = new GenericEntity<List<Item>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();

    }

    @Path("/seller/order/edit/{id}/{status}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)

    public Response sellerEditOrder(@PathParam("id") Long oID, @PathParam("status") String status) {
        try {
            ecaSessionBeanLocal.updateOrderStatus(oID, status);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/seller/order/all/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSellerOrder(@PathParam("id") Long sID) {
        List<ItemOrder> results = getEcaSessionBeanLocal().viewAllSellerOrders(sID);
        GenericEntity<List<ItemOrder>> entity
                = new GenericEntity<List<ItemOrder>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();

    }

    @Path("/buyer/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyerRegistration(Buyer b) {
        b.setStatus(true);
        ecaSessionBeanLocal.registerBuyer(b);
        return Response.status(200).entity(
                b
        ).build();
    }

    @Path("/buyer/login")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyerLogin(Buyer b) {
        Buyer result = ecaSessionBeanLocal.buyerLogin(b.getUsername(), b.getPassword());

        if (!result.isStatus() || result == null) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception).build();
        } else {
            Buyer newBuyer = new Buyer();

            newBuyer.setGender(result.getGender());
            newBuyer.setStatus(result.isStatus());
            newBuyer.setUsername(result.getUsername());
            newBuyer.setName(result.getName());
            newBuyer.setId(result.getId());
            result = newBuyer;
            return Response.status(200).entity(
                    result
            ).build();
        }
    }

    @Path("/buyer/profile/{id}/{name}/{gender}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyerEditProfile(@PathParam("id") Long bID, @PathParam("name") String name, @PathParam("gender") byte gender) {
        try {
            Buyer b = ecaSessionBeanLocal.getBuyerByID(bID);
            b.setGender(gender);
            b.setName(name);
            ecaSessionBeanLocal.updateBuyerProfile(b);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/buyer/item/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItem() {
        List<Item> results = getEcaSessionBeanLocal().viewAllBuyerItems();
        for (int i = 0; i < results.size(); i++) {
            Item newItem = new Item();
            newItem.setCategory(results.get(i).getCategory());
            newItem.setDescription(results.get(i).getDescription());
            newItem.setName(results.get(i).getName());
            newItem.setPrice(results.get(i).getPrice());
            newItem.setQuantity(results.get(i).getQuantity());
            Seller newSeller = new Seller();
            newSeller.setId(results.get(i).getSeller().getId());
            newItem.setSeller(newSeller);
            newItem.setId(results.get(i).getId());
            results.set(i, newItem);
        }
        GenericEntity<List<Item>> entity
                = new GenericEntity<List<Item>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();

    }

    @Path("/buyer/item/search/{type}/{key}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBuyerItem(@PathParam("type") String type, @PathParam("key") String key) {

        List<Item> results = new ArrayList<Item>();
        switch (type) {
            case "keyword":
                results = getEcaSessionBeanLocal().searchItemByKeyword(key);
                break;
            case "category":
                results = getEcaSessionBeanLocal().searchItemByCategory(key);
                break;

            case "quantity":
                Long numKey = Long.valueOf(key);
                results = getEcaSessionBeanLocal().searchItemByAvailability(numKey);
                break;
        }

        if (results != null) {
            for (int i = 0; i < results.size(); i++) {
                Item newItem = new Item();
                newItem.setCategory(results.get(i).getCategory());
                newItem.setDescription(results.get(i).getDescription());
                newItem.setName(results.get(i).getName());
                newItem.setPrice(results.get(i).getPrice());
                newItem.setQuantity(results.get(i).getQuantity());
                Seller newSeller = new Seller();
                newSeller.setId(results.get(i).getSeller().getId());
                newItem.setSeller(newSeller);
                newItem.setId(results.get(i).getId());
                results.set(i, newItem);
            }
        }
        GenericEntity<List<Item>> entity
                = new GenericEntity<List<Item>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();

    }
    
    @Path("/buyer/order/edit/{orderID}/{rating}/{review}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyerEditOrder(@PathParam("orderID") Long oID, @PathParam("rating") String rating, @PathParam("review") String review) {
        try {
            ecaSessionBeanLocal.addFeedback(rating, review, oID);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }


    @Path("/buyer/order/create/{itemID}/{sellerID}/{buyerID}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("itemID") Long iID, @PathParam("sellerID") Long sID, @PathParam("buyerID") Long bID) {
        try {
            Item i = ecaSessionBeanLocal.findItemByID(iID);
            ecaSessionBeanLocal.createOrder(i, sID, bID);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/buyer/order/all/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBuyerOrder(@PathParam("id") Long bID) {
        List<ItemOrder> results = getEcaSessionBeanLocal().viewAllBuyerOrders(bID);
        GenericEntity<List<ItemOrder>> entity
                = new GenericEntity<List<ItemOrder>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();

    }

    /**
     * @return the ecaSessionBeanLocal
     */
    public ECASessionBeanLocal getEcaSessionBeanLocal() {
        return ecaSessionBeanLocal;
    }

    /**
     * @param ecaSessionBeanLocal the ecaSessionBeanLocal to set
     */
    public void setEcaSessionBeanLocal(ECASessionBeanLocal ecaSessionBeanLocal) {
        this.ecaSessionBeanLocal = ecaSessionBeanLocal;
    }

}
