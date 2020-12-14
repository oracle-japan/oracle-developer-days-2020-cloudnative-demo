package devdays.demo.mp.trendword;

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
 * This class implements REST endpoints to interact with trendword. The following
 * operations are supported:
 *
 * <ul>
 * <li>GET /trendword: Retrieve list of all trendword</li>
 * <li>GET /trendword/{id}: Retrieve single trendword by ID</li>
 * <li>GET /trendword/name/{name}: Retrieve single trendword by name</li>
 * <li>DELETE /trendword/{id}: Delete a trendword by ID</li>
 * <li>POST /trendword: Create a new trendword</li>
 * </ul>
 *
 */
@Path("trendword")
public class TrendWordResource {

    @PersistenceContext(unitName = "test")
    private EntityManager entityManager;

    @OPTIONS
    @CrossOrigin(value = {"*"},
                 allowMethods = {HttpMethod.GET})
    public void options() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TrendWord> getKimetsu() {
        return entityManager.createNamedQuery("getTrendWord", TrendWord.class).getResultList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TrendWord getTrendWordById(@PathParam("id") String id) {
        TrendWord trendWord = entityManager.find(TrendWord.class, Integer.valueOf(id));
        if (trendWord == null) {
            throw new NotFoundException("Unable to find trendword with ID " + id);
        }
        return trendWord;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteTrendWord(@PathParam("id") String id) {
        TrendWord kimetsu = getTrendWordById(id);
        entityManager.remove(kimetsu);
    }

    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public TrendWord getKimetsuByName(@PathParam("name") String name) {
        TypedQuery<TrendWord> query = entityManager.createNamedQuery("getTrendWordByName", TrendWord.class);
        List<TrendWord> list = query.setParameter("name", name).getResultList();
        if (list.isEmpty()) {
            throw new NotFoundException("Unable to find trendword with name " + name);
        }
        return list.get(0);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public void createTrendWord(TrendWord trendWord) {
        try {
            entityManager.persist(trendWord);
        } catch (Exception e) {
            throw new BadRequestException("Unable to create kimetsu with ID " + trendWord.getId());
        }
    }
}
