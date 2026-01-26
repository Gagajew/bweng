package at.technikum.springrestbackend.it;

import at.technikum.springrestbackend.storage.FileStorage;
import io.minio.MinioClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    @ServiceConnection
    static final MariaDBContainer<?> mariadb =
            new MariaDBContainer<>("mariadb:10.11");

    @MockitoBean
    protected MinioClient minioClient;

    @MockitoBean
    protected FileStorage fileStorage;

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("minio.bucket", () -> "test-bucket");
        registry.add("security.jwt.secret", () -> "my-jwt-secret-my-jwt-secret-my-jwt-secret");

        // optional – nur wenn Liquibase dir im Test Probleme macht:
        // registry.add("spring.liquibase.enabled", () -> "false");
    }
}
