package at.technikum.springrestbackend.repositories;

import at.technikum.springrestbackend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
