package at.technikum.springrestbackend.services;

import at.technikum.springrestbackend.dtos.GroupDto;
import at.technikum.springrestbackend.entities.Group;
import at.technikum.springrestbackend.mappers.GroupMapper;
import at.technikum.springrestbackend.repositories.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.technikum.springrestbackend.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    private static final Logger LOG = LoggerFactory.getLogger(GroupService.class);

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupService(GroupMapper groupMapper, GroupRepository groupRepository){
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
    }

    public List<GroupDto> getAllGroups() {

        return groupRepository.findAll().stream().map(groupMapper::toDto).toList();
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
}

