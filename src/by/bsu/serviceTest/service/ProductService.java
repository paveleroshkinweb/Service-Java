package by.bsu.serviceTest.service;

import by.bsu.serviceTest.dao.DAO;
import by.bsu.serviceTest.dao.ProductDAO;
import by.bsu.serviceTest.entity.Message;
import by.bsu.serviceTest.entity.Product;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
public class ProductService {

    private static final Gson gson = new Gson();

    private static final DAO<Product> productDAO = new ProductDAO();

    @GET
    public Response getProducts() {
        List<Product> products = productDAO.selectAll();
        return formOkResponse(gson.toJson(products));
    }

    @POST
    public Response addProduct(String productJson) {
        Product product = gson.fromJson(productJson, Product.class);
        if (product.getName() == null || product.getPrice() < 0) {
            return formBadResponse(422, formMessage("A new product can't be created! Please enter correct data for product!"));
        }
        productDAO.insert(product);
        return formOkResponse(formMessage("A new product " + product.getName() + " was created!"));
    }

    @DELETE
    public Response deleteProducts() {
        productDAO.deleteAll();
        return formOkResponse(formMessage("All products was deleted!"));
    }

    @Path("{id}")
    @GET
    public Response getProductById(@PathParam("id") long id) {
        Product product = productDAO.selectOne(id);
        if (product == null) {
            return formBadResponse(404, formMessage("No user with such id!"));
        }
        return formOkResponse(gson.toJson(product));
    }

    @Path("{id}")
    @DELETE
    public Response deleteProductById(@PathParam("id") long id) {
        if (!productDAO.exist(id)) {
            return formBadResponse(404, formMessage("No user with such id!"));
        }
        productDAO.delete(id);
        return formOkResponse(formMessage("User with id: " + id + " was removed"));
    }

    private Response formOkResponse(Object entity) {
        return Response.ok()
                        .entity(entity)
                        .build();
    }

    private Response formBadResponse(int status, Object entity) {
        return Response.status(status)
                        .entity(entity)
                        .build();
    }

    private String formMessage(String msg) {
        return gson.toJson(new Message(msg));
    }

}