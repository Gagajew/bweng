package at.technikum.springrestbackend.storage.minio;

import at.technikum.springrestbackend.storage.FileException;
import at.technikum.springrestbackend.storage.FileStorage;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinioStorage implements FileStorage {

    private final MinioClient minioClient;
    private final MinioProperties props;

    public MinioStorage(MinioClient minioClient, MinioProperties props) {
        this.minioClient = minioClient;
        this.props = props;
    }

    @Override
    public String upload(MultipartFile file) {
        String externalId = UUID.randomUUID().toString();

        try (InputStream in = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(props.getBucket())
                            .object(externalId)
                            .stream(in, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return externalId;
        } catch (Exception e) {
            throw new FileException("Upload to MinIO failed", e);
        }
    }


    @Override
    public InputStream load(String externalId) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(props.getBucket())
                            .object(externalId)
                            .build()
            );
        } catch (Exception e) {
            throw new FileException("Load from MinIO failed", e);
        }
    }

    @Override
    public void delete(String externalId) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(props.getBucket())
                            .object(externalId)
                            .build()
            );
        } catch (Exception e) {
            throw new FileException("Delete from MinIO failed", e);
        }
    }

}
