package at.technikum.springrestbackend.storage.minio;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfig {

    @Bean
    public MinioClient minioClient(MinioProperties props) {
        String base = props.getUrl();
        if (!base.startsWith("http://") && !base.startsWith("https://")) {
            base = "http://" + base;
        }
        String endpoint = base + ":" + props.getPort();

        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(props.getAccessKey(), props.getSecretKey())
                .build();
    }
}
