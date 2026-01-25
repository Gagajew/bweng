package at.technikum.springrestbackend.storage;

import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    String upload(MultipartFile file);
    InputStream load(String externalId);
}
