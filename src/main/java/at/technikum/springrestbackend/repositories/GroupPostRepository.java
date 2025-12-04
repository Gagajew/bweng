package at.technikum.springrestbackend.repositories;

import at.technikum.springrestbackend.entities.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupPostRepository extends JpaRepository<GroupPost, UUID> {
}
