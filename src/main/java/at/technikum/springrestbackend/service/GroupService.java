package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.GroupDto;
import at.technikum.springrestbackend.entity.Group;
import at.technikum.springrestbackend.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.technikum.springrestbackend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    private static final Logger log = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(UUID id) {
        return groupRepository.findById(id).orElseThrow(() ->{
            log.warn("Group not found with id {}", id);
            return new ResourceNotFoundException("Group not found with id: " + id);
        });
    }

    @Transactional
    public Group createGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());

        return groupRepository.save(group);
    }

    @Transactional
    public Group updateGroup(UUID id, GroupDto groupDto) {
        Group group = getGroupById(id);

        group.setName(groupDto.getName());

        return groupRepository.save(group);
    }

    @Transactional
    public void deleteGroup(UUID id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            log.info("Group with id {} was deleted.", id);
        } else {
            log.warn("Tried to delete group with id {}", id);
            throw new ResourceNotFoundException("Group not found with id: " + id);
        }
    }
}

