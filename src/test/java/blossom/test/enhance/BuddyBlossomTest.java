package blossom.test.enhance;

import com.ctrip.framework.apollo.ConfigService;
import com.github.charlemaznable.apollo.MockApolloServer;
import com.github.charlemaznable.httpclient.common.HttpStatus;
import lombok.SneakyThrows;
import lombok.val;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.n3r.eql.Eql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringJUnitConfig(TestBuddyConfig.class)
public class BuddyBlossomTest {

    private static final DockerImageName mysqlImageName = DockerImageName.parse("mysql:5.7.34");
    private static final MySQLContainer<?> mysql0 = new MySQLContainer<>(mysqlImageName).withDatabaseName(Eql.DEFAULT_CONN_NAME);

    @BeforeAll
    public static void beforeAll() {
        mysql0.start();

        MockApolloServer.setUpMockServer();
        MockApolloServer.addOrModifyProperty("EqlConfig", Eql.DEFAULT_CONN_NAME, "" +
                "driver=com.mysql.cj.jdbc.Driver\n" +
                "url=" + mysql0.getJdbcUrl() + "\n" +
                "user=" + mysql0.getUsername() + "\n" +
                "password=" + mysql0.getPassword() + "\n");
        await().forever().until(() ->
                isNotBlank(ConfigService.getConfig("EqlConfig").getProperty(Eql.DEFAULT_CONN_NAME, "")));
    }

    @AfterAll
    public static void afterAll() {
        MockApolloServer.tearDownMockServer();
        mysql0.stop();
    }

    @Autowired
    private TestBuddyNoneEqler testNoneEqler;
    @Autowired
    private TestBuddyEqler testEqler;

    @Autowired
    private TestBuddyNoneClient testNoneClient;
    @Autowired
    private TestBuddyClient testClient;

    @Test
    public void testWithEqler() {
        assertEquals("x", testNoneEqler.x());
        assertEquals("y", testEqler.y());
        assertThrows(Exception.class, testEqler::z);
    }

    @Test
    @SneakyThrows
    public void testWithOhClient() {
        try (val mockWebServer = new MockWebServer()) {
            mockWebServer.setDispatcher(new Dispatcher() {
                @Nonnull
                @Override
                public MockResponse dispatch(@Nonnull RecordedRequest request) {
                    return switch (requireNonNull(request.getPath())) {
                        case "/x" -> new MockResponse().setBody("X");
                        case "/y" -> new MockResponse().setBody("Y");
                        default -> new MockResponse()
                                .setResponseCode(HttpStatus.NOT_FOUND.value())
                                .setBody(HttpStatus.NOT_FOUND.getReasonPhrase());
                    };
                }
            });
            mockWebServer.start(21215);

            assertEquals("X", testNoneClient.x());
            assertEquals("Y", testClient.y());
            assertThrows(Exception.class, testClient::z);
        }
    }
}
