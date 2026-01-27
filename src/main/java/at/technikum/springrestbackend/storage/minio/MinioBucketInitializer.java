package at.technikum.springrestbackend.storage.minio;

import at.technikum.springrestbackend.storage.FileException;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MinioBucketInitializer {

    private final MinioClient minioClient;
    private final MinioProperties props;

    public MinioBucketInitializer(MinioClient minioClient, MinioProperties props) {
        this.minioClient = minioClient;
        this.props = props;
    }

    @PostConstruct
    public void ensureBucketExists() {
        int attempts = 10;
        long sleepMs = 1000;

        for (int i = 1; i <= attempts; i++) {
            try {
                boolean exists = minioClient.bucketExists(
                        BucketExistsArgs.builder()
                                .bucket(props.getBucket())
                                .build()
                );

                if (!exists) {
                    minioClient.makeBucket(
                            MakeBucketArgs.builder()
                                    .bucket(props.getBucket())
                                    .build()
                    );
                }

                return; // success
            } catch (Exception e) {
                if (i == attempts) {
                    throw new FileException("MinIO bucket init failed", e);
                }
                try {
                    Thread.sleep(sleepMs);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}