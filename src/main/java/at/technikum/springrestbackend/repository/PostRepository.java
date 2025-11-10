package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
