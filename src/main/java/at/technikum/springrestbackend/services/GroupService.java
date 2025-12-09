package at.technikum.springrestbackend.services;

import at.technikum.springrestbackend.dtos.GroupDto;
import at.technikum.springrestbackend.entities.Group;
import at.technikum.springrestbackend.entities.User;
import at.technikum.springrestbackend.mappers.GroupMapper;
import at.technikum.springrestbackend.repositories.GroupRepository;
import at.technikum.springrestbackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.technikum.springrestbackend.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    private static final Logger LOG = LoggerFactory.getLogger(GroupService.class);

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;

    public GroupService(GroupMapper groupMapper, GroupRepository groupRepository, UserRepository userRepository){
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<GroupDto> getAllGroups() {

        return groupRepository.findAll().stream().map(groupMapper::toDto).toList();
    }

    public List<GroupDto> getGroupsForUser(UUID userId) {
        List<Group> groups = groupRepository.findByMembers_Id(userId);
        return groups.stream().map(groupMapper::toDto).toList();
    }

    public GroupDto getGroupById(UUID id) {
        Group group = groupRepository.findById(id).orElseThrow(() ->{
            LOG.warn("Group not found with id {}", id);
            return new ResourceNotFoundException("Group not found with id: " + id);
        });
        return groupMapper.toDto(group);
    }

    @Transactional
    public GroupDto createGroup(GroupDto groupDto) {
            return groupMapper.toDto(
                    groupRepository.save(
                            groupMapper.toEntity(groupDto)));
        }

    @Transactional
    public GroupDto updateGroup(UUID id, GroupDto groupDto) {
        Group group = groupRepository.findById(id).orElseThrow(() ->{
            LOG.warn("Group with id {} not found ", id);
            return new ResourceNotFoundException("Group not found with id " + id);
        });
        groupMapper.updateEntityFromDto(groupDto, group);

        Group updated = groupRepository.save(group);
        return groupMapper.toDto(updated);
    }

    @Transactional
    public void deleteGroup(UUID id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            LOG.info("Group with id {} was deleted.", id);
        } else {
            LOG.warn("Tried to delete group with id {}", id);
            throw new ResourceNotFoundException("Group not found with id: " + id);
        }
    }

    @Transactional
    public GroupDto addMember(UUID groupId, UUID userId){
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        group.getMembers().add(user);
        user.getGroups().add(group);

        Group saved = groupRepository.save(group);
        return groupMapper.toDto(saved);
    }
}

