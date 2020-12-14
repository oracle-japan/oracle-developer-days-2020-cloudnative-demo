
package devdays.demo.mp.trendword;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.spi.CDI;
import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.helidon.microprofile.server.Server;

class MainTest {

    private static Server server;
    private static String serverUrl;
    private static Client client;

    @BeforeAll
    public static void startTheServer() {
        client = ClientBuilder.newClient();
        server = Server.create().start();
        serverUrl = "http://localhost:" + server.port();
    }

    @AfterAll
    static void destroyClass() {
        CDI<Object> current = CDI.current();
        ((SeContainer) current).close();
    }

    @Test
    void testTrendWord() {
        assertThat(getTrendWordCount(), is(10));

        TrendWord trendWord = client.target(serverUrl)
                .path("trendword/1")
                .request()
                .get(TrendWord.class);
        assertThat(trendWord.getName(), is("愛の不時着"));

        Response response = client.target(serverUrl)
                .path("trendword/1")
                .request()
                .get();
        assertThat(response.getStatus(), is(200));

        TrendWord test = new TrendWord();
        test.setRank(11);
        test.setId(100);
        test.setName("Test");
        response = client.target(serverUrl)
                .path("trendword")
                .request()
                .post(Entity.entity(test, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus(), is(204));
        assertThat(getTrendWordCount(), is(11));

        response = client.target(serverUrl)
                .path("trendword/100")
                .request()
                .delete();
        assertThat(response.getStatus(), is(204));
        assertThat(getTrendWordCount(), is(10));
    }

    @Test
    void testHealthMetrics() {
        Response response = client.target(serverUrl)
                .path("health")
                .request()
                .get();
        assertThat(response.getStatus(), is(200));
        response = client.target(serverUrl)
                .path("metrics")
                .request()
                .get();
        assertThat(response.getStatus(), is(200));
    }

    private int getTrendWordCount() {
        JsonArray trendWord = client.target(serverUrl)
                .path("trendword")
                .request()
                .get(JsonArray.class);
        return trendWord.size();
    }
}
