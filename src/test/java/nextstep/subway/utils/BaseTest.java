package nextstep.subway.utils;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {
    @LocalServerPort
    public int port;

    @Autowired
    public DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void setUp(){
        RestAssured.port = port;
        databaseCleanup.execute();
    }
}
