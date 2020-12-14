package devdays.demo.mp.kohaku;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.helidon.microprofile.cors.CrossOrigin;
import io.helidon.webserver.BadRequestException;

/**
 * This class implements REST endpoints to interact with Pokemons. The following
 * operations are supported:
 *
 * <ul>
 * <li>GET /kohaku: Retrieve list of all pokemons</li>
 * <li>GET /kohaku/{id}: Retrieve single kohaku by ID</li>
 * <li>GET /kohaku/name/{name}: Retrieve single kohaku by name</li>
 * <li>DELETE /kohaku/{id}: Delete a kohaku by ID</li>
 * <li>POST /kohaku: Create a new kohaku</li>
 * </ul>
 *
 * Pokémon, and Pokémon character names are trademarks of Nintendo.
 */
@Path("kohaku")
public class KohakuResource {

    @PersistenceContext(unitName = "test")
    private EntityManager entityManager;

    @OPTIONS
    @CrossOrigin(value = {"*"},
                 allowMethods = {HttpMethod.GET})
    public void options() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kohaku> getKohaku() {
        return entityManager.createNamedQuery("getKohaku", Kohaku.class).getResultList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Kohaku getKohakuById(@PathParam("id") String id) {
        Kohaku kohaku = entityManager.find(Kohaku.class, Integer.valueOf(id));
        if (kohaku == null) {
            throw new NotFoundException("Unable to find kohaku with ID " + id);
        }
        return kohaku;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteKohaku(@PathParam("id") String id) {
        Kohaku kohaku = getKohakuById(id);
        entityManager.remove(kohaku);
    }

    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Kohaku getKohakuByName(@PathParam("name") String name) {
        TypedQuery<Kohaku> query = entityManager.createNamedQuery("getKohakuByName", Kohaku.class);
        List<Kohaku> list = query.setParameter("name", name).getResultList();
        if (list.isEmpty()) {
            throw new NotFoundException("Unable to find kohaku with name " + name);
        }
        return list.get(0);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public void createKohaku(Kohaku kohaku) {
        try {
            entityManager.persist(kohaku);
        } catch (Exception e) {
            throw new BadRequestException("Unable to create kimetsu with ID " + kohaku.getId());
        }
    }
}
