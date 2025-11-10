package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.GroupDto;
import at.technikum.springrestbackend.entity.Group;
import at.technikum.springrestbackend.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(UUID id) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            System.out.println("Keine Gruppe mit " + id);
            return null;
        }
        return group;
    }

    @Transactional
    public Group createGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());

        return groupRepository.save(group);
    }

    @Transactional
    public Group updateGroup(UUID id, GroupDto groupDto) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            System.out.println("Keine Gruppe mit " + id);
            return null;
        }

        group.setName(groupDto.getName());

        return groupRepository.save(group);
    }

    @Transactional
    public void deleteGroup(UUID id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
        } else {
            System.out.println("Keine Gruppe mit: " + id);
        }
    }
}

