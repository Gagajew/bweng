package at.technikum.springrestbackend.repositories;

import at.technikum.springrestbackend.dtos.UserGroupViewDto;
import at.technikum.springrestbackend.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    List<Group> findByMembers_Id(UUID userId);

    @Query("""
        select new at.technikum.springrestbackend.dtos.UserGroupViewDto(
        u.id, u.username, u.email, g.id, g.name
        )
        from Group g
        join g.members u
        order by g.name, u.username
""")
    List<UserGroupViewDto> getUserGroupOverview();
}
