package at.technikum.springrestbackend.repositories;

import at.technikum.springrestbackend.entities.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupPostRepository extends JpaRepository<GroupPost, UUID> {
    boolean existsByPost_IdAndGroup_Members_Id(UUID postId, UUID userId);

    List<GroupPost> findDistinctByGroup_Members_Id(UUID userId);
}
