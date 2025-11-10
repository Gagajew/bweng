package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupPostRepository extends JpaRepository<GroupPost, UUID> {
}
